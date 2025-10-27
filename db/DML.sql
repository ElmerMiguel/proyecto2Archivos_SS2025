-- =====================================================
-- E-COMMERCE GT - DATOS INICIALES
-- PostgreSQL DML Script
-- Inserciones para valores iniciales del sistema
-- ORDEN: Respeta dependencias de foreign keys
-- =====================================================

-- =====================================================
-- 1. INSERCIÓN DE CATÁLOGOS (Sin dependencias)
-- =====================================================

-- Roles del sistema
INSERT INTO roles (nombre_rol, descripcion) VALUES
('COMUN', 'Usuario que puede comprar y vender productos'),
('MODERADOR', 'Usuario que revisa y aprueba productos'),
('LOGISTICA', 'Usuario que gestiona entregas de pedidos'),
('ADMINISTRADOR', 'Usuario con acceso total al sistema');

-- Estados de producto
INSERT INTO estados_producto (nombre_estado) VALUES
('NUEVO'),
('USADO');

-- Categorías de productos
INSERT INTO categorias (nombre_categoria, descripcion) VALUES
('TECNOLOGIA', 'Productos electrónicos y tecnológicos'),
('HOGAR', 'Artículos para el hogar'),
('ACADEMICO', 'Libros y material educativo'),
('PERSONAL', 'Artículos de uso personal'),
('DECORACION', 'Elementos decorativos'),
('OTRO', 'Otros productos');

-- Estados de pedido
INSERT INTO estados_pedido (nombre_estado) VALUES
('EN_CURSO'),
('ENTREGADO');

-- Estados de solicitud
INSERT INTO estados_solicitud (nombre_estado) VALUES
('PENDIENTE'),
('APROBADO'),
('RECHAZADO');

-- Estados de sanción
INSERT INTO estados_sancion (nombre_estado) VALUES
('ACTIVA'),
('FINALIZADA');

-- Tipos de notificación
INSERT INTO tipos_notificacion (nombre_tipo) VALUES
('CAMBIO_ESTADO_PEDIDO'),
('PRODUCTO_APROBADO'),
('PRODUCTO_RECHAZADO');

-- =====================================================
-- 2. INSERCIÓN DE USUARIOS (Depende de: roles)
-- =====================================================

-- 1 Administrador
INSERT INTO usuarios (id_rol, nombre_completo, email, password_hash, telefono, direccion) VALUES
(4, 'Carlos Méndez Administrador', 'admin@ecommercegt.com', '$2a$10$S4vup6.0L7uehjPO57NSrubejprulB1MIXjJ0n0ac6coxKBirHzLK', '21234567', 'Zona 10, Ciudad de Guatemala');

-- 5 Moderadores
INSERT INTO usuarios (id_rol, nombre_completo, email, password_hash, telefono, direccion) VALUES
(2, 'María González Moderadora', 'moderador1@ecommercegt.com', '$2a$10$S4vup6.0L7uehjPO57NSrubejprulB1MIXjJ0n0ac6coxKBirHzLK', '21234568', 'Zona 1, Ciudad de Guatemala'),
(2, 'José Ramírez Moderador', 'moderador2@ecommercegt.com', '$2a$10$S4vup6.0L7uehjPO57NSrubejprulB1MIXjJ0n0ac6coxKBirHzLK', '21234569', 'Antigua Guatemala'),
(2, 'Ana López Moderadora', 'moderador3@ecommercegt.com', '$2a$10$S4vup6.0L7uehjPO57NSrubejprulB1MIXjJ0n0ac6coxKBirHzLK', '21234570', 'Quetzaltenango'),
(2, 'Pedro Castillo Moderador', 'moderador4@ecommercegt.com', '$2a$10$S4vup6.0L7uehjPO57NSrubejprulB1MIXjJ0n0ac6coxKBirHzLK', '21234571', 'Escuintla'),
(2, 'Laura Fernández Moderadora', 'moderador5@ecommercegt.com', '$2a$10$S4vup6.0L7uehjPO57NSrubejprulB1MIXjJ0n0ac6coxKBirHzLK', '21234572', 'Zona 15, Ciudad de Guatemala');

-- 3 Usuarios de Logística
INSERT INTO usuarios (id_rol, nombre_completo, email, password_hash, telefono, direccion) VALUES
(3, 'Roberto Morales Logística', 'logistica1@ecommercegt.com', '$2a$10$S4vup6.0L7uehjPO57NSrubejprulB1MIXjJ0n0ac6coxKBirHzLK', '21234573', 'Villa Nueva'),
(3, 'Carmen Díaz Logística', 'logistica2@ecommercegt.com', '$2a$10$S4vup6.0L7uehjPO57NSrubejprulB1MIXjJ0n0ac6coxKBirHzLK', '21234574', 'Mixco'),
(3, 'Luis Torres Logística', 'logistica3@ecommercegt.com', '$2a$10$S4vup6.0L7uehjPO57NSrubejprulB1MIXjJ0n0ac6coxKBirHzLK', '21234575', 'San Miguel Petapa');

-- 10 Usuarios Comunes
INSERT INTO usuarios (id_rol, nombre_completo, email, password_hash, telefono, direccion) VALUES
(1, 'Andrea Jiménez García', 'andrea.jimenez@gmail.com', '$2a$10$S4vup6.0L7uehjPO57NSrubejprulB1MIXjJ0n0ac6coxKBirHzLK', '31234567', 'Zona 11, Ciudad de Guatemala'),
(1, 'Fernando Silva Martínez', 'fernando.silva@gmail.com', '$2a$10$S4vup6.0L7uehjPO57NSrubejprulB1MIXjJ0n0ac6coxKBirHzLK', '31234568', 'Zona 13, Ciudad de Guatemala'),
(1, 'Gabriela Ortiz Cruz', 'gabriela.ortiz@gmail.com', '$2a$10$S4vup6.0L7uehjPO57NSrubejprulB1MIXjJ0n0ac6coxKBirHzLK', '31234569', 'Zona 7, Ciudad de Guatemala'),
(1, 'Ricardo Vásquez León', 'ricardo.vasquez@gmail.com', '$2a$10$S4vup6.0L7uehjPO57NSrubejprulB1MIXjJ0n0ac6coxKBirHzLK', '31234570', 'Zona 4, Ciudad de Guatemala'),
(1, 'Sofía Herrera Pérez', 'sofia.herrera@gmail.com', '$2a$10$S4vup6.0L7uehjPO57NSrubejprulB1MIXjJ0n0ac6coxKBirHzLK', '31234571', 'Zona 14, Ciudad de Guatemala'),
(1, 'Diego Moreno Rojas', 'diego.moreno@gmail.com', '$2a$10$S4vup6.0L7uehjPO57NSrubejprulB1MIXjJ0n0ac6coxKBirHzLK', '31234572', 'Zona 3, Ciudad de Guatemala'),
(1, 'Valentina Castro Mejía', 'valentina.castro@gmail.com', '$2a$10$S4vup6.0L7uehjPO57NSrubejprulB1MIXjJ0n0ac6coxKBirHzLK', '31234573', 'Zona 16, Ciudad de Guatemala'),
(1, 'Andrés Guzmán Flores', 'andres.guzman@gmail.com', '$2a$10$S4vup6.0L7uehjPO57NSrubejprulB1MIXjJ0n0ac6coxKBirHzLK', '31234574', 'Zona 5, Ciudad de Guatemala'),
(1, 'Isabella Mendoza Vargas', 'isabella.mendoza@gmail.com', '$2a$10$S4vup6.0L7uehjPO57NSrubejprulB1MIXjJ0n0ac6coxKBirHzLK', '31234575', 'Zona 12, Ciudad de Guatemala'),
(1, 'Sebastián Rivas Aguilar', 'sebastian.rivas@gmail.com', '$2a$10$S4vup6.0L7uehjPO57NSrubejprulB1MIXjJ0n0ac6coxKBirHzLK', '31234576', 'Zona 9, Ciudad de Guatemala');

-- =====================================================
-- 3. INSERCIÓN DE SOLICITUDES DE PRODUCTOS
-- (Depende de: usuarios, estados_solicitud, estados_producto, categorias)
-- =====================================================

-- Usuario 1: Andrea Jiménez (id_usuario = 10) - SOLICITUDES APROBADAS
INSERT INTO solicitudes_producto (id_vendedor, id_estado_solicitud, id_moderador, nombre_producto, descripcion, url_imagen, precio, stock, id_estado_producto, id_categoria, fecha_respuesta, comentario_moderador) VALUES
(10, 2, 2, 'Laptop HP Pavilion 15', 'Laptop HP con procesador Intel i5, 8GB RAM, 256GB SSD', 'https://images.unsplash.com/photo-1496181133206-80ce9b88a853', 4500.00, 5, 1, 1, CURRENT_TIMESTAMP - INTERVAL '2 days', 'Producto aprobado'),
(10, 2, 2, 'Mouse Inalámbrico Logitech', 'Mouse óptico inalámbrico con receptor USB', 'https://images.unsplash.com/photo-1527864550417-7fd91fc51a46', 150.00, 15, 1, 1, CURRENT_TIMESTAMP - INTERVAL '2 days', 'Producto aprobado'),
(10, 2, 3, 'Teclado Mecánico RGB', 'Teclado mecánico gaming con iluminación RGB', 'https://images.unsplash.com/photo-1587829741301-dc798b83add3', 350.00, 10, 1, 1, CURRENT_TIMESTAMP - INTERVAL '1 day', 'Producto aprobado'),
(10, 2, 2, 'Audífonos Sony WH-1000XM4', 'Audífonos con cancelación de ruido activa', 'https://images.unsplash.com/photo-1545127398-14699f92334b', 1200.00, 8, 2, 1, CURRENT_TIMESTAMP - INTERVAL '3 days', 'Producto aprobado'),
(10, 2, 3, 'Webcam Logitech C920', 'Cámara web Full HD 1080p', 'https://images.unsplash.com/photo-1625948515291-69613efd103f', 450.00, 12, 1, 1, CURRENT_TIMESTAMP - INTERVAL '1 day', 'Producto aprobado'),
(10, 2, 2, 'Monitor LG 24 pulgadas', 'Monitor Full HD IPS 24 pulgadas', 'https://images.unsplash.com/photo-1527443224154-c4a3942d3acf', 900.00, 6, 1, 1, CURRENT_TIMESTAMP - INTERVAL '2 days', 'Producto aprobado'),
(10, 2, 3, 'Disco Duro Externo 1TB', 'Disco duro portátil USB 3.0 de 1TB', 'https://images.unsplash.com/photo-1597872200969-2b65d56bd16b', 350.00, 20, 1, 1, CURRENT_TIMESTAMP - INTERVAL '1 day', 'Producto aprobado'),
(10, 2, 2, 'Router WiFi TP-Link', 'Router inalámbrico dual band AC1200', 'https://images.unsplash.com/photo-1606904825846-647eb07f5be2', 280.00, 15, 1, 1, CURRENT_TIMESTAMP - INTERVAL '3 days', 'Producto aprobado'),
(10, 2, 3, 'Cable HDMI 2m', 'Cable HDMI 2.0 de alta velocidad', 'https://images.unsplash.com/photo-1591488320449-011701bb6704', 45.00, 30, 1, 1, CURRENT_TIMESTAMP - INTERVAL '1 day', 'Producto aprobado'),
(10, 2, 2, 'Hub USB-C 7 puertos', 'Hub multipuerto USB-C con HDMI y lector SD', 'https://images.unsplash.com/photo-1625948515291-69613efd103f', 180.00, 18, 1, 1, CURRENT_TIMESTAMP - INTERVAL '2 days', 'Producto aprobado');

-- Usuario 2: Fernando Silva (id_usuario = 11)
INSERT INTO solicitudes_producto (id_vendedor, id_estado_solicitud, id_moderador, nombre_producto, descripcion, url_imagen, precio, stock, id_estado_producto, id_categoria, fecha_respuesta, comentario_moderador) VALUES
(11, 2, 3, 'Juego de Sartenes Antiadherentes', 'Set de 3 sartenes con recubrimiento antiadherente', 'https://images.unsplash.com/photo-1584990347449-39b4aa97f87d', 280.00, 12, 1, 2, CURRENT_TIMESTAMP - INTERVAL '2 days', 'Producto aprobado'),
(11, 2, 2, 'Licuadora 15 velocidades', 'Licuadora de vidrio con 15 velocidades', 'https://images.unsplash.com/photo-1585515320310-259814833e62', 320.00, 8, 1, 2, CURRENT_TIMESTAMP - INTERVAL '1 day', 'Producto aprobado'),
(11, 2, 3, 'Cafetera Programable', 'Cafetera de 12 tazas con temporizador', 'https://images.unsplash.com/photo-1517668808822-9ebb02f2a0e6', 450.00, 10, 1, 2, CURRENT_TIMESTAMP - INTERVAL '3 days', 'Producto aprobado'),
(11, 2, 2, 'Juego de Cuchillos 6 piezas', 'Set de cuchillos de acero inoxidable', 'https://images.unsplash.com/photo-1593618998160-e34014e67546', 180.00, 15, 1, 2, CURRENT_TIMESTAMP - INTERVAL '2 days', 'Producto aprobado'),
(11, 2, 3, 'Organizador de Cocina', 'Organizador modular para especias y utensilios', 'https://images.unsplash.com/photo-1556911220-bff31c812dba', 120.00, 20, 1, 2, CURRENT_TIMESTAMP - INTERVAL '1 day', 'Producto aprobado'),
(11, 2, 2, 'Báscula Digital Cocina', 'Báscula digital con capacidad de 5kg', 'https://images.unsplash.com/photo-1593117877250-e6eae746ba28', 95.00, 18, 1, 2, CURRENT_TIMESTAMP - INTERVAL '3 days', 'Producto aprobado'),
(11, 2, 3, 'Tabla de Cortar Bambú', 'Tabla de cortar de bambú 40x30cm', 'https://images.unsplash.com/photo-1621511450257-28829b1c02b4', 85.00, 25, 1, 2, CURRENT_TIMESTAMP - INTERVAL '2 days', 'Producto aprobado'),
(11, 2, 2, 'Set de Bowls Vidrio', 'Juego de 5 bowls de vidrio con tapas', 'https://images.unsplash.com/photo-1610701596007-11502861dcfa', 140.00, 16, 1, 2, CURRENT_TIMESTAMP - INTERVAL '1 day', 'Producto aprobado'),
(11, 2, 3, 'Exprimidor Eléctrico', 'Exprimidor de cítricos eléctrico', 'https://images.unsplash.com/photo-1600880292203-757bb62b4baf', 110.00, 14, 1, 2, CURRENT_TIMESTAMP - INTERVAL '3 days', 'Producto aprobado'),
(11, 2, 2, 'Termo Stanley 1L', 'Termo de acero inoxidable para bebidas', 'https://images.unsplash.com/photo-1602143407151-7111542de6e8', 280.00, 12, 1, 2, CURRENT_TIMESTAMP - INTERVAL '2 days', 'Producto aprobado');

-- Usuario 3: Gabriela Ortiz (id_usuario = 12)
INSERT INTO solicitudes_producto (id_vendedor, id_estado_solicitud, id_moderador, nombre_producto, descripcion, url_imagen, precio, stock, id_estado_producto, id_categoria, fecha_respuesta, comentario_moderador) VALUES
(12, 2, 2, 'Cálculo de una Variable', 'Libro de cálculo diferencial e integral', 'https://images.unsplash.com/photo-1544947950-fa07a98d237f', 250.00, 10, 2, 3, CURRENT_TIMESTAMP - INTERVAL '2 days', 'Producto aprobado'),
(12, 2, 3, 'Física para Ciencias e Ingeniería', 'Libro completo de física universitaria', 'https://images.unsplash.com/photo-1589998059171-988d887df646', 320.00, 8, 2, 3, CURRENT_TIMESTAMP - INTERVAL '1 day', 'Producto aprobado'),
(12, 2, 2, 'Química Orgánica', 'Texto de química orgánica con ejercicios', 'https://images.unsplash.com/photo-1532012197267-da84d127e765', 280.00, 6, 2, 3, CURRENT_TIMESTAMP - INTERVAL '3 days', 'Producto aprobado'),
(12, 2, 3, 'Álgebra Lineal', 'Libro de álgebra lineal y matrices', 'https://images.unsplash.com/photo-1509266272358-7701da638078', 200.00, 12, 1, 3, CURRENT_TIMESTAMP - INTERVAL '2 days', 'Producto aprobado'),
(12, 2, 2, 'Set de Compás Técnico', 'Juego de geometría profesional', 'https://images.unsplash.com/photo-1596495577886-d920f1fb7238', 95.00, 15, 1, 3, CURRENT_TIMESTAMP - INTERVAL '1 day', 'Producto aprobado'),
(12, 2, 3, 'Calculadora Científica Casio', 'Calculadora fx-991ES PLUS', 'https://images.unsplash.com/photo-1611625618270-e4f35a3c91d8', 180.00, 20, 1, 3, CURRENT_TIMESTAMP - INTERVAL '3 days', 'Producto aprobado'),
(12, 2, 2, 'Diccionario Inglés-Español', 'Diccionario Oxford bilingüe', 'https://images.unsplash.com/photo-1619734086067-24bf8889ea7d', 150.00, 10, 2, 3, CURRENT_TIMESTAMP - INTERVAL '2 days', 'Producto aprobado'),
(12, 2, 3, 'Cuadernos Universitarios Pack x5', 'Pack de 5 cuadernos de 200 hojas', 'https://images.unsplash.com/photo-1588075592446-265fd1e6e76f', 75.00, 30, 1, 3, CURRENT_TIMESTAMP - INTERVAL '1 day', 'Producto aprobado'),
(12, 2, 2, 'Mochila para Laptop', 'Mochila ergonómica con compartimento acolchado', 'https://images.unsplash.com/photo-1553062407-98eeb64c6a62', 280.00, 18, 1, 3, CURRENT_TIMESTAMP - INTERVAL '3 days', 'Producto aprobado'),
(12, 2, 3, 'Resaltadores Stabilo Pack x8', 'Set de 8 resaltadores colores variados', 'https://images.unsplash.com/photo-1586864387634-31f1d8f05b0b', 45.00, 25, 1, 3, CURRENT_TIMESTAMP - INTERVAL '2 days', 'Producto aprobado');

-- Usuario 4: Ricardo Vásquez (id_usuario = 13)
INSERT INTO solicitudes_producto (id_vendedor, id_estado_solicitud, id_moderador, nombre_producto, descripcion, url_imagen, precio, stock, id_estado_producto, id_categoria, fecha_respuesta, comentario_moderador) VALUES
(13, 2, 3, 'Perfume Hugo Boss', 'Fragancia masculina 100ml', 'https://images.unsplash.com/photo-1541643600914-78b084683601', 420.00, 10, 1, 4, CURRENT_TIMESTAMP - INTERVAL '2 days', 'Producto aprobado'),
(13, 2, 2, 'Reloj Casio G-Shock', 'Reloj deportivo resistente al agua', 'https://images.unsplash.com/photo-1524805444758-089113d48a6d', 650.00, 6, 2, 4, CURRENT_TIMESTAMP - INTERVAL '1 day', 'Producto aprobado'),
(13, 2, 3, 'Cartera de Cuero', 'Billetera de cuero genuino', 'https://images.unsplash.com/photo-1627123424574-724758594e93', 180.00, 15, 1, 4, CURRENT_TIMESTAMP - INTERVAL '3 days', 'Producto aprobado'),
(13, 2, 2, 'Gafas de Sol Ray-Ban', 'Lentes de sol con protección UV', 'https://images.unsplash.com/photo-1511499767150-a48a237f0083', 550.00, 8, 2, 4, CURRENT_TIMESTAMP - INTERVAL '2 days', 'Producto aprobado'),
(13, 2, 3, 'Cinturón de Vestir', 'Cinturón de cuero negro reversible', 'https://images.unsplash.com/photo-1624222247344-550fb60583f2', 120.00, 20, 1, 4, CURRENT_TIMESTAMP - INTERVAL '1 day', 'Producto aprobado'),
(13, 2, 2, 'Mancuernillas Elegantes', 'Mancuernillas de acero inoxidable', 'https://images.unsplash.com/photo-1610701596007-11502861dcfa', 95.00, 12, 1, 4, CURRENT_TIMESTAMP - INTERVAL '3 days', 'Producto aprobado'),
(13, 2, 3, 'Kit de Afeitado Profesional', 'Set completo con navaja y brocha', 'https://images.unsplash.com/photo-1621607512214-68297480165e', 220.00, 10, 1, 4, CURRENT_TIMESTAMP - INTERVAL '2 days', 'Producto aprobado'),
(13, 2, 2, 'Mochila Deportiva Nike', 'Mochila para gimnasio con compartimentos', 'https://images.unsplash.com/photo-1553062407-98eeb64c6a62', 280.00, 14, 1, 4, CURRENT_TIMESTAMP - INTERVAL '1 day', 'Producto aprobado'),
(13, 2, 3, 'Set de Pañuelos de Bolsillo', 'Pack de 5 pañuelos de seda', 'https://images.unsplash.com/photo-1632401362533-f9ae0a54d2b0', 65.00, 25, 1, 4, CURRENT_TIMESTAMP - INTERVAL '3 days', 'Producto aprobado'),
(13, 2, 2, 'Corbata de Seda', 'Corbata elegante para ocasiones formales', 'https://images.unsplash.com/photo-1617127365659-c47fa864d8bc', 85.00, 18, 1, 4, CURRENT_TIMESTAMP - INTERVAL '2 days', 'Producto aprobado');

-- Usuario 5: Sofía Herrera (id_usuario = 14)
INSERT INTO solicitudes_producto (id_vendedor, id_estado_solicitud, id_moderador, nombre_producto, descripcion, url_imagen, precio, stock, id_estado_producto, id_categoria, fecha_respuesta, comentario_moderador) VALUES
(14, 2, 2, 'Cuadro Abstracto 60x80cm', 'Pintura abstracta moderna con marco', 'https://images.unsplash.com/photo-1513519245088-0e12902e5a38', 450.00, 5, 1, 5, CURRENT_TIMESTAMP - INTERVAL '2 days', 'Producto aprobado'),
(14, 2, 3, 'Lámpara de Pie Moderna', 'Lámpara de diseño minimalista', 'https://images.unsplash.com/photo-1507473885765-e6ed057f782c', 380.00, 8, 1, 5, CURRENT_TIMESTAMP - INTERVAL '1 day', 'Producto aprobado'),
(14, 2, 2, 'Espejo Decorativo Redondo', 'Espejo con marco dorado 50cm', 'https://images.unsplash.com/photo-1618220179428-22790b461013', 220.00, 10, 1, 5, CURRENT_TIMESTAMP - INTERVAL '3 days', 'Producto aprobado'),
(14, 2, 3, 'Florero de Cerámica', 'Florero artesanal de cerámica blanca', 'https://images.unsplash.com/photo-1578500494198-246f612d3b3d', 95.00, 15, 1, 5, CURRENT_TIMESTAMP - INTERVAL '2 days', 'Producto aprobado'),
(14, 2, 2, 'Set de 3 Macetas Colgantes', 'Macetas de cerámica con soporte', 'https://images.unsplash.com/photo-1485955900006-10f4d324d411', 140.00, 12, 1, 5, CURRENT_TIMESTAMP - INTERVAL '1 day', 'Producto aprobado'),
(14, 2, 3, 'Cojines Decorativos Pack x4', 'Cojines de algodón con diseños geométricos', 'https://images.unsplash.com/photo-1555041469-a586c61ea9bc', 180.00, 20, 1, 5, CURRENT_TIMESTAMP - INTERVAL '3 days', 'Producto aprobado'),
(14, 2, 2, 'Reloj de Pared Vintage', 'Reloj decorativo estilo industrial', 'https://images.unsplash.com/photo-1563861826100-9cb868fdbe1c', 160.00, 10, 1, 5, CURRENT_TIMESTAMP - INTERVAL '2 days', 'Producto aprobado'),
(14, 2, 3, 'Estante Flotante Madera', 'Repisa flotante de madera 80cm', 'https://images.unsplash.com/photo-1595428774223-ef52624120d2', 120.00, 18, 1, 5, CURRENT_TIMESTAMP - INTERVAL '1 day', 'Producto aprobado'),
(14, 2, 2, 'Velas Aromáticas Set x6', 'Set de velas con aromas relajantes', 'https://images.unsplash.com/photo-1602874801006-95e39d3149e9', 85.00, 25, 1, 5, CURRENT_TIMESTAMP - INTERVAL '3 days', 'Producto aprobado'),
(14, 2, 3, 'Cortinas Blackout 2.5m', 'Cortinas opacas para ventana', 'https://images.unsplash.com/photo-1616047006789-b7af5afb8c20', 280.00, 14, 1, 5, CURRENT_TIMESTAMP - INTERVAL '2 days', 'Producto aprobado');

-- Usuario 6: Diego Moreno (id_usuario = 15)
INSERT INTO solicitudes_producto (id_vendedor, id_estado_solicitud, id_moderador, nombre_producto, descripcion, url_imagen, precio, stock, id_estado_producto, id_categoria, fecha_respuesta, comentario_moderador) VALUES
(15, 2, 3, 'Smartwatch Samsung Galaxy', 'Reloj inteligente con monitor cardíaco', 'https://images.unsplash.com/photo-1579586337278-3befd40fd17a', 1200.00, 6, 2, 1, CURRENT_TIMESTAMP - INTERVAL '2 days', 'Producto aprobado'),
(15, 2, 2, 'Tablet Lenovo 10 pulgadas', 'Tablet Android con 64GB almacenamiento', 'https://images.unsplash.com/photo-1544244015-0df4b3ffc6b0', 950.00, 8, 1, 1, CURRENT_TIMESTAMP - INTERVAL '1 day', 'Producto aprobado'),
(15, 2, 3, 'Cargador Inalámbrico Rápido', 'Base de carga inalámbrica 15W', 'https://images.unsplash.com/photo-1591290619762-c588f0e1e8cd', 120.00, 20, 1, 1, CURRENT_TIMESTAMP - INTERVAL '3 days', 'Producto aprobado'),
(15, 2, 2, 'Power Bank 20000mAh', 'Batería externa con carga rápida', 'https://images.unsplash.com/photo-1609091839311-d5365f9ff1c5', 180.00, 15, 1, 1, CURRENT_TIMESTAMP - INTERVAL '2 days', 'Producto aprobado'),
(15, 2, 3, 'Funda Laptop 15.6 pulgadas', 'Funda acolchada resistente al agua', 'https://images.unsplash.com/photo-1588872657578-7efd1f1555ed', 95.00, 25, 1, 1, CURRENT_TIMESTAMP - INTERVAL '1 day', 'Producto aprobado'),
(15, 2, 2, 'Cable USB-C Trenzado 2m', 'Cable de carga reforzado', 'https://images.unsplash.com/photo-1591488320449-011701bb6704', 35.00, 30, 1, 1, CURRENT_TIMESTAMP - INTERVAL '3 days', 'Producto aprobado'),
(15, 2, 3, 'Soporte Laptop Ajustable', 'Elevador ergonómico para portátil', 'https://images.unsplash.com/photo-1625948515291-69613efd103f', 140.00, 18, 1, 1, CURRENT_TIMESTAMP - INTERVAL '2 days', 'Producto aprobado'),
(15, 2, 2, 'Mousepad Gaming XXL', 'Alfombrilla grande con base antideslizante', 'https://images.unsplash.com/photo-1587829741301-dc798b83add3', 75.00, 22, 1, 1, CURRENT_TIMESTAMP - INTERVAL '1 day', 'Producto aprobado'),
(15, 2, 3, 'Adaptador USB-C a HDMI', 'Conversor multipuerto 4K', 'https://images.unsplash.com/photo-1625948515291-69613efd103f', 85.00, 20, 1, 1, CURRENT_TIMESTAMP - INTERVAL '3 days', 'Producto aprobado'),
(15, 2, 2, 'Protector de Pantalla Laptop', 'Film protector antirreflejos', 'https://images.unsplash.com/photo-1588872657578-7efd1f1555ed', 45.00, 28, 1, 1, CURRENT_TIMESTAMP - INTERVAL '2 days', 'Producto aprobado');

-- Usuario 7: Valentina Castro (id_usuario = 16)
INSERT INTO solicitudes_producto (id_vendedor, id_estado_solicitud, id_moderador, nombre_producto, descripcion, url_imagen, precio, stock, id_estado_producto, id_categoria, fecha_respuesta, comentario_moderador) VALUES
(16, 2, 2, 'Microondas LG 1.1 pies', 'Horno microondas con grill', 'https://images.unsplash.com/photo-1585515320310-259814833e62', 580.00, 5, 1, 2, CURRENT_TIMESTAMP - INTERVAL '2 days', 'Producto aprobado'),
(16, 2, 3, 'Aspiradora Robot', 'Aspiradora inteligente con mapeo', 'https://images.unsplash.com/photo-1558317374-067fb5f30001', 1400.00, 4, 1, 2, CURRENT_TIMESTAMP - INTERVAL '1 day', 'Producto aprobado'),
(16, 2, 2, 'Batidora de Mano KitchenAid', 'Batidora eléctrica 5 velocidades', 'https://images.unsplash.com/photo-1585515320310-259814833e62', 280.00, 10, 2, 2, CURRENT_TIMESTAMP - INTERVAL '3 days', 'Producto aprobado'),
(16, 2, 3, 'Juego de Vasos Cristal x12', 'Set de vasos para agua y jugo', 'https://images.unsplash.com/photo-1610701596007-11502861dcfa', 140.00, 15, 1, 2, CURRENT_TIMESTAMP - INTERVAL '2 days', 'Producto aprobado'),
(16, 2, 2, 'Olla de Presión Eléctrica', 'Olla programable 6 litros', 'https://images.unsplash.com/photo-1584990347449-39b4aa97f87d', 420.00, 8, 1, 2, CURRENT_TIMESTAMP - INTERVAL '1 day', 'Producto aprobado'),
(16, 2, 3, 'Manteles Individuales Set x6', 'Manteles de bambú lavables', 'https://images.unsplash.com/photo-1556911220-bff31c812dba', 95.00, 20, 1, 2, CURRENT_TIMESTAMP - INTERVAL '3 days', 'Producto aprobado'),
(16, 2, 2, 'Dispensador de Agua', 'Dispensador para garrafón frio/caliente', 'https://images.unsplash.com/photo-1600880292203-757bb62b4baf', 380.00, 6, 1, 2, CURRENT_TIMESTAMP - INTERVAL '2 days', 'Producto aprobado'),
(16, 2, 3, 'Set de Cubiertos 48 piezas', 'Cubiertos de acero inoxidable', 'https://images.unsplash.com/photo-1593618998160-e34014e67546', 320.00, 12, 1, 2, CURRENT_TIMESTAMP - INTERVAL '1 day', 'Producto aprobado'),
(16, 2, 2, 'Procesador de Alimentos', 'Picadora multifunción 10 tazas', 'https://images.unsplash.com/photo-1585515320310-259814833e62', 450.00, 7, 1, 2, CURRENT_TIMESTAMP - INTERVAL '3 days', 'Producto aprobado'),
(16, 2, 3, 'Tetera Eléctrica 1.7L', 'Hervidor de agua de acero inoxidable', 'https://images.unsplash.com/photo-1600880292203-757bb62b4baf', 180.00, 14, 1, 2, CURRENT_TIMESTAMP - INTERVAL '2 days', 'Producto aprobado');

-- Usuario 8: Andrés Guzmán (id_usuario = 17)
INSERT INTO solicitudes_producto (id_vendedor, id_estado_solicitud, id_moderador, nombre_producto, descripcion, url_imagen, precio, stock, id_estado_producto, id_categoria, fecha_respuesta, comentario_moderador) VALUES
(17, 2, 3, 'Fundamentos de Programación', 'Libro de introducción a la programación', 'https://images.unsplash.com/photo-1544947950-fa07a98d237f', 280.00, 8, 1, 3, CURRENT_TIMESTAMP - INTERVAL '2 days', 'Producto aprobado'),
(17, 2, 2, 'Base de Datos: Teoría y Práctica', 'Libro sobre diseño de bases de datos', 'https://images.unsplash.com/photo-1589998059171-988d887df646', 320.00, 6, 2, 3, CURRENT_TIMESTAMP - INTERVAL '1 day', 'Producto aprobado'),
(17, 2, 3, 'Regla T de Dibujo Técnico', 'Regla T profesional 60cm', 'https://images.unsplash.com/photo-1596495577886-d920f1fb7238', 120.00, 12, 1, 3, CURRENT_TIMESTAMP - INTERVAL '3 days', 'Producto aprobado'),
(17, 2, 2, 'Carpeta Archivadora x10', 'Set de carpetas con gancho', 'https://images.unsplash.com/photo-1588075592446-265fd1e6e76f', 85.00, 25, 1, 3, CURRENT_TIMESTAMP - INTERVAL '2 days', 'Producto aprobado'),
(17, 2, 3, 'Lampara de Escritorio LED', 'Luz ajustable con puerto USB', 'https://images.unsplash.com/photo-1507473885765-e6ed057f782c', 150.00, 15, 1, 3, CURRENT_TIMESTAMP - INTERVAL '1 day', 'Producto aprobado'),
(17, 2, 2, 'Portaminas Rotring 0.5mm', 'Portaminas técnico profesional', 'https://images.unsplash.com/photo-1586864387634-31f1d8f05b0b', 95.00, 20, 1, 3, CURRENT_TIMESTAMP - INTERVAL '3 days', 'Producto aprobado'),
(17, 2, 3, 'Manual de Estadística', 'Libro de estadística aplicada', 'https://images.unsplash.com/photo-1532012197267-da84d127e765', 260.00, 10, 1, 3, CURRENT_TIMESTAMP - INTERVAL '2 days', 'Producto aprobado'),
(17, 2, 2, 'Tijeras de Precisión', 'Tijeras para manualidades y corte fino', 'https://images.unsplash.com/photo-1593618998160-e34014e67546', 45.00, 30, 1, 3, CURRENT_TIMESTAMP - INTERVAL '1 day', 'Producto aprobado'),
(17, 2, 3, 'Engrapadora Industrial', 'Engrapadora de alta resistencia', 'https://images.unsplash.com/photo-1596495577886-d920f1fb7238', 140.00, 18, 1, 3, CURRENT_TIMESTAMP - INTERVAL '3 days', 'Producto aprobado'),
(17, 2, 2, 'Plumas Gel Pack x12', 'Set de plumas de colores variados', 'https://images.unsplash.com/photo-1586864387634-31f1d8f05b0b', 55.00, 28, 1, 3, CURRENT_TIMESTAMP - INTERVAL '2 days', 'Producto aprobado');

-- Usuario 9: Isabella Mendoza (id_usuario = 18)
INSERT INTO solicitudes_producto (id_vendedor, id_estado_solicitud, id_moderador, nombre_producto, descripcion, url_imagen, precio, stock, id_estado_producto, id_categoria, fecha_respuesta, comentario_moderador) VALUES
(18, 2, 2, 'Bolso de Mano Elegante', 'Cartera de cuero sintético', 'https://images.unsplash.com/photo-1591561954557-26941169b49e', 320.00, 10, 1, 4, CURRENT_TIMESTAMP - INTERVAL '2 days', 'Producto aprobado'),
(18, 2, 3, 'Set de Maquillaje Profesional', 'Kit completo con brochas y esponjas', 'https://images.unsplash.com/photo-1512496015851-a90fb38ba796', 280.00, 8, 1, 4, CURRENT_TIMESTAMP - INTERVAL '1 day', 'Producto aprobado'),
(18, 2, 2, 'Plancha de Cabello Cerámica', 'Plancha con control de temperatura', 'https://images.unsplash.com/photo-1522338140262-f46f5913618a', 220.00, 12, 1, 4, CURRENT_TIMESTAMP - INTERVAL '3 days', 'Producto aprobado'),
(18, 2, 3, 'Secadora de Cabello Iónica', 'Secador profesional 2000W', 'https://images.unsplash.com/photo-1522338140262-f46f5913618a', 280.00, 10, 1, 4, CURRENT_TIMESTAMP - INTERVAL '2 days', 'Producto aprobado'),
(18, 2, 2, 'Estuche de Cosméticos', 'Organizador portátil para maquillaje', 'https://images.unsplash.com/photo-1627123424574-724758594e93', 95.00, 20, 1, 4, CURRENT_TIMESTAMP - INTERVAL '1 day', 'Producto aprobado'),
(18, 2, 3, 'Espejo con Luz LED', 'Espejo de tocador con iluminación', 'https://images.unsplash.com/photo-1618220179428-22790b461013', 180.00, 15, 1, 4, CURRENT_TIMESTAMP - INTERVAL '3 days', 'Producto aprobado'),
(18, 2, 2, 'Kit de Cuidado Facial', 'Set de productos para skincare', 'https://images.unsplash.com/photo-1556228720-195a672e8a03', 340.00, 12, 1, 4, CURRENT_TIMESTAMP - INTERVAL '2 days', 'Producto aprobado'),
(18, 2, 3, 'Rizador de Cabello Automático', 'Rizador giratorio de cerámica', 'https://images.unsplash.com/photo-1522338140262-f46f5913618a', 320.00, 8, 1, 4, CURRENT_TIMESTAMP - INTERVAL '1 day', 'Producto aprobado'),
(18, 2, 2, 'Perfume Carolina Herrera', 'Fragancia femenina 80ml', 'https://images.unsplash.com/photo-1541643600914-78b084683601', 480.00, 6, 1, 4, CURRENT_TIMESTAMP - INTERVAL '3 days', 'Producto aprobado'),
(18, 2, 3, 'Juego de Pulseras Doradas', 'Set de 5 pulseras elegantes', 'https://images.unsplash.com/photo-1611652022419-a9419f74343a', 140.00, 18, 1, 4, CURRENT_TIMESTAMP - INTERVAL '2 days', 'Producto aprobado');

-- Usuario 10: Sebastián Rivas (id_usuario = 19)
INSERT INTO solicitudes_producto (id_vendedor, id_estado_solicitud, id_moderador, nombre_producto, descripcion, url_imagen, precio, stock, id_estado_producto, id_categoria, fecha_respuesta, comentario_moderador) VALUES
(19, 2, 3, 'Guitarra Acústica Yamaha', 'Guitarra clásica para principiantes', 'https://images.unsplash.com/photo-1510915361894-db8b60106cb1', 850.00, 5, 2, 6, CURRENT_TIMESTAMP - INTERVAL '2 days', 'Producto aprobado'),
(19, 2, 2, 'Set de Pesas Ajustables', 'Mancuernas con discos intercambiables', 'https://images.unsplash.com/photo-1517836357463-d25dfeac3438', 480.00, 8, 1, 6, CURRENT_TIMESTAMP - INTERVAL '1 day', 'Producto aprobado'),
(19, 2, 3, 'Patineta Profesional', 'Skateboard con rodamientos ABEC-7', 'https://images.unsplash.com/photo-1547447134-cd3f5c716030', 380.00, 10, 1, 6, CURRENT_TIMESTAMP - INTERVAL '3 days', 'Producto aprobado'),
(19, 2, 2, 'Balón de Basketball Spalding', 'Balón oficial tamaño 7', 'https://images.unsplash.com/photo-1519861531473-9200262188bf', 180.00, 15, 1, 6, CURRENT_TIMESTAMP - INTERVAL '2 days', 'Producto aprobado'),
(19, 2, 3, 'Yoga Mat Antideslizante', 'Tapete para yoga 6mm grosor', 'https://images.unsplash.com/photo-1601925260368-ae2f83cf8b7f', 95.00, 20, 1, 6, CURRENT_TIMESTAMP - INTERVAL '1 day', 'Producto aprobado'),
(19, 2, 2, 'Botella Deportiva 1L', 'Termo aislado para ejercicio', 'https://images.unsplash.com/photo-1602143407151-7111542de6e8', 85.00, 25, 1, 6, CURRENT_TIMESTAMP - INTERVAL '3 days', 'Producto aprobado'),
(19, 2, 3, 'Raqueta de Tenis Wilson', 'Raqueta profesional con funda', 'https://images.unsplash.com/photo-1622279457486-62759b8d5a2a', 420.00, 6, 2, 6, CURRENT_TIMESTAMP - INTERVAL '2 days', 'Producto aprobado'),
(19, 2, 2, 'Cuerda para Saltar', 'Cuerda de velocidad ajustable', 'https://images.unsplash.com/photo-1517836357463-d25dfeac3438', 45.00, 30, 1, 6, CURRENT_TIMESTAMP - INTERVAL '1 day', 'Producto aprobado'),
(19, 2, 3, 'Guantes de Box Everlast', 'Guantes profesionales 12oz', 'https://images.unsplash.com/photo-1517836357463-d25dfeac3438', 280.00, 12, 1, 6, CURRENT_TIMESTAMP - INTERVAL '3 days', 'Producto aprobado'),
(19, 2, 2, 'Banda Elástica Fitness Set x3', 'Bandas de resistencia variadas', 'https://images.unsplash.com/photo-1598289431512-b97b0917affc', 120.00, 18, 1, 6, CURRENT_TIMESTAMP - INTERVAL '2 days', 'Producto aprobado');

-- =====================================================
-- 4. INSERCIÓN DE PRODUCTOS
-- (Depende de: solicitudes_producto aprobadas)
-- =====================================================

-- Insertar productos basados en las solicitudes aprobadas
INSERT INTO productos (id_vendedor, id_solicitud, nombre_producto, descripcion, url_imagen, precio, stock, id_estado_producto, id_categoria, fecha_publicacion)
SELECT 
    id_vendedor,
    id_solicitud,
    nombre_producto,
    descripcion,
    url_imagen,
    precio,
    stock,
    id_estado_producto,
    id_categoria,
    fecha_respuesta
FROM solicitudes_producto
WHERE id_estado_solicitud = 2;

-- =====================================================
-- 5. INSERCIÓN DE CARRITOS
-- (Depende de: usuarios)
-- =====================================================

INSERT INTO carritos (id_usuario) VALUES
(10), (11), (12), (13), (14), (15), (16), (17), (18), (19);

-- =====================================================
-- 6. INSERCIÓN DE TARJETAS DE CRÉDITO
-- (Depende de: usuarios)
-- =====================================================

-- Nota: En producción, estos datos deben estar encriptados
INSERT INTO tarjetas_credito (id_usuario, numero_tarjeta_encriptado, nombre_titular, fecha_expiracion, cvv_encriptado, tipo_tarjeta, es_principal) VALUES
(10, 'ENC_4532123456789012', 'Andrea Jiménez García', '2026-12-31', 'ENC_123', 'VISA', TRUE),
(11, 'ENC_5412345678901234', 'Fernando Silva Martínez', '2027-06-30', 'ENC_456', 'MASTERCARD', TRUE),
(12, 'ENC_4916234567890123', 'Gabriela Ortiz Cruz', '2026-09-30', 'ENC_789', 'VISA', TRUE),
(13, 'ENC_5234567890123456', 'Ricardo Vásquez León', '2027-03-31', 'ENC_321', 'MASTERCARD', TRUE),
(14, 'ENC_378282246310005', 'Sofía Herrera Pérez', '2026-11-30', 'ENC_654', 'AMEX', TRUE),
(15, 'ENC_4539876543210987', 'Diego Moreno Rojas', '2027-08-31', 'ENC_987', 'VISA', TRUE),
(16, 'ENC_5187654321098765', 'Valentina Castro Mejía', '2026-07-31', 'ENC_147', 'MASTERCARD', TRUE),
(17, 'ENC_4716543210987654', 'Andrés Guzmán Flores', '2027-05-31', 'ENC_258', 'VISA', TRUE),
(18, 'ENC_371449635398431', 'Isabella Mendoza Vargas', '2026-10-31', 'ENC_369', 'AMEX', TRUE),
(19, 'ENC_5298765432109876', 'Sebastián Rivas Aguilar', '2027-04-30', 'ENC_741', 'MASTERCARD', TRUE);

-- =====================================================
-- 7. INSERCIÓN DE PEDIDOS
-- (Depende de: usuarios, tarjetas_credito, estados_pedido)
-- =====================================================

-- Pedido 1: Andrea compra productos de Fernando (ENTREGADO)
INSERT INTO pedidos (id_comprador, id_tarjeta, id_estado_pedido, total, fecha_pedido, fecha_entrega_estimada, fecha_entrega_real, direccion_entrega) VALUES
(10, 1, 2, 665.00, CURRENT_TIMESTAMP - INTERVAL '15 days', CURRENT_DATE - INTERVAL '10 days', CURRENT_TIMESTAMP - INTERVAL '9 days', 'Zona 11, Ciudad de Guatemala');

-- Pedido 2: Fernando compra productos de Gabriela (EN CURSO)
INSERT INTO pedidos (id_comprador, id_tarjeta, id_estado_pedido, total, fecha_pedido, fecha_entrega_estimada, direccion_entrega) VALUES
(11, 2, 1, 825.00, CURRENT_TIMESTAMP - INTERVAL '2 days', CURRENT_DATE + INTERVAL '3 days', 'Zona 13, Ciudad de Guatemala');

-- Pedido 3: Gabriela compra productos de Ricardo (ENTREGADO)
INSERT INTO pedidos (id_comprador, id_tarjeta, id_estado_pedido, total, fecha_pedido, fecha_entrega_estimada, fecha_entrega_real, direccion_entrega) VALUES
(12, 3, 2, 1195.00, CURRENT_TIMESTAMP - INTERVAL '12 days', CURRENT_DATE - INTERVAL '8 days', CURRENT_TIMESTAMP - INTERVAL '7 days', 'Zona 7, Ciudad de Guatemala');

-- Pedido 4: Ricardo compra productos de Sofía (EN CURSO)
INSERT INTO pedidos (id_comprador, id_tarjeta, id_estado_pedido, total, fecha_pedido, fecha_entrega_estimada, direccion_entrega) VALUES
(13, 4, 1, 1095.00, CURRENT_TIMESTAMP - INTERVAL '1 day', CURRENT_DATE + INTERVAL '4 days', 'Zona 4, Ciudad de Guatemala');

-- Pedido 5: Sofía compra productos de Diego (EN CURSO)
INSERT INTO pedidos (id_comprador, id_tarjeta, id_estado_pedido, total, fecha_pedido, fecha_entrega_estimada, direccion_entrega) VALUES
(14, 5, 1, 2405.00, CURRENT_TIMESTAMP - INTERVAL '3 days', CURRENT_DATE + INTERVAL '2 days', 'Zona 14, Ciudad de Guatemala');

-- =====================================================
-- 8. INSERCIÓN DE DETALLE_PEDIDO
-- (Depende de: pedidos, productos, usuarios)
-- =====================================================

-- Detalle Pedido 1: Andrea compra de Fernando
INSERT INTO detalle_pedido (id_pedido, id_producto, id_vendedor, cantidad, precio_unitario, subtotal, comision_plataforma, ganancia_vendedor) VALUES
(1, 11, 11, 1, 280.00, 280.00, 14.00, 266.00),
(1, 14, 11, 1, 180.00, 180.00, 9.00, 171.00),
(1, 17, 11, 1, 85.00, 85.00, 4.25, 80.75),
(1, 19, 11, 2, 60.00, 120.00, 6.00, 114.00);

-- Detalle Pedido 2: Fernando compra de Gabriela
INSERT INTO detalle_pedido (id_pedido, id_producto, id_vendedor, cantidad, precio_unitario, subtotal, comision_plataforma, ganancia_vendedor) VALUES
(2, 21, 12, 1, 250.00, 250.00, 12.50, 237.50),
(2, 24, 12, 1, 200.00, 200.00, 10.00, 190.00),
(2, 26, 12, 2, 90.00, 180.00, 9.00, 171.00),
(2, 28, 12, 1, 75.00, 75.00, 3.75, 71.25),
(2, 29, 12, 1, 120.00, 120.00, 6.00, 114.00);

-- Detalle Pedido 3: Gabriela compra de Ricardo
INSERT INTO detalle_pedido (id_pedido, id_producto, id_vendedor, cantidad, precio_unitario, subtotal, comision_plataforma, ganancia_vendedor) VALUES
(3, 31, 13, 1, 420.00, 420.00, 21.00, 399.00),
(3, 33, 13, 1, 180.00, 180.00, 9.00, 171.00),
(3, 37, 13, 1, 220.00, 220.00, 11.00, 209.00),
(3, 38, 13, 1, 280.00, 280.00, 14.00, 266.00),
(3, 36, 13, 1, 95.00, 95.00, 4.75, 90.25);

-- Detalle Pedido 4: Ricardo compra de Sofía
INSERT INTO detalle_pedido (id_pedido, id_producto, id_vendedor, cantidad, precio_unitario, subtotal, comision_plataforma, ganancia_vendedor) VALUES
(4, 41, 14, 1, 450.00, 450.00, 22.50, 427.50),
(4, 42, 14, 1, 380.00, 380.00, 19.00, 361.00),
(4, 45, 14, 1, 140.00, 140.00, 7.00, 133.00),
(4, 49, 14, 1, 85.00, 85.00, 4.25, 80.75),
(4, 36, 13, 1, 40.00, 40.00, 2.00, 38.00);

-- Detalle Pedido 5: Sofía compra de Diego
INSERT INTO detalle_pedido (id_pedido, id_producto, id_vendedor, cantidad, precio_unitario, subtotal, comision_plataforma, ganancia_vendedor) VALUES
(5, 51, 15, 1, 1200.00, 1200.00, 60.00, 1140.00),
(5, 52, 15, 1, 950.00, 950.00, 47.50, 902.50),
(5, 54, 15, 1, 180.00, 180.00, 9.00, 171.00),
(5, 55, 15, 1, 75.00, 75.00, 3.75, 71.25);

-- =====================================================
-- 9. INSERCIÓN DE CALIFICACIONES
-- (Depende de: productos, usuarios, pedidos)
-- =====================================================

INSERT INTO calificaciones (id_producto, id_usuario, id_pedido, puntuacion, comentario) VALUES
(11, 10, 1, 5, 'Excelente producto, llegó en perfectas condiciones'),
(14, 10, 1, 4, 'Buen producto, calidad precio aceptable'),
(17, 10, 1, 5, 'Muy útil y de buena calidad'),
(31, 12, 3, 5, 'Perfume original, muy contenta con la compra'),
(33, 12, 3, 4, 'Buena cartera, el cuero se ve de calidad'),
(38, 12, 3, 5, 'Excelente mochila, muy espaciosa');
-- =====================================================
-- 10. INSERCIÓN DE SANCIONES
-- (Depende de: usuarios, estados_sancion)
-- =====================================================

INSERT INTO sanciones (id_usuario_sancionado, id_moderador, id_estado_sancion, motivo, descripcion, fecha_inicio, fecha_fin, dias_suspension) VALUES
(15, 2, 2, 'Producto prohibido', 'Publicación de productos que violan las políticas de la plataforma', CURRENT_TIMESTAMP - INTERVAL '30 days', CURRENT_TIMESTAMP - INTERVAL '20 days', 10),
(17, 3, 2, 'Incumplimiento de entrega', 'Retrasos constantes en la entrega de productos sin justificación válida', CURRENT_TIMESTAMP - INTERVAL '25 days', CURRENT_TIMESTAMP - INTERVAL '18 days', 7),
(10, 2, 1, 'Comportamiento inadecuado', 'Comentarios ofensivos y lenguaje inapropiado en calificaciones de productos', CURRENT_TIMESTAMP - INTERVAL '5 days', CURRENT_TIMESTAMP + INTERVAL '2 days', 7);
-- =====================================================
-- 11. INSERCIÓN DE NOTIFICACIONES
-- (Depende de: usuarios, tipos_notificacion)
-- =====================================================

INSERT INTO notificaciones (id_usuario, id_tipo_notificacion, asunto, mensaje, id_referencia) VALUES
(10, 1, 'Pedido entregado #1', 'Tu pedido #1 ha sido entregado exitosamente. Gracias por tu compra en eCommerce GT.', 1),
(12, 1, 'Pedido entregado #3', 'Tu pedido #3 ha sido entregado exitosamente. Gracias por confiar en nosotros.', 3),
(10, 2, 'Producto aprobado', 'Tu producto "Laptop HP Pavilion 15" ha sido aprobado y está visible en la plataforma.', 1),
(10, 2, 'Producto aprobado', 'Tu producto "Mouse Inalámbrico Logitech" ha sido aprobado.', 2),
(11, 2, 'Producto aprobado', 'Tu producto "Juego de Sartenes Antiadherentes" ha sido aprobado.', 11),
(12, 2, 'Producto aprobado', 'Tu producto "Cálculo de una Variable" ha sido aprobado.', 21),
(11, 1, 'Pedido en camino #2', 'Tu pedido #2 está en camino. Fecha estimada de entrega: ' || TO_CHAR(CURRENT_DATE + INTERVAL '3 days', 'DD/MM/YYYY'), 2),
(13, 1, 'Pedido en camino #4', 'Tu pedido #4 está siendo procesado para entrega.', 4),
(14, 1, 'Pedido en camino #5', 'Tu pedido #5 está en ruta de entrega.', 5);

-- =====================================================
-- 12. SOLICITUDES PENDIENTES Y RECHAZADAS
-- (Depende de: usuarios, estados_solicitud, estados_producto, categorias)
-- =====================================================

-- Solicitudes PENDIENTES (estado 1)
INSERT INTO solicitudes_producto (id_vendedor, id_estado_solicitud, nombre_producto, descripcion, url_imagen, precio, stock, id_estado_producto, id_categoria) VALUES
(10, 1, 'iPhone 15 Pro', 'Teléfono nuevo en caja sellada', 'https://images.unsplash.com/photo-1695048133142-1a20484d2569', 5200.00, 2, 1, 1),
(11, 1, 'Juego de Ollas Premium', 'Set completo de ollas de acero inoxidable', 'https://images.unsplash.com/photo-1584990347449-39b4aa97f87d', 680.00, 5, 1, 2),
(14, 1, 'Pintura Original de Paisaje', 'Cuadro al óleo pintado a mano', 'https://images.unsplash.com/photo-1513519245088-0e12902e5a38', 2500.00, 1, 1, 5);

-- Solicitudes RECHAZADAS (estado 3)
INSERT INTO solicitudes_producto (id_vendedor, id_estado_solicitud, id_moderador, nombre_producto, descripcion, url_imagen, precio, stock, id_estado_producto, id_categoria, fecha_respuesta, comentario_moderador) VALUES
(12, 3, 2, 'Calculadora Graficadora', 'Calculadora usada con rayones', 'https://images.unsplash.com/photo-1611625618270-e4f35a3c91d8', 350.00, 1, 2, 3, CURRENT_TIMESTAMP - INTERVAL '1 day', 'Producto con descripción inadecuada del estado. Rayones no mencionados claramente.'),
(15, 3, 3, 'Producto sin marca', 'Artículo genérico sin especificaciones', 'https://images.unsplash.com/photo-1505740420928-5e560c06d30e', 50.00, 10, 1, 6, CURRENT_TIMESTAMP - INTERVAL '2 days', 'Descripción insuficiente del producto. Favor de agregar más detalles.');

-- =====================================================
-- 13. ALGUNOS ITEMS EN CARRITOS (OPCIONAL)
-- (Depende de: carritos, productos)
-- =====================================================

-- Andrea tiene algunos productos en su carrito
INSERT INTO items_carrito (id_carrito, id_producto, cantidad, precio_unitario) VALUES
(1, 61, 1, 1400.00),  -- Aspiradora Robot de Valentina
(1, 71, 1, 280.00);   -- Fundamentos de Programación de Andrés

-- Fernando tiene productos en su carrito
INSERT INTO items_carrito (id_carrito, id_producto, cantidad, precio_unitario) VALUES
(2, 81, 1, 320.00),   -- Bolso de Isabella
(2, 92, 2, 480.00);   -- Set de Pesas de Sebastián

-- Gabriela explorando productos
INSERT INTO items_carrito (id_carrito, id_producto, cantidad, precio_unitario) VALUES
(3, 50, 1, 280.00);   -- Cortinas de Sofía

-- =====================================================
-- 14. CONSULTA DE VERIFICACIÓN
-- =====================================================

SELECT 
    'RESUMEN DE DATOS INSERTADOS' AS reporte,
    '=============================' AS separador
UNION ALL
SELECT 'Roles:', COUNT(*)::TEXT FROM roles
UNION ALL
SELECT 'Usuarios totales:', COUNT(*)::TEXT FROM usuarios
UNION ALL
SELECT '  - Administradores:', COUNT(*)::TEXT FROM usuarios WHERE id_rol = 4
UNION ALL
SELECT '  - Moderadores:', COUNT(*)::TEXT FROM usuarios WHERE id_rol = 2
UNION ALL
SELECT '  - Logística:', COUNT(*)::TEXT FROM usuarios WHERE id_rol = 3
UNION ALL
SELECT '  - Comunes:', COUNT(*)::TEXT FROM usuarios WHERE id_rol = 1
UNION ALL
SELECT '=============================' AS separador, '' AS col2
UNION ALL
SELECT 'Solicitudes totales:', COUNT(*)::TEXT FROM solicitudes_producto
UNION ALL
SELECT '  - Aprobadas:', COUNT(*)::TEXT FROM solicitudes_producto WHERE id_estado_solicitud = 2
UNION ALL
SELECT '  - Pendientes:', COUNT(*)::TEXT FROM solicitudes_producto WHERE id_estado_solicitud = 1
UNION ALL
SELECT '  - Rechazadas:', COUNT(*)::TEXT FROM solicitudes_producto WHERE id_estado_solicitud = 3
UNION ALL
SELECT '=============================' AS separador, '' AS col2
UNION ALL
SELECT 'Productos publicados:', COUNT(*)::TEXT FROM productos WHERE activo = TRUE
UNION ALL
SELECT 'Categorías:', COUNT(*)::TEXT FROM categorias
UNION ALL
SELECT '=============================' AS separador, '' AS col2
UNION ALL
SELECT 'Carritos creados:', COUNT(*)::TEXT FROM carritos
UNION ALL
SELECT 'Items en carritos:', COUNT(*)::TEXT FROM items_carrito
UNION ALL
SELECT 'Tarjetas registradas:', COUNT(*)::TEXT FROM tarjetas_credito
UNION ALL
SELECT '=============================' AS separador, '' AS col2
UNION ALL
SELECT 'Pedidos totales:', COUNT(*)::TEXT FROM pedidos
UNION ALL
SELECT '  - En curso:', COUNT(*)::TEXT FROM pedidos WHERE id_estado_pedido = 1
UNION ALL
SELECT '  - Entregados:', COUNT(*)::TEXT FROM pedidos WHERE id_estado_pedido = 2
UNION ALL
SELECT 'Detalles de pedidos:', COUNT(*)::TEXT FROM detalle_pedido
UNION ALL
SELECT '=============================' AS separador, '' AS col2
UNION ALL
SELECT 'Calificaciones:', COUNT(*)::TEXT FROM calificaciones
UNION ALL
SELECT 'Notificaciones enviadas:', COUNT(*)::TEXT FROM notificaciones
UNION ALL
SELECT 'Sanciones históricas:', COUNT(*)::TEXT FROM sanciones
UNION ALL
SELECT '=============================' AS separador, '' AS col2
UNION ALL
SELECT 'Total registros:', 
    (SELECT COUNT(*) FROM usuarios) + 
    (SELECT COUNT(*) FROM productos) + 
    (SELECT COUNT(*) FROM pedidos) + 
    (SELECT COUNT(*) FROM calificaciones) ||
    ' (aprox)' AS cantidad;

-- =====================================================
-- 15. VERIFICACIÓN DE INTEGRIDAD REFERENCIAL
-- =====================================================

-- Verificar que no hay productos huérfanos
SELECT 
    'Verificación de Integridad' AS tipo,
    '===========================' AS detalle
UNION ALL
SELECT 
    'Productos sin vendedor:' AS tipo,
    COUNT(*)::TEXT AS detalle
FROM productos p
LEFT JOIN usuarios u ON p.id_vendedor = u.id_usuario
WHERE u.id_usuario IS NULL
UNION ALL
SELECT 
    'Pedidos sin comprador:' AS tipo,
    COUNT(*)::TEXT AS detalle
FROM pedidos p
LEFT JOIN usuarios u ON p.id_comprador = u.id_usuario
WHERE u.id_usuario IS NULL
UNION ALL
SELECT 
    'Carritos sin usuario:' AS tipo,
    COUNT(*)::TEXT AS detalle
FROM carritos c
LEFT JOIN usuarios u ON c.id_usuario = u.id_usuario
WHERE u.id_usuario IS NULL;

-- =====================================================
-- FIN DEL SCRIPT DML
-- =====================================================
