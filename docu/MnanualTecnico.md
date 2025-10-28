# Manual Técnico - Sistema de E-commerce

## Descripción General

Sistema de comercio electrónico desarrollado con **Spring Boot** (backend) y **Vue.js** (frontend), que permite la gestión de productos, usuarios, pedidos y procesos administrativos.

## Arquitectura del Sistema

### Backend (Spring Boot)
- **Framework**: Spring Boot 
- **Base de datos**: JPA/Hibernate
- **Seguridad**: JWT Authentication
- **Arquitectura**: MVC con capas de servicio y repositorio

### Frontend (Vue.js)
- **Framework**: Vue.js 3
- **Router**: Vue Router
- **Estado**: Vuex Store
- **HTTP Client**: Axios
- **Build Tool**: Webpack

## Estructura del Proyecto

### Backend (`/backend_springboot`)

```
src/main/java/com/archivos/ecommerce/
├── EcommerceApplication.java          # Clase principal de Spring Boot
├── config/                           # Configuraciones
│   ├── JwtAuthenticationFilter.java  # Filtro JWT
│   ├── SecurityConfig.java          # Configuración de seguridad
│   └── WebConfig.java               # Configuración web
├── controller/                       # Controladores REST
│   ├── AdminController.java
│   ├── AuthController.java
│   ├── CalificacionController.java
│   ├── CarritoController.java
│   ├── CategoriaController.java
│   ├── LogisticaController.java
│   ├── ModeradorController.java
│   ├── NotificacionController.java
│   ├── PedidoController.java
│   ├── ProductoController.java
│   ├── SancionController.java
│   ├── TestController.java
│   └── UsuarioController.java
├── dto/                             # Data Transfer Objects
│   ├── CalificacionDTO.java
│   ├── CarritoDTO.java
│   ├── DetallePedidoDTO.java
│   ├── ItemCarritoDTO.java
│   ├── NotificacionDTO.java
│   ├── PedidoDTO.java
│   ├── ProductoDTO.java
│   ├── SancionDTO.java
│   ├── SolicitudProductoDTO.java
│   └── UsuarioDTO.java
├── entity/                          # Entidades JPA
│   ├── Calificacion.java
│   ├── Carrito.java
│   ├── Categoria.java
│   ├── DetallePedido.java
│   ├── EstadoPedido.java
│   ├── EstadoProducto.java
│   ├── EstadoSancion.java
│   ├── EstadoSolicitud.java
│   ├── ItemCarrito.java
│   ├── Notificacion.java
│   ├── Pedido.java
│   ├── Producto.java
│   └── Rol.java
├── repository/                      # Repositorios JPA
└── service/                        # Servicios de negocio
```

### Frontend (`/frontend_vue`)

```
src/
├── App.vue                         # Componente raíz
├── main.js                        # Punto de entrada
├── components/                    # Componentes reutilizables
│   ├── HelloWorld.vue
│   ├── NavBar.vue
│   └── ProductCard.vue
├── router/
│   └── index.js                   # Configuración de rutas
├── services/                      # Servicios HTTP
│   ├── api.js
│   ├── authService.js
│   ├── cartService.js
│   ├── categoryService.js
│   └── productService.js
├── store/
│   └── index.js                   # Store de Vuex
└── views/                         # Vistas/Páginas
    ├── Cart.vue
    ├── Checkout.vue
    ├── CreateProduct.vue
    ├── Home.vue
    ├── Login.vue
    ├── ProductDetail.vue
    ├── Products.vue
    ├── Register.vue
    ├── admin/
    │   ├── AdminDashboard.vue
    │   ├── AdminReportes.vue
    │   └── AdminUsuarios.vue
    ├── logistica/
    │   ├── Entregas.vue
    │   └── Pedidos.vue
    └── moderador/
        ├── Sanciones.vue
        └── Solicitudes.vue
```

## Funcionalidades Principales

### Autenticación y Autorización
- **JWT Authentication**: Sistema de tokens para autenticación
- **Roles de usuario**: Admin, Moderador, Logística, Cliente
- **Filtros de seguridad**: Control de acceso por rutas

### Gestión de Productos
- **CRUD de productos**: Crear, leer, actualizar, eliminar
- **Categorización**: Organización por categorías
- **Estados**: Control de estados de productos
- **Solicitudes**: Sistema de aprobación de productos

### Sistema de Pedidos
- **Carrito de compras**: Gestión de items en carrito
- **Procesamiento de pedidos**: Flujo completo de pedidos
- **Estados de pedido**: Seguimiento de estados
- **Detalles de pedido**: Información detallada

### Administración
- **Dashboard administrativo**: Panel de control
- **Gestión de usuarios**: CRUD de usuarios
- **Reportes**: Generación de reportes
- **Sanciones**: Sistema de sanciones

### Logística
- **Gestión de entregas**: Control de entregas
- **Estados de envío**: Seguimiento de envíos

## Configuración de Desarrollo

### Backend
1. **Requisitos**:
   - Java 17+
   - Maven 3.6+
   - Base de datos (MySQL/PostgreSQL)

2. **Instalación**:
   ```bash
   cd backend_springboot
   mvn clean install
   mvn spring-boot:run
   ```

3. **Configuración** (`application.properties`):
   ```properties
   server.port=8080
   spring.datasource.url=jdbc:mysql://localhost:3306/ecommerce
   spring.datasource.username=root
   spring.datasource.password=password
   spring.jpa.hibernate.ddl-auto=update
   jwt.secret=mySecretKey
   jwt.expiration=86400000
   ```

### Frontend
1. **Requisitos**:
   - Node.js 16+
   - npm/yarn

2. **Instalación**:
   ```bash
   cd frontend_vue
   npm install
   npm run serve
   ```

3. **Configuración** (`vue.config.js`):
   ```javascript
   module.exports = {
     devServer: {
       proxy: {
         '/api': {
           target: 'http://localhost:8080',
           changeOrigin: true
         }
       }
     }
   }
   ```

## API Endpoints

### Autenticación
- `POST /api/auth/login` - Iniciar sesión
- `POST /api/auth/register` - Registrar usuario
- `POST /api/auth/logout` - Cerrar sesión

### Productos
- `GET /api/productos` - Listar productos
- `POST /api/productos` - Crear producto
- `PUT /api/productos/{id}` - Actualizar producto
- `DELETE /api/productos/{id}` - Eliminar producto
- `GET /api/productos/{id}` - Obtener producto por ID

### Carrito
- `GET /api/carrito` - Obtener carrito del usuario
- `POST /api/carrito/agregar` - Agregar item al carrito
- `PUT /api/carrito/actualizar` - Actualizar cantidad
- `DELETE /api/carrito/eliminar/{id}` - Eliminar item

### Pedidos
- `GET /api/pedidos` - Listar pedidos
- `POST /api/pedidos` - Crear pedido
- `PUT /api/pedidos/{id}/estado` - Actualizar estado
- `GET /api/pedidos/{id}` - Obtener pedido por ID

### Administración
- `GET /api/admin/usuarios` - Listar usuarios
- `PUT /api/admin/usuarios/{id}` - Actualizar usuario
- `GET /api/admin/reportes` - Obtener reportes
- `POST /api/admin/sanciones` - Crear sanción

## Base de Datos

### Entidades Principales
- **Usuario**: Información de usuarios del sistema
- **Producto**: Catálogo de productos
- **Categoria**: Categorías de productos
- **Carrito**: Carrito de compras por usuario
- **ItemCarrito**: Items dentro del carrito
- **Pedido**: Órdenes de compra
- **DetallePedido**: Detalles de cada pedido
- **Calificacion**: Calificaciones de productos
- **Notificacion**: Sistema de notificaciones
- **Sancion**: Registro de sanciones

### Relaciones
- Usuario 1:1 Carrito
- Carrito 1:N ItemCarrito
- Usuario 1:N Pedido
- Pedido 1:N DetallePedido
- Producto 1:N Calificacion
- Usuario 1:N Calificacion

## Seguridad

### JWT Implementation
- **Generación**: Tokens firmados con clave secreta
- **Validación**: Filtro de autenticación en cada request
- **Expiración**: Tokens con tiempo de vida limitado
- **Roles**: Control de acceso basado en roles

### CORS Configuration
- **Permitir orígenes**: Frontend en desarrollo
- **Métodos permitidos**: GET, POST, PUT, DELETE
- **Headers**: Authorization, Content-Type

## Testing

### Backend Testing
```bash
mvn test
```

### Frontend Testing
```bash
npm run test:unit
```

## Deployment

### Backend Production
```bash
mvn clean package
java -jar target/ecommerce-0.0.1-SNAPSHOT.jar
```

### Frontend Production
```bash
npm run build
# Servir archivos de la carpeta dist/
```

## Monitoreo y Logs

### Spring Boot Actuator
- **Health Check**: `/actuator/health`
- **Metrics**: `/actuator/metrics`
- **Info**: `/actuator/info`

### Logging Configuration
```properties
logging.level.com.archivos.ecommerce=DEBUG
logging.pattern.console=%d{yyyy-MM-dd HH:mm:ss} - %msg%n
logging.file.name=logs/ecommerce.log
```

## Troubleshooting

### Problemas Comunes
1. **Error de conexión a BD**: Verificar configuración en application.properties
2. **CORS errors**: Revisar configuración de WebConfig
3. **JWT inválido**: Verificar expiración y clave secreta
4. **404 en rutas**: Verificar configuración de router en Vue

### Comandos Útiles
```bash
# Limpiar y compilar backend
mvn clean compile

# Verificar dependencias frontend
npm audit

# Ver logs en tiempo real
tail -f logs/ecommerce.log
```