@echo off
echo.
echo [ERP-PRODUCTION] Starting ruoyi-modules-erp-production (port 9208) ...
echo.

cd %~dp0
cd ../ruoyi-modules/ruoyi-erp-production/target

set JAVA_OPTS=-Xms256m -Xmx512m -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=256m

java -Dfile.encoding=utf-8 %JAVA_OPTS% -jar ruoyi-modules-erp-production.jar

cd bin
pause
