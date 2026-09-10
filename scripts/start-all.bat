@echo off
setlocal EnableExtensions

rem Compatibility wrapper. The canonical ERP launcher is bin\start-all.bat.
call "%~dp0..\bin\start-all.bat" %*
set EXIT_CODE=%ERRORLEVEL%

if not "%EXIT_CODE%"=="0" (
  echo.
  echo Startup failed with exit code %EXIT_CODE%.
  pause
)

exit /b %EXIT_CODE%
