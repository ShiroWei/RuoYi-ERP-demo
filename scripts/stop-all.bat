@echo off
rem ============================================================
rem ERP Demo - Stop frontend and backend microservices
rem (only processes started by this project are stopped,
rem  Nacos / Redis / MySQL are left untouched)
rem ============================================================
echo Stopping frontend (vue-cli-service) ...
powershell -NoProfile -Command "Get-CimInstance Win32_Process | Where-Object { $_.CommandLine -match 'vue-cli-service' } | ForEach-Object { Stop-Process -Id $_.ProcessId -Force -ErrorAction SilentlyContinue }"

echo Stopping backend ERP jars ...
powershell -NoProfile -Command "Get-CimInstance Win32_Process | Where-Object { $_.CommandLine -match 'ruoyi-(gateway|auth|modules-system|modules-gen|modules-job|modules-file|monitor|modules-erp-base|modules-erp-purchase|modules-erp-sale|modules-erp-stock|modules-erp-finance|modules-erp-production|modules-erp-report)\.jar' } | ForEach-Object { Stop-Process -Id $_.ProcessId -Force -ErrorAction SilentlyContinue }"

echo.
echo Done. Nacos/Redis/MySQL keep running (stop them manually if needed).
pause
