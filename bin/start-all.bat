@echo off
setlocal EnableExtensions

rem ERP demo one-click startup script for Windows.
rem Start order: infrastructure -> shared auth/system -> ERP services -> shared gateway -> UI.
rem This script never stops OA services on ports 9210-9214.

set "ROOT=%~dp0.."
for %%I in ("%ROOT%") do set "ROOT=%%~fI"
set "NACOS_HOME=%NACOS_HOME%"
if not defined NACOS_HOME set "NACOS_HOME=D:\env\nacos-server-3.0.2"
set "JAVA_OPTS=-Dfile.encoding=utf-8 -Xms256m -Xmx512m"

echo.
echo ========================================
echo   RuoYi ERP Demo - Start All
echo ========================================
echo Root : %ROOT%
echo Nacos: %NACOS_HOME%
echo.

call :cleanup_erp
if errorlevel 1 goto :fail

call :check_command java "Java"
if errorlevel 1 goto :fail

if not defined JAVA_HOME (
    for /f "delims=" %%J in ('where java 2^>nul') do if not defined JAVA_HOME for %%K in ("%%J") do set "JAVA_HOME=%%~dpK.."
)
for %%J in ("%JAVA_HOME%") do set "JAVA_HOME=%%~fJ"
if not exist "%JAVA_HOME%\bin\java.exe" (
    echo [ERROR] JAVA_HOME is not set and could not be inferred from java on PATH.
    echo         Set JAVA_HOME to a JDK directory before running this script.
    goto :fail
)
echo [OK] JAVA_HOME=%JAVA_HOME%

call :check_port 3306 "MySQL"
if errorlevel 1 goto :fail
call :check_port 6379 "Redis"
if errorlevel 1 goto :fail

call :check_or_start_nacos
if errorlevel 1 goto :fail

call :check_jar "ruoyi-auth\target\ruoyi-auth.jar"
if errorlevel 1 goto :fail
call :check_jar "ruoyi-modules\ruoyi-system\target\ruoyi-modules-system.jar"
if errorlevel 1 goto :fail
call :check_jar "ruoyi-modules\ruoyi-erp-base\target\ruoyi-modules-erp-base.jar"
if errorlevel 1 goto :fail
call :check_jar "ruoyi-modules\ruoyi-erp-purchase\target\ruoyi-modules-erp-purchase.jar"
if errorlevel 1 goto :fail
call :check_jar "ruoyi-modules\ruoyi-erp-sale\target\ruoyi-modules-erp-sale.jar"
if errorlevel 1 goto :fail
call :check_jar "ruoyi-modules\ruoyi-erp-stock\target\ruoyi-modules-erp-stock.jar"
if errorlevel 1 goto :fail
call :check_jar "ruoyi-modules\ruoyi-erp-finance\target\ruoyi-modules-erp-finance.jar"
if errorlevel 1 goto :fail
call :check_jar "ruoyi-modules\ruoyi-erp-production\target\ruoyi-modules-erp-production.jar"
if errorlevel 1 goto :fail
call :check_jar "ruoyi-modules\ruoyi-erp-report\target\ruoyi-modules-erp-report.jar"
if errorlevel 1 goto :fail
call :check_jar "ruoyi-gateway\target\ruoyi-gateway.jar"
if errorlevel 1 goto :fail

call :start_jar 9200 "ruoyi-auth" "ruoyi-auth\target" "ruoyi-auth.jar"
call :start_jar 9201 "ruoyi-system" "ruoyi-modules\ruoyi-system\target" "ruoyi-modules-system.jar"
call :start_jar 9217 "ruoyi-erp-base" "ruoyi-modules\ruoyi-erp-base\target" "ruoyi-modules-erp-base.jar"
call :start_jar 9218 "ruoyi-erp-purchase" "ruoyi-modules\ruoyi-erp-purchase\target" "ruoyi-modules-erp-purchase.jar"
call :start_jar 9219 "ruoyi-erp-sale" "ruoyi-modules\ruoyi-erp-sale\target" "ruoyi-modules-erp-sale.jar"
call :start_jar 9220 "ruoyi-erp-stock" "ruoyi-modules\ruoyi-erp-stock\target" "ruoyi-modules-erp-stock.jar"
call :start_jar 9221 "ruoyi-erp-finance" "ruoyi-modules\ruoyi-erp-finance\target" "ruoyi-modules-erp-finance.jar"
call :start_jar 9222 "ruoyi-erp-production" "ruoyi-modules\ruoyi-erp-production\target" "ruoyi-modules-erp-production.jar"
call :start_jar 9223 "ruoyi-erp-report" "ruoyi-modules\ruoyi-erp-report\target" "ruoyi-modules-erp-report.jar"

call :is_port_open 8000
if errorlevel 1 (
    echo [INFO] Port 8000 is free. Starting ERP gateway.
    start "ERP gateway" /D "%ROOT%\ruoyi-gateway\target" cmd /k "java %JAVA_OPTS% -jar ruoyi-gateway.jar"
) else (
    echo [WARN] Port 8000 is already occupied. ERP gateway will not be started.
    echo [WARN] If OA is running, stop OA or use the Docker dual-stack setup.
)

if exist "%ROOT%\ruoyi-ui\node_modules" (
    start "ERP frontend" /D "%ROOT%\ruoyi-ui" cmd /k "set port=3000&& npm run dev"
) else (
    echo [WARN] ruoyi-ui\node_modules does not exist. Run npm install first.
)

echo.
echo [OK] ERP services have been started in separate windows.
echo [INFO] URLs: UI http://localhost:3000, Gateway http://localhost:8000
echo [INFO] Nacos: http://localhost:18088
echo [INFO] Login: admin / admin123
echo [INFO] Optional gen/job/file/monitor services are not started by this script.
echo [INFO] Stop ERP services with scripts\stop-all.bat.
echo.
exit /b 0

:cleanup_erp
echo [INFO] Cleaning existing ERP application processes...
powershell -NoProfile -ExecutionPolicy Bypass -Command ^
  "$root=[regex]::Escape('%ROOT%');" ^
  "$targets=Get-CimInstance Win32_Process | Where-Object {" ^
  "  $_.CommandLine -and (" ^
  "    $_.CommandLine -match 'ruoyi-modules-erp-(base|purchase|sale|stock|finance|production|report)\.jar' -or" ^
  "    ($_.CommandLine -match 'vue-cli-service' -and $_.CommandLine -match $root)" ^
  "  )" ^
  "};" ^
  "$targets | ForEach-Object { Stop-Process -Id $_.ProcessId -Force -ErrorAction SilentlyContinue }"
timeout /t 3 /nobreak >nul

for %%P in (9217 9218 9219 9220 9221 9222 9223 3000) do (
    call :is_port_open %%P
    if not errorlevel 1 (
        echo [ERROR] Port %%P is still occupied after ERP cleanup.
        echo         Close the remaining process before starting ERP.
        exit /b 1
    )
)
echo [OK] Existing ERP application processes have been cleaned.
echo [INFO] Shared Nacos/auth/system/gateway and OA ports were intentionally preserved.
exit /b 0

:check_command
where %~1 >nul 2>nul
if errorlevel 1 (
    echo [ERROR] %~2 was not found in PATH.
    exit /b 1
)
exit /b 0

:check_port
powershell -NoProfile -ExecutionPolicy Bypass -Command "$c=Get-NetTCPConnection -LocalPort %~1 -State Listen -ErrorAction SilentlyContinue; if($c){exit 0}else{exit 1}" >nul 2>nul
if errorlevel 1 (
    echo [ERROR] %~2 is not listening on port %~1.
    exit /b 1
)
echo [OK] %~2 is listening on %~1.
exit /b 0

:check_or_start_nacos
call :is_port_open 8848
if not errorlevel 1 (
    echo [OK] Nacos is already listening on 8848.
    exit /b 0
)
if not exist "%NACOS_HOME%\bin\startup.cmd" (
    echo [ERROR] Nacos startup script not found: %NACOS_HOME%\bin\startup.cmd
    exit /b 1
)
echo [INFO] Starting Nacos in standalone mode...
start "ERP Nacos" /D "%NACOS_HOME%\bin" cmd /k "startup.cmd -m standalone"
for /L %%N in (1,1,90) do (
    timeout /t 1 /nobreak >nul
    call :is_port_open 8848
    if not errorlevel 1 (
        echo [OK] Nacos is listening on 8848.
        exit /b 0
    )
)
echo [ERROR] Nacos did not become ready within 90 seconds.
exit /b 1

:check_jar
if not exist "%ROOT%\%~1" (
    echo [ERROR] Jar not found: %ROOT%\%~1
    echo         Build it first with Maven package -DskipTests.
    exit /b 1
)
exit /b 0

:start_jar
call :is_port_open %~1
if not errorlevel 1 (
    echo [SKIP] %~2 is already listening on %~1.
    exit /b 0
)
echo [INFO] Starting %~2 on %~1...
start "%~2" /D "%ROOT%\%~3" cmd /k "java %JAVA_OPTS% -jar %~4"
exit /b 0

:is_port_open
powershell -NoProfile -ExecutionPolicy Bypass -Command "$c=Get-NetTCPConnection -LocalPort %~1 -State Listen -ErrorAction SilentlyContinue; if($c){exit 0}else{exit 1}" >nul 2>nul
exit /b %errorlevel%

:fail
echo.
echo [FAILED] Startup aborted. Fix the error above and run start-all.bat again.
pause
exit /b 1
