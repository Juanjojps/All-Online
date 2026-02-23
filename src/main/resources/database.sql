-- Creación de esquema (si no existe, aunque ddl-auto=update lo gestiona, esto es bueno para reiniciar)
DROP TABLE IF EXISTS linea_pedido;
DROP TABLE IF EXISTS pedido;
DROP TABLE IF EXISTS producto;

CREATE TABLE producto (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    descripcion TEXT,
    precio DECIMAL(10, 2) NOT NULL,
    stock INT NOT NULL,
    categoria VARCHAR(50) NOT NULL,
    imagen_url VARCHAR(255)
);

CREATE TABLE pedido (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    fecha DATETIME,
    total DECIMAL(10, 2),
    estado VARCHAR(50)
);

CREATE TABLE linea_pedido (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    pedido_id BIGINT,
    producto_id BIGINT,
    cantidad INT NOT NULL,
    precio_unitario DECIMAL(10, 2),
    FOREIGN KEY (pedido_id) REFERENCES pedido(id),
    FOREIGN KEY (producto_id) REFERENCES producto(id)
);

-- Datos iniciales
INSERT INTO producto (nombre, descripcion, precio, stock, categoria, imagen_url) VALUES 
('Portátil Gaming', 'Ordenador portátil de alto rendimiento', 1200.50, 10, 'INFORMATICA', '/images/portatil.jpg'),
('Ratón Inalámbrico', 'Ratón ergonómico con batería de larga duración', 25.99, 50, 'INFORMATICA', '/images/raton.jpg'),
('Teclado Mecánico', 'Teclado con luces RGB y switches azules', 45.00, 30, 'INFORMATICA', '/images/teclado.jpg'),
('Monitor 24"', 'Monitor Full HD IPS', 150.00, 15, 'INFORMATICA', '/images/monitor.jpg'),
('Silla de Oficina', 'Silla ergonómica reclinable', 89.99, 20, 'HOGAR', '/images/silla.jpeg'),
('Mesa Escritorio', 'Mesa amplia de madera', 120.00, 5, 'HOGAR', '/images/mesa.jpg'),
('Lámpara LED', 'Lámpara de escritorio con intensidad regulable', 15.50, 100, 'HOGAR', '/images/lampara.jpeg'),
('Smartphone Pro', 'Teléfono inteligente de última generación', 899.00, 25, 'ELECTRONICA', '/images/smartphone.jpg'),
('Auriculares Bluetooth', 'Auriculares con cancelación de ruido', 199.99, 40, 'ELECTRONICA', '/images/auriculares.jpg'),
('Bicicleta de Montaña', 'Bicicleta robusta para terrenos difíciles', 450.00, 8, 'DEPORTE', '/images/bicicleta.jpg'),
('Set de Pesas', 'Mancuernas ajustables de 20kg', 75.00, 15, 'DEPORTE', '/images/pesas.jpg'),
('Sudadera Casual', 'Sudadera de algodón 100% muy cómoda', 29.95, 60, 'ROPA', '/images/sudadera.jpg'),
('Zapatillas Running', 'Calzado deportivo ligero y transpirable', 59.90, 35, 'ROPA', '/images/zapatillas.jpg');
