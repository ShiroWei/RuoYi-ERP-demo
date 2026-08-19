@echo off
rem ============================================================
rem ERP Demo - One-click start: Nacos + Redis + backend jars + frontend
rem Prerequisites:
rem   1. DB already initialized (run scripts\setup-db.bat once)
rem   2. Backend jars already packaged (mvn clean install -DskipTests
rem      at root, then bin\package.bat)
rem   3. Nacos 3.0.2 installed at D:\env\nacos-server-3.0.2
rem      (change NACOS_HOME below if different)
rem ============================================================
cd /d %~dp0..

set NACOS_HOME=D:\env\nacos-server-3.0.2

echo [1/3] Check infrastructure ...
if exist "%NACOS_HOME%\bin\startup.cmd" (
  echo   - Starting Nacos (standalone) ...
  start "ERP-Nacos" cmd /k "cd /d %NACOS_HOME%\bin && startup.cmd -m standalone"
) else (
  echo   - [WARN] Nacos not found at %NACOS_HOME%. Start Nacos 3.0.2 manually.
)

net start 2>nul | findstr /i "Redis" >nul
if %errorlevel%==0 (
  echo   - Redis service already running.
) else (
  where redis-server >nul 2>nul
  if %errorlevel%==0 (
    echo   - Starting Redis (6379) ...
    start "ERP-Redis" redis-server --port 6379
  ) else (
    echo   - [WARN] Redis not found. Start Redis(6379) manually.
  )
)

echo [2/3] Starting backend microservices ...
start "ERP-Gateway" cmd /k "call bin\run-gateway.bat"
start "ERP-Auth"    cmd /k "call bin\run-auth.bat"
start "ERP-System"  cmd /k "call bin\run-modules-system.bat"
start "ERP-Gen"     cmd /k "call bin\run-modules-gen.bat"
start "ERP-Job"     cmd /k "call bin\run-modules-job.bat"
start "ERP-File"    cmd /k "call bin\run-modules-file.bat"
start "ERP-Monitor" cmd /k "call bin\run-monitor.bat"
start "ERP-ERP"     cmd /k "call bin\run-modules-erp.bat"

echo [3/3] Starting frontend ...
start "ERP-UI" cmd /k "cd /d %~dp0..\ruoyi-ui && npm run dev"

echo.
echo ============================================================
echo  All startup commands dispatched.
echo    Gateway : http://localhost:8000
echo    Frontend: http://localhost          (login: admin / admin123)
echo    Nacos   : http://127.0.0.1:18088
echo  Wait ~1-2 min for services to register before using frontend.
echo ============================================================
pause
