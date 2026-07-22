-- Estructura de referencia de los dos microservicios.
-- JPA usa ddl-auto=update, por lo que también verificará y completará el esquema al iniciar.

USE libros_db;

CREATE TABLE IF NOT EXISTS libros (
    id BIGINT NOT NULL AUTO_INCREMENT,
    isbn VARCHAR(20) NOT NULL,
    titulo VARCHAR(160) NOT NULL,
    autor VARCHAR(120) NOT NULL,
    categoria VARCHAR(80) NOT NULL,
    anio_publicacion INT NOT NULL,
    disponible BOOLEAN NOT NULL DEFAULT TRUE,
    creado_en DATETIME(6) NOT NULL,
    actualizado_en DATETIME(6) NOT NULL,
    CONSTRAINT pk_libros PRIMARY KEY (id),
    CONSTRAINT uk_libro_isbn UNIQUE (isbn)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

USE prestamos_db;

CREATE TABLE IF NOT EXISTS prestamos (
    id BIGINT NOT NULL AUTO_INCREMENT,
    libro_id BIGINT NOT NULL COMMENT 'Referencia lógica al id de libros-service; no se crea FK entre microservicios',
    lector_nombre VARCHAR(140) NOT NULL,
    lector_documento VARCHAR(20) NOT NULL,
    fecha_prestamo DATE NOT NULL,
    fecha_limite DATE NOT NULL,
    fecha_devolucion DATE NULL,
    estado VARCHAR(20) NOT NULL,
    multa DECIMAL(10,2) NOT NULL DEFAULT 0.00,
    registrado_por VARCHAR(80) NOT NULL,
    creado_en DATETIME(6) NOT NULL,
    actualizado_en DATETIME(6) NOT NULL,
    CONSTRAINT pk_prestamos PRIMARY KEY (id),
    INDEX idx_prestamo_libro (libro_id),
    INDEX idx_prestamo_documento (lector_documento)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
