-- Primero, verificar si la columna rol existe, si no, crearla
SET @exist := (SELECT COUNT(*) 
               FROM information_schema.columns 
               WHERE table_name = 'usuarios' 
               AND column_name = 'rol'
               AND table_schema = 'datacash');

SET @sql := IF(@exist = 0, 'ALTER TABLE usuarios ADD COLUMN rol VARCHAR(10) NOT NULL DEFAULT "USER"', 'SELECT "Column exists"');

PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

-- Insertar o actualizar el usuario administrador
INSERT INTO usuarios (nombre_usuario, correo_electronico, contraseña_hash, rol)
VALUES ('Administrador', 'admin@datacash.com', '$2a$10$8FVnYPWZPwswo.rR3PO7M.ydNK.y5J9Zw2AZm0hJZYv4Ouc0mOjQe', 'ADMIN')
ON DUPLICATE KEY UPDATE
    contraseña_hash = '$2a$10$8FVnYPWZPwswo.rR3PO7M.ydNK.y5J9Zw2AZm0hJZYv4Ouc0mOjQe',
    rol = 'ADMIN';

-- La contraseña es: admin123