@echo off
echo.
echo [ERP] Starting Modules-ERP ...
echo.

cd %~dp0
cd ../ruoyi-modules/ruoyi-erp/target

set JAVA_OPTS=-Xms512m -Xmx1024m -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=512m

java -Dfile.encoding=utf-8 %JAVA_OPTS% -jar ruoyi-modules-erp.jar

cd bin
pause
