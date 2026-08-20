@echo off
echo.
echo [ERP-REPORT] Starting ruoyi-modules-erp-report (port 9209) ...
echo.

cd %~dp0
cd ../ruoyi-modules/ruoyi-erp-report/target

set JAVA_OPTS=-Xms256m -Xmx512m -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=256m

java -Dfile.encoding=utf-8 %JAVA_OPTS% -jar ruoyi-modules-erp-report.jar

cd bin
pause
