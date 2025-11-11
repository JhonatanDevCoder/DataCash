-- Crear la base de datos
CREATE DATABASE IF NOT EXISTS parqueadero;
USE parqueadero;

-- Tabla de Usuarios
CREATE TABLE IF NOT EXISTS usuario (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    role VARCHAR(20) NOT NULL DEFAULT 'USER',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Tabla de Categorías
CREATE TABLE IF NOT EXISTS categoria (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    descripcion TEXT,
    usuario_id BIGINT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (usuario_id) REFERENCES usuario(id)
);

-- Tabla de Meses
CREATE TABLE IF NOT EXISTS mes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    anio INT NOT NULL,
    usuario_id BIGINT,
    total_ingresos DECIMAL(10,2) DEFAULT 0.00,
    total_gastos DECIMAL(10,2) DEFAULT 0.00,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (usuario_id) REFERENCES usuario(id)
);

-- Tabla de Ingresos
CREATE TABLE IF NOT EXISTS ingreso (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    monto DECIMAL(10,2) NOT NULL,
    descripcion TEXT,
    fecha DATE NOT NULL,
    categoria_id BIGINT,
    usuario_id BIGINT,
    mes_id BIGINT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (categoria_id) REFERENCES categoria(id),
    FOREIGN KEY (usuario_id) REFERENCES usuario(id),
    FOREIGN KEY (mes_id) REFERENCES mes(id)
);

-- Tabla de Gastos
CREATE TABLE IF NOT EXISTS gasto (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    monto DECIMAL(10,2) NOT NULL,
    descripcion TEXT,
    fecha DATE NOT NULL,
    categoria_id BIGINT,
    usuario_id BIGINT,
    mes_id BIGINT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (categoria_id) REFERENCES categoria(id),
    FOREIGN KEY (usuario_id) REFERENCES usuario(id),
    FOREIGN KEY (mes_id) REFERENCES mes(id)
);

-- Insertar usuario administrador por defecto
-- Contraseña: admin123 (asegúrate de cambiarla en producción)
INSERT INTO usuario (username, password, email, role)
VALUES ('admin', '$2a$10$xn3LI/AjqicFYZFruSwve.681477XaVNaUQbr1gioaWPn4t1KsnmG', 'admin@datacash.com', 'ADMIN')
ON DUPLICATE KEY UPDATE role = 'ADMIN';

-- Insertar categorías por defecto
INSERT INTO categoria (nombre, descripcion, usuario_id) VALUES 
('Salario', 'Ingresos por trabajo', 1),
('Inversiones', 'Ingresos por inversiones', 1),
('Alimentos', 'Gastos en comida y víveres', 1),
('Transporte', 'Gastos en transporte y combustible', 1),
('Servicios', 'Gastos en servicios públicos', 1),
('Entretenimiento', 'Gastos en actividades recreativas', 1),
('Educación', 'Gastos en formación y materiales', 1),
('Salud', 'Gastos médicos y medicamentos', 1)
ON DUPLICATE KEY UPDATE nombre = VALUES(nombre);

-- Crear triggers para actualizar totales en mes
DELIMITER //

CREATE TRIGGER after_ingreso_insert 
AFTER INSERT ON ingreso
FOR EACH ROW
BEGIN
    UPDATE mes 
    SET total_ingresos = total_ingresos + NEW.monto
    WHERE id = NEW.mes_id;
END;//

CREATE TRIGGER after_ingreso_delete
AFTER DELETE ON ingreso
FOR EACH ROW
BEGIN
    UPDATE mes
    SET total_ingresos = total_ingresos - OLD.monto
    WHERE id = OLD.mes_id;
END;//

CREATE TRIGGER after_gasto_insert
AFTER INSERT ON gasto
FOR EACH ROW
BEGIN
    UPDATE mes
    SET total_gastos = total_gastos + NEW.monto
    WHERE id = NEW.mes_id;
END;//

CREATE TRIGGER after_gasto_delete
AFTER DELETE ON gasto
FOR EACH ROW
BEGIN
    UPDATE mes
    SET total_gastos = total_gastos - OLD.monto
    WHERE id = OLD.mes_id;
END;//

DELIMITER ;

-- Crear índices para mejorar el rendimiento
CREATE INDEX idx_usuario_username ON usuario(username);
CREATE INDEX idx_ingreso_fecha ON ingreso(fecha);
CREATE INDEX idx_gasto_fecha ON gasto(fecha);
CREATE INDEX idx_mes_usuario ON mes(usuario_id, anio);