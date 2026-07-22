@echo off
cd /d "%~dp0\.."
docker compose ps
echo.
echo Eureka:   http://localhost:8761
echo Keycloak: http://localhost:8090
echo Swagger Libros:    http://localhost:8081/swagger-ui.html
echo Swagger Prestamos: http://localhost:8082/swagger-ui.html
