CREATE DATABASE IF NOT EXISTS libros_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS prestamos_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS keycloak_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE USER IF NOT EXISTS 'biblioteca'@'%' IDENTIFIED BY 'biblioteca123';
CREATE USER IF NOT EXISTS 'keycloak'@'%' IDENTIFIED BY 'keycloak123';

GRANT ALL PRIVILEGES ON libros_db.* TO 'biblioteca'@'%';
GRANT ALL PRIVILEGES ON prestamos_db.* TO 'biblioteca'@'%';
GRANT ALL PRIVILEGES ON keycloak_db.* TO 'keycloak'@'%';
FLUSH PRIVILEGES;
