@echo off
tasklist /FI "IMAGENAME eq java.exe" | find /C /I "java.exe" > NUL
if %errorlevel%==0 goto :running

PAUSE
echo tomcat is not running
goto :eof

:running
taskkill /im java.exe
:eof




