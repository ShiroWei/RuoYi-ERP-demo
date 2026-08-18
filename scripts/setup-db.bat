@echo off
rem ============================================================
rem ERP Demo - Database init script (local MySQL: root / empty password)
rem ============================================================
set MYSQL=mysql -h 127.0.0.1 -P 3306 -uroot

echo [1/4] Creating database ry-cloud ...
%MYSQL% -e "CREATE DATABASE IF NOT EXISTS `ry-cloud` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;"
if errorlevel 1 goto :err

echo [2/4] Importing business tables ...
%MYSQL% --default-character-set=utf8mb4 ry-cloud < sql\ry_20260417.sql
if errorlevel 1 goto :err
%MYSQL% --default-character-set=utf8mb4 ry-cloud < sql\quartz.sql
if errorlevel 1 goto :err

echo [3/4] Importing ERP tables and report-center menus ...
%MYSQL% --default-character-set=utf8mb4 ry-cloud < sql\ry_erp_20260818.sql
if errorlevel 1 goto :err
%MYSQL% --default-character-set=utf8mb4 ry-cloud < sql\ry_erp_20260819.sql
if errorlevel 1 goto :err

echo [4/4] Importing Nacos config database (ry-config) ...
%MYSQL% < sql\ry_config_20260611.sql
if errorlevel 1 goto :err

echo.
echo ============================================================
echo  Database init finished. Please keep root password EMPTY,
echo  otherwise edit Nacos configs (ruoyi-*-dev.yml) password.
echo ============================================================
pause
exit /b 0

:err
echo.
echo [ERROR] Database init failed. Check MySQL is running and reachable.
pause
exit /b 1
