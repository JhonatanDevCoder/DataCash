ALTER TABLE usuarios ADD COLUMN rol VARCHAR(10) NOT NULL DEFAULT 'USER';

-- Crear el usuario administrador
INSERT INTO usuarios (nombre_usuario, correo_electronico, contraseña_hash, rol)
VALUES ('Administrador', 'admin@datacash.com', '$2a$10$vd7owI98Ym8uVQxj9tWOUOcX0HpOSr1i5fzE8xgkU5GsF/eBx/UXG', 'ADMIN');
-- La contraseña es 'admin123'