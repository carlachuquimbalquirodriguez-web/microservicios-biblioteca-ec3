@echo off
cd /d "%~dp0\.."
echo ADVERTENCIA: se eliminaran contenedores y datos de MySQL.
pause
docker compose down -v --remove-orphans
