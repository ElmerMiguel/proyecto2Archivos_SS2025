-- =====================================================
-- E-COMMERCE GT - DATABASE SCHEMA
-- PostgreSQL DDL Script
-- Normalizado hasta 3FN
-- =====================================================

-- Eliminar tablas existentes (en orden inverso por dependencias)
DROP TABLE IF EXISTS notificaciones CASCADE;
DROP TABLE IF EXISTS sanciones CASCADE;
DROP TABLE IF EXISTS calificaciones CASCADE;
DROP TABLE IF EXISTS detalle_pedido CASCADE;
DROP TABLE IF EXISTS pedidos CASCADE;
DROP TABLE IF EXISTS items_carrito CASCADE;
DROP TABLE IF EXISTS carritos CASCADE;
DROP TABLE IF EXISTS tarjetas_credito CASCADE;
DROP TABLE IF EXISTS productos CASCADE;
DROP TABLE IF EXISTS solicitudes_producto CASCADE;
DROP TABLE IF EXISTS categorias CASCADE;
DROP TABLE IF EXISTS usuarios CASCADE;
DROP TABLE IF EXISTS roles CASCADE;
DROP TABLE IF EXISTS estados_producto CASCADE;
DROP TABLE IF EXISTS estados_pedido CASCADE;
DROP TABLE IF EXISTS estados_solicitud CASCADE;
DROP TABLE IF EXISTS estados_sancion CASCADE;
DROP TABLE IF EXISTS tipos_notificacion CASCADE;

-- =====================================================
-- TABLAS DE CATÁLOGOS (Tablas de referencia)
-- =====================================================

-- Tabla: roles
CREATE TABLE roles (
    id_rol SERIAL PRIMARY KEY,
    nombre_rol VARCHAR(50) NOT NULL UNIQUE,
    descripcion TEXT,
    CONSTRAINT chk_roles_nombre CHECK (nombre_rol IN ('COMUN', 'MODERADOR', 'LOGISTICA', 'ADMINISTRADOR'))
);

-- Tabla: estados_producto
CREATE TABLE estados_producto (
    id_estado_producto SERIAL PRIMARY KEY,
    nombre_estado VARCHAR(20) NOT NULL UNIQUE,
    CONSTRAINT chk_estado_prod CHECK (nombre_estado IN ('NUEVO', 'USADO'))
);

-- Tabla: categorias
CREATE TABLE categorias (
    id_categoria SERIAL PRIMARY KEY,
    nombre_categoria VARCHAR(50) NOT NULL UNIQUE,
    descripcion TEXT,
    CONSTRAINT chk_categorias CHECK (nombre_categoria IN ('TECNOLOGIA', 'HOGAR', 'ACADEMICO', 'PERSONAL', 'DECORACION', 'OTRO'))
);

-- Tabla: estados_pedido
CREATE TABLE estados_pedido (
    id_estado_pedido SERIAL PRIMARY KEY,
    nombre_estado VARCHAR(20) NOT NULL UNIQUE,
    CONSTRAINT chk_estado_pedido CHECK (nombre_estado IN ('EN_CURSO', 'ENTREGADO'))
);

-- Tabla: estados_solicitud
CREATE TABLE estados_solicitud (
    id_estado_solicitud SERIAL PRIMARY KEY,
    nombre_estado VARCHAR(20) NOT NULL UNIQUE,
    CONSTRAINT chk_estado_solicitud CHECK (nombre_estado IN ('PENDIENTE', 'APROBADO', 'RECHAZADO'))
);

-- Tabla: estados_sancion
CREATE TABLE estados_sancion (
    id_estado_sancion SERIAL PRIMARY KEY,
    nombre_estado VARCHAR(20) NOT NULL UNIQUE,
    CONSTRAINT chk_estado_sancion CHECK (nombre_estado IN ('ACTIVA', 'FINALIZADA'))
);

-- Tabla: tipos_notificacion
CREATE TABLE tipos_notificacion (
    id_tipo_notificacion SERIAL PRIMARY KEY,
    nombre_tipo VARCHAR(50) NOT NULL UNIQUE,
    CONSTRAINT chk_tipo_notif CHECK (nombre_tipo IN ('CAMBIO_ESTADO_PEDIDO', 'PRODUCTO_APROBADO', 'PRODUCTO_RECHAZADO'))
);

-- =====================================================
-- TABLAS PRINCIPALES
-- =====================================================

-- Tabla: usuarios
CREATE TABLE usuarios (
    id_usuario SERIAL PRIMARY KEY,
    id_rol INTEGER NOT NULL,
    nombre_completo VARCHAR(200) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    telefono VARCHAR(20),
    direccion TEXT,
    fecha_registro TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    suspendido BOOLEAN NOT NULL DEFAULT FALSE,
    fecha_suspension TIMESTAMP,
    CONSTRAINT fk_usuarios_roles FOREIGN KEY (id_rol) REFERENCES roles(id_rol),
    CONSTRAINT chk_email_formato CHECK (email ~* '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$')
);

-- Tabla: tarjetas_credito
CREATE TABLE tarjetas_credito (
    id_tarjeta SERIAL PRIMARY KEY,
    id_usuario INTEGER NOT NULL,
    numero_tarjeta_encriptado VARCHAR(255) NOT NULL,
    nombre_titular VARCHAR(200) NOT NULL,
    fecha_expiracion DATE NOT NULL,
    cvv_encriptado VARCHAR(255) NOT NULL,
    tipo_tarjeta VARCHAR(20) NOT NULL,
    es_principal BOOLEAN NOT NULL DEFAULT FALSE,
    fecha_agregada TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_tarjetas_usuarios FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario) ON DELETE CASCADE,
    CONSTRAINT chk_tipo_tarjeta CHECK (tipo_tarjeta IN ('VISA', 'MASTERCARD', 'AMEX'))
);

-- Tabla: solicitudes_producto
CREATE TABLE solicitudes_producto (
    id_solicitud SERIAL PRIMARY KEY,
    id_vendedor INTEGER NOT NULL,
    id_estado_solicitud INTEGER NOT NULL,
    id_moderador INTEGER,
    nombre_producto VARCHAR(200) NOT NULL,
    descripcion TEXT NOT NULL,
    url_imagen VARCHAR(500),
    precio DECIMAL(10, 2) NOT NULL,
    stock INTEGER NOT NULL,
    id_estado_producto INTEGER NOT NULL,
    id_categoria INTEGER NOT NULL,
    fecha_solicitud TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    fecha_respuesta TIMESTAMP,
    comentario_moderador TEXT,
    CONSTRAINT fk_solicitud_vendedor FOREIGN KEY (id_vendedor) REFERENCES usuarios(id_usuario),
    CONSTRAINT fk_solicitud_estado FOREIGN KEY (id_estado_solicitud) REFERENCES estados_solicitud(id_estado_solicitud),
    CONSTRAINT fk_solicitud_moderador FOREIGN KEY (id_moderador) REFERENCES usuarios(id_usuario),
    CONSTRAINT fk_solicitud_estado_prod FOREIGN KEY (id_estado_producto) REFERENCES estados_producto(id_estado_producto),
    CONSTRAINT fk_solicitud_categoria FOREIGN KEY (id_categoria) REFERENCES categorias(id_categoria),
    CONSTRAINT chk_precio_positivo CHECK (precio > 0),
    CONSTRAINT chk_stock_minimo CHECK (stock >= 1)
);

-- Tabla: productos
CREATE TABLE productos (
    id_producto SERIAL PRIMARY KEY,
    id_vendedor INTEGER NOT NULL,
    id_solicitud INTEGER,
    nombre_producto VARCHAR(200) NOT NULL,
    descripcion TEXT NOT NULL,
    url_imagen VARCHAR(500),
    precio DECIMAL(10, 2) NOT NULL,
    stock INTEGER NOT NULL,
    id_estado_producto INTEGER NOT NULL,
    id_categoria INTEGER NOT NULL,
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    fecha_publicacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    fecha_ultima_actualizacion TIMESTAMP,
    CONSTRAINT fk_productos_vendedor FOREIGN KEY (id_vendedor) REFERENCES usuarios(id_usuario),
    CONSTRAINT fk_productos_solicitud FOREIGN KEY (id_solicitud) REFERENCES solicitudes_producto(id_solicitud),
    CONSTRAINT fk_productos_estado FOREIGN KEY (id_estado_producto) REFERENCES estados_producto(id_estado_producto),
    CONSTRAINT fk_productos_categoria FOREIGN KEY (id_categoria) REFERENCES categorias(id_categoria),
    CONSTRAINT chk_precio_producto CHECK (precio > 0),
    CONSTRAINT chk_stock_producto CHECK (stock >= 0)
);

-- Tabla: carritos
CREATE TABLE carritos (
    id_carrito SERIAL PRIMARY KEY,
    id_usuario INTEGER NOT NULL UNIQUE,
    fecha_creacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    fecha_ultima_modificacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_carritos_usuario FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario) ON DELETE CASCADE
);

-- Tabla: items_carrito
CREATE TABLE items_carrito (
    id_item_carrito SERIAL PRIMARY KEY,
    id_carrito INTEGER NOT NULL,
    id_producto INTEGER NOT NULL,
    cantidad INTEGER NOT NULL,
    precio_unitario DECIMAL(10, 2) NOT NULL,
    fecha_agregado TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_items_carrito FOREIGN KEY (id_carrito) REFERENCES carritos(id_carrito) ON DELETE CASCADE,
    CONSTRAINT fk_items_producto FOREIGN KEY (id_producto) REFERENCES productos(id_producto),
    CONSTRAINT chk_cantidad_positiva CHECK (cantidad > 0),
    CONSTRAINT chk_precio_unitario CHECK (precio_unitario > 0),
    CONSTRAINT uq_carrito_producto UNIQUE (id_carrito, id_producto)
);

-- Tabla: pedidos
CREATE TABLE pedidos (
    id_pedido SERIAL PRIMARY KEY,
    id_comprador INTEGER NOT NULL,
    id_tarjeta INTEGER NOT NULL,
    id_estado_pedido INTEGER NOT NULL,
    total DECIMAL(12, 2) NOT NULL,
    fecha_pedido TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    fecha_entrega_estimada DATE NOT NULL,
    fecha_entrega_real TIMESTAMP,
    direccion_entrega TEXT NOT NULL,
    CONSTRAINT fk_pedidos_comprador FOREIGN KEY (id_comprador) REFERENCES usuarios(id_usuario),
    CONSTRAINT fk_pedidos_tarjeta FOREIGN KEY (id_tarjeta) REFERENCES tarjetas_credito(id_tarjeta),
    CONSTRAINT fk_pedidos_estado FOREIGN KEY (id_estado_pedido) REFERENCES estados_pedido(id_estado_pedido),
    CONSTRAINT chk_total_positivo CHECK (total > 0)
);

-- Tabla: detalle_pedido
CREATE TABLE detalle_pedido (
    id_detalle_pedido SERIAL PRIMARY KEY,
    id_pedido INTEGER NOT NULL,
    id_producto INTEGER NOT NULL,
    id_vendedor INTEGER NOT NULL,
    cantidad INTEGER NOT NULL,
    precio_unitario DECIMAL(10, 2) NOT NULL,
    subtotal DECIMAL(12, 2) NOT NULL,
    comision_plataforma DECIMAL(10, 2) NOT NULL,
    ganancia_vendedor DECIMAL(12, 2) NOT NULL,
    CONSTRAINT fk_detalle_pedido FOREIGN KEY (id_pedido) REFERENCES pedidos(id_pedido) ON DELETE CASCADE,
    CONSTRAINT fk_detalle_producto FOREIGN KEY (id_producto) REFERENCES productos(id_producto),
    CONSTRAINT fk_detalle_vendedor FOREIGN KEY (id_vendedor) REFERENCES usuarios(id_usuario),
    CONSTRAINT chk_detalle_cantidad CHECK (cantidad > 0),
    CONSTRAINT chk_detalle_precio CHECK (precio_unitario > 0),
    CONSTRAINT chk_detalle_subtotal CHECK (subtotal > 0),
    CONSTRAINT chk_detalle_comision CHECK (comision_plataforma >= 0),
    CONSTRAINT chk_detalle_ganancia CHECK (ganancia_vendedor >= 0)
);

-- Tabla: calificaciones
CREATE TABLE calificaciones (
    id_calificacion SERIAL PRIMARY KEY,
    id_producto INTEGER NOT NULL,
    id_usuario INTEGER NOT NULL,
    id_pedido INTEGER NOT NULL,
    puntuacion INTEGER NOT NULL,
    comentario TEXT,
    fecha_calificacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_calificacion_producto FOREIGN KEY (id_producto) REFERENCES productos(id_producto),
    CONSTRAINT fk_calificacion_usuario FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario),
    CONSTRAINT fk_calificacion_pedido FOREIGN KEY (id_pedido) REFERENCES pedidos(id_pedido),
    CONSTRAINT chk_puntuacion_rango CHECK (puntuacion >= 1 AND puntuacion <= 5),
    CONSTRAINT uq_calificacion_usuario_pedido_producto UNIQUE (id_usuario, id_pedido, id_producto)
);

-- Tabla: sanciones
CREATE TABLE sanciones (
    id_sancion SERIAL PRIMARY KEY,
    id_usuario_sancionado INTEGER NOT NULL,
    id_moderador INTEGER NOT NULL,
    id_estado_sancion INTEGER NOT NULL,
    motivo TEXT NOT NULL,
    fecha_inicio TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    fecha_fin TIMESTAMP,
    dias_suspension INTEGER,
    CONSTRAINT fk_sancion_usuario FOREIGN KEY (id_usuario_sancionado) REFERENCES usuarios(id_usuario),
    CONSTRAINT fk_sancion_moderador FOREIGN KEY (id_moderador) REFERENCES usuarios(id_usuario),
    CONSTRAINT fk_sancion_estado FOREIGN KEY (id_estado_sancion) REFERENCES estados_sancion(id_estado_sancion),
    CONSTRAINT chk_dias_suspension CHECK (dias_suspension > 0)
);

-- Tabla: notificaciones
CREATE TABLE notificaciones (
    id_notificacion SERIAL PRIMARY KEY,
    id_usuario INTEGER NOT NULL,
    id_tipo_notificacion INTEGER NOT NULL,
    asunto VARCHAR(200) NOT NULL,
    mensaje TEXT NOT NULL,
    fecha_envio TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    enviado_exitosamente BOOLEAN NOT NULL DEFAULT TRUE,
    id_referencia INTEGER,
    CONSTRAINT fk_notificacion_usuario FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario) ON DELETE CASCADE,
    CONSTRAINT fk_notificacion_tipo FOREIGN KEY (id_tipo_notificacion) REFERENCES tipos_notificacion(id_tipo_notificacion)
);

-- =====================================================
-- ÍNDICES PARA OPTIMIZACIÓN
-- =====================================================

-- Índices para búsquedas frecuentes
CREATE INDEX idx_usuarios_email ON usuarios(email);
CREATE INDEX idx_usuarios_rol ON usuarios(id_rol);
CREATE INDEX idx_productos_vendedor ON productos(id_vendedor);
CREATE INDEX idx_productos_categoria ON productos(id_categoria);
CREATE INDEX idx_productos_activo ON productos(activo);
CREATE INDEX idx_pedidos_comprador ON pedidos(id_comprador);
CREATE INDEX idx_pedidos_estado ON pedidos(id_estado_pedido);
CREATE INDEX idx_pedidos_fecha ON pedidos(fecha_pedido);
CREATE INDEX idx_detalle_pedido ON detalle_pedido(id_pedido);
CREATE INDEX idx_detalle_vendedor ON detalle_pedido(id_vendedor);
CREATE INDEX idx_calificaciones_producto ON calificaciones(id_producto);
CREATE INDEX idx_solicitudes_estado ON solicitudes_producto(id_estado_solicitud);
CREATE INDEX idx_solicitudes_vendedor ON solicitudes_producto(id_vendedor);
CREATE INDEX idx_sanciones_usuario ON sanciones(id_usuario_sancionado);
CREATE INDEX idx_notificaciones_usuario ON notificaciones(id_usuario);

-- =====================================================
-- COMENTARIOS EN TABLAS
-- =====================================================

COMMENT ON TABLE roles IS 'Catálogo de roles de usuario en el sistema';
COMMENT ON TABLE usuarios IS 'Información de todos los usuarios del sistema';
COMMENT ON TABLE productos IS 'Productos aprobados y disponibles para la venta';
COMMENT ON TABLE solicitudes_producto IS 'Solicitudes de publicación de productos pendientes de aprobación';
COMMENT ON TABLE pedidos IS 'Órdenes de compra realizadas por los usuarios';
COMMENT ON TABLE detalle_pedido IS 'Detalle de productos incluidos en cada pedido';
COMMENT ON TABLE calificaciones IS 'Calificaciones y comentarios de productos comprados';
COMMENT ON TABLE sanciones IS 'Historial de sanciones aplicadas a usuarios';
COMMENT ON TABLE notificaciones IS 'Historial de notificaciones enviadas por correo';

-- =====================================================
-- FIN DEL SCRIPT DDL
-- =====================================================