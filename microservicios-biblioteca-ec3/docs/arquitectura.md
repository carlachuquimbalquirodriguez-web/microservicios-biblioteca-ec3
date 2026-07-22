# Arquitectura y modelo de datos

## Comunicación

```mermaid
sequenceDiagram
    actor Cliente as Postman
    participant KC as Keycloak
    participant PS as prestamos-service
    participant EU as Eureka
    participant LS as libros-service
    participant DBP as prestamos_db
    participant DBL as libros_db

    Cliente->>KC: Solicita access token
    KC-->>Cliente: JWT con roles
    Cliente->>PS: POST /api/prestamos + Bearer JWT
    PS->>EU: Resolver LIBROS-SERVICE
    EU-->>PS: Instancia disponible
    PS->>LS: GET /api/libros/{id} + mismo JWT
    LS->>DBL: Consultar libro
    DBL-->>LS: Libro disponible
    LS-->>PS: Datos del libro
    PS->>DBP: Guardar préstamo
    PS->>LS: PATCH disponibilidad=false
    LS->>DBL: Actualizar libro
    PS-->>Cliente: Préstamo creado
```

## Entidad Libro

```mermaid
erDiagram
    LIBRO {
        bigint id PK
        varchar isbn UK
        varchar titulo
        varchar autor
        varchar categoria
        int anio_publicacion
        boolean disponible
        datetime creado_en
        datetime actualizado_en
    }
```

## Entidad Préstamo

No existe una llave foránea física entre bases de datos de microservicios. `libro_id` es una referencia lógica validada mediante la API de libros.

```mermaid
erDiagram
    PRESTAMO {
        bigint id PK
        bigint libro_id "Referencia lógica"
        varchar lector_nombre
        varchar lector_documento
        date fecha_prestamo
        date fecha_limite
        date fecha_devolucion
        varchar estado
        decimal multa
        varchar registrado_por
        datetime creado_en
        datetime actualizado_en
    }
```
