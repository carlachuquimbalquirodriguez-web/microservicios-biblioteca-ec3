@echo off
setlocal
cd /d "%~dp0\.."
echo Construyendo e iniciando todos los contenedores...
docker compose up -d --build
if errorlevel 1 (
  echo.
  echo Ocurrio un error. Ejecuta: docker compose logs --tail=200
  exit /b 1
)
echo.
echo Proyecto iniciado. Ejecuta "scripts\estado.bat" para ver el estado.
endlocal
