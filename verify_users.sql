-- Verificar la estructura de la tabla
DESCRIBE usuarios;

-- Mostrar todos los usuarios
SELECT * FROM usuarios;

-- Eliminar usuarios existentes (¡cuidado! esto borrará todos los usuarios)
-- DELETE FROM usuarios;

-- Crear el usuario administrador con la contraseña correctamente hasheada
INSERT INTO usuarios (nombre_usuario, correo_electronico, contraseña_hash, rol)
VALUES ('Administrador', 'admin@datacash.com', '$2a$10$8FVnYPWZPwswo.rR3PO7M.ydNK.y5J9Zw2AZm0hJZYv4Ouc0mOjQe', 'ADMIN')
ON DUPLICATE KEY UPDATE
    contraseña_hash = '$2a$10$8FVnYPWZPwswo.rR3PO7M.ydNK.y5J9Zw2AZm0hJZYv4Ouc0mOjQe',
    rol = 'ADMIN';

-- Verificar el usuario creado
SELECT usuario_id, nombre_usuario, correo_electronico, rol FROM usuarios WHERE correo_electronico = 'admin@datacash.com';