@echo off
echo.
echo [ERP-BASE] Starting ruoyi-modules-erp-base (port 9203) ...
echo.

cd %~dp0
cd ../ruoyi-modules/ruoyi-erp-base/target

set JAVA_OPTS=-Xms256m -Xmx512m -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=256m

java -Dfile.encoding=utf-8 %JAVA_OPTS% -jar ruoyi-modules-erp-base.jar

cd bin
pause
