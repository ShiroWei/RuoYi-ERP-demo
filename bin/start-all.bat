@echo off
setlocal EnableExtensions

rem Canonical ERP launcher. The implementation lives in start-all.ps1 so
rem process cleanup and startup checks are not affected by cmd label parsing.
powershell -NoProfile -ExecutionPolicy Bypass -File "%~dp0start-all.ps1" %*
set EXIT_CODE=%ERRORLEVEL%

if not "%EXIT_CODE%"=="0" (
  echo.
  echo ERP startup failed with exit code %EXIT_CODE%.
  pause
)

exit /b %EXIT_CODE%
