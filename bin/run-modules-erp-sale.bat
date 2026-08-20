@echo off
echo.
echo [ERP-SALE] Starting ruoyi-modules-erp-sale (port 9219) ...
echo.

cd %~dp0
cd ../ruoyi-modules/ruoyi-erp-sale/target

set JAVA_OPTS=-Xms256m -Xmx512m -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=256m

java -Dfile.encoding=utf-8 %JAVA_OPTS% -jar ruoyi-modules-erp-sale.jar

cd bin
pause
