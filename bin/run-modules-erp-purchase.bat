@echo off
echo.
echo [ERP-PURCHASE] Starting ruoyi-modules-erp-purchase (port 9218) ...
echo.

cd %~dp0
cd ../ruoyi-modules/ruoyi-erp-purchase/target

set JAVA_OPTS=-Xms256m -Xmx512m -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=256m

java -Dfile.encoding=utf-8 %JAVA_OPTS% -jar ruoyi-modules-erp-purchase.jar

cd bin
pause
