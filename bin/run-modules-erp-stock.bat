@echo off
echo.
echo [ERP-STOCK] Starting ruoyi-modules-erp-stock (port 9220) ...
echo.

cd %~dp0
cd ../ruoyi-modules/ruoyi-erp-stock/target

set JAVA_OPTS=-Xms256m -Xmx512m -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=256m

java -Dfile.encoding=utf-8 %JAVA_OPTS% -jar ruoyi-modules-erp-stock.jar

cd bin
pause
