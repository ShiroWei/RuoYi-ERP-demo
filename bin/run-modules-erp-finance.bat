@echo off
echo.
echo [ERP-FINANCE] Starting ruoyi-modules-erp-finance (port 9207) ...
echo.

cd %~dp0
cd ../ruoyi-modules/ruoyi-erp-finance/target

set JAVA_OPTS=-Xms256m -Xmx512m -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=256m

java -Dfile.encoding=utf-8 %JAVA_OPTS% -jar ruoyi-modules-erp-finance.jar

cd bin
pause
