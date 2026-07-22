# Validación técnica realizada

Fecha de validación: 20 de julio de 2026.

## Resultado

- `mvn clean verify`: **BUILD SUCCESS** para el reactor completo.
- Módulos empaquetados correctamente:
  - `eureka-server`
  - `config-server`
  - `libros-service`
  - `prestamos-service`
- Configuración YAML y archivos JSON de Postman/Keycloak validados sintácticamente.
- Eureka Server iniciado desde su JAR y `/actuator/health` respondió `UP`.
- Config Server iniciado desde su JAR y `/actuator/health` respondió `UP`.
- Config Server entregó correctamente las configuraciones de:
  - `/libros-service/default`
  - `/prestamos-service/default`

## Alcance de la validación

El entorno de creación no dispone de un daemon Docker, por lo que la ejecución integrada de MySQL, Keycloak y los dos microservicios debe verificarse en Docker Desktop con:

```bash
docker compose up -d --build
docker compose ps
```

La colección Postman incluida contiene el flujo de prueba completo y guarda automáticamente el token, el id del libro y el id del préstamo.
