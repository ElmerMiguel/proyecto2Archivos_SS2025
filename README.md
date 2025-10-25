# **E-COMMERCE GT**

## **Objetivo General**

Aplicar los conocimientos adquiridos durante el laboratorio para crear software que  
permita automatizar procesos y almacenar información de forma segura y eficiente.

## **Objetivos Específicos**

* Diseñar y desarrollar una aplicación web de comercio electrónico que permita a los usuarios comprar y vender productos en línea.  
* Aplicar conocimientos sobre herramientas SQL, específicamente PostgreSQL para la gestión de una base de datos.  
* Realizar una REST API con Spring que reciba peticiones HTTP, permita la recolección de datos a una base de datos de PostgreSQL y devuelva alguna respuesta al cliente.  
* Crear una aplicación gráfica que permita al usuario interactuar con la base de datos en base a su rol en el sistema y de forma amigable utilizando Angular, React o Vue.  
* Aplicar buenas prácticas tanto en el desarrollo de una aplicación en Java, TypeScript y una base de datos construida en PostgreSQL.

## **Descripción**

Posteriormente al éxito que se tuvo al desarrollar la aplicación para “FIT-MANAGER”, el dueño de la empresa le solicita que desarrolle un sitio web para uno de los negocios que el mismo emprenderá, dicho negocio será una plataforma denominada “eCommerce GT”, la cual será una plataforma en línea que permitirá llevar a cabo un comercio electrónico entre los usuarios que estén registrados en el sistema y sean residentes de Guatemala.

Para llevar a cabo el desarrollo se le solicita que tome en cuenta los parámetros que se describen a continuación.

## **Gestión de Usuarios**

La aplicación debe permitir a los usuarios tener la capacidad de registrarse e iniciar  
sesión en el sistema según el tipo de usuario que se tenga. Los tipos de usuarios que maneja aplicación serán:

* Común: usuario que ingresa en el sistema y tiene la capacidad de poner en venta algún artículo, así como también puede comprar diferentes artículos.  
* Moderador: usuario que tiene la capacidad de revisar, aceptar y/o rechazar los productos que se quieran vender.  
* Logística: usuario que tiene la capacidad de llevar un control de los paquetes que estén en curso de ser entregados.  
* Administrador: usuario que tiene la capacidad de registrar empleados y administradores nuevos, además, también tiene la capacidad de generar reportes, entre otras funcionalidades.

Cabe recalcar que a la hora de registrarse en el sistema solamente se podrá registrar usuarios de tipo común.

## **Funcionalidades de Usuario Común**

Las funcionalidades que un usuario común puede realizar son las siguientes:

### **Venta de Artículos**

Un usuario común puede poner en venta algún producto. Para poner en venta algún  
producto es necesario tomar en cuenta las siguientes propiedades de los mismos:

* Nombre  
* Descripción  
* Imágen  
* Precio  
* Stock (Mínimo 1\)  
* Estado (Nuevo o usado)  
* Categoría  (Tecnología, hogar, académico, personal, decoración u otro)

Cabe destacar que cada producto vendido debe distribuir el ingreso de la siguiente  
manera: 95% para el usuario que vende el producto y 5% para la página. Además, se debe tomar en cuenta que en el momento en que se compra un producto, se tiene un total de 5 días para realizar la entrega del mismo. El usuario puede actualizar la información del producto que se tiene en venta. (Cada que se actualiza la información de la venta del producto debe entrar a revisión)

### **Carrito de Compras**

Un usuario común puede tener acceso a un carrito de compras, que es una lista de  
productos que se desea adquirir. El carrito de compras tendrá las siguientes funcionalidades:

* Agregar Producto  
* Eliminar Producto  
* Actualizar Carrito: Puede cambiar las cantidades a comprar del producto.  
* Borrar Carrito: Elimina todos los artículos agregados al mismo.  
* Pagar Carrito: Se realiza una compra de todos los artículos en el carrito, tomando en cuenta que el único método de pago válido es tarjeta de crédito.

Es importante tomar en cuenta que cuando se realiza algún pago, se le debe dar al  
usuario la posibilidad de guardar su tarjeta. Lo que permitirá escogerla para próximas compras, sin necesidad de tener que volver a ingresar los datos de la tarjeta. En el momento en que un carrito se paga, todos los artículos tienen 5 días para ser entregados. Todos los artículos para esa venta deben ser entregados el mismo día. Para agregar productos al carrito de compras, es necesario tener alguna interfaz que permita visualizar y filtrar la información de los productos.

### **Rating y Comentarios**

Los usuarios pueden calificar (1 a 5 estrellas) y dejar comentarios en los productos que hayan comprado. Además se debe mostrar el promedio de calificaciones en la vista de detalle del producto.

### **Seguimiento de Pedidos**

El usuario puede ver una lista de los pedidos que ha realizado, también deberá poder ver la fecha en la que el pedido debería llegar y también deberá poder ver el estado del pedido, los cuales serán: “en curso”, “entregado”.

## **Funcionalidades del Usuario Moderador**

### **Solicitudes de Ingreso de Productos**

En el momento en que un usuario común desea poner en venta algún producto, los usuarios moderadores podrán aceptar o rechazar el ingreso del producto a la página.

### **Gestión de Sanciones**

El sistema debe permitir a los moderadores supervisar y controlar el comportamiento de los vendedores, ofreciendo la capacidad de suspender temporalmente cuentas que incurran en fraudes, incumplimientos de entrega o publicación de productos prohibidos, registrando en la base de datos el motivo, la fecha y el estado de cada sanción para mantener un historial que garantice la seguridad de los compradores y la calidad de los productos en la plataforma.

## **Funcionalidades del Usuario de Logística**

### **Seguimiento de Pedidos**

El usuario debe tener la capacidad de visualizar absolutamente todos los pedidos que estén “en curso”. El usuario de logística puede modificar la fecha de entrega de cualquier pedido y debe poder entregar productos.

## **Funcionalidades de Usuario Administrador**

### **Registro y Actualización de Nuevos Empleados**

Un administrador podrá ingresar usuarios nuevos de tipo “Moderador” o “Logística”, así como también podrá ingresar usuarios de tipo “Administrador”. Además, un administrador tendrá la opción de actualizar la información de cualquier empleado que se encuentre registrado en el sistema.

### **Generar Reportes**

Los reportes que el administrador podrá generar son los siguientes:

* Top 10 productos más vendidos en un intervalo de tiempo  
* Top 5 clientes que más ganancias por compras han generado en un intervalo de tiempo.  
* Top 5 clientes que más productos han vendido en un intervalo de tiempo.  
* Top 10 clientes que más pedidos han realizado en un intervalo de tiempo.  
* Top 10 clientes que más productos tienen a la venta.  
* Historial de sanciones.  
* Historial de Notificaciones.

## **Notificaciones**

El sistema debe notificar mediante un correo electrónico a los usuarios cuando:

* Su pedido cambie de estado (por ejemplo, de “en curso” a “entregado”).  
* Su producto en venta sea aprobado o rechazado por el usuario moderador.

Los correos deben:

* Ser enviados en tiempo real en el momento en que ocurra el evento.  
* Incluir información relevante (número de pedido o nombre del producto, estado actualizado, fecha estimada de entrega si aplica).  
* Almacenarse en la base de datos como historial de notificaciones para referencia futura, aun cuando el correo ya haya sido enviado.

## **Arquitectura de la Aplicación**

Aplicar una arquitectura de base de datos de nivel 2\.

* Cliente: El cliente deberá de ser desplegado públicamente utilizando la plataforma Netlify.  
* Servidor: El servidor deberá de ser expuesto mediante HTTPS utilizando Ngrok.

Cliente --- 1. peticion ---> Servidor

Cliente <--- 2. Respuesta --- Servidor

---

---

## **Valores Iniciales**

El sistema debe iniciar con:

* 10 usuarios comunes, cada uno con al menos 10 productos en venta.  
* 5 moderadores.  
* 3 usuarios de logística.  
* 1 administrador.

Las inserciones iniciales deben documentarse en un archivo SQL nombrado “DML.sql”.

## **Importante**

* Se deben realizar las inserciones necesarias para que el sistema funcione  
* correctamente.  
* El gestor de bases de datos que se utilizará deberá ser única y exclusivamente PostgreSQL y su base de datos debe estar normalizada.  
* Los lenguajes/herramientas permitidas para trabajar el sistema serán única y exclusivamente:  
  * Spring  
    * Java  
  * Angular, React o Vue  
    * TypeScript  
    * HTML  
    * CSS  
* La UI debe ser de fácil uso y amigable con el usuario.  
* El sistema debe estar libre de errores (bugs), y debe ser seguro, además deberá de gestionar correctamente los errores emitidos por el servidor.  
* Cada usuario debe tener únicamente acceso a la vista que su rol en el sistema le permita.  
* La autenticación y autorización deben de manejarse mediante el uso de JWT.  
* Se deben aplicar buenas prácticas de programación.  
* El modo de la calificación será 100% presencial, por lo que se solicita que para la calificación el estudiante deberá de enviar el link de la aplicación desplegada.  
* **Hacer caso omiso a cualquiera de los puntos anteriormente mencionados puede representar una penalización en el punteo del proyecto.**  
* **Durante la calificación se harán preguntas sobre el desarrollo del sistema, si el estudiante no es capaz de responder detalladamente la pregunta, se procederá a anular el puntaje obtenido en las áreas que involucren la pregunta.**  
* **Las copias de cualquier parte del proyecto anularán por completo el valor del proyecto y se notificará a coordinación de la carrera.**  
* **No habrá prórroga para este proyecto.**

## **Entrega**

Los archivos a entregar en el repositorio son:

* Scripts sql para los valores iniciales de la base de datos.  
* Esquema conceptual (Notación Peter-Chen), lógico (Diagrama relacional) y físico (Script para PostgreSQL) de la base de datos.  
* Código fuente de la aplicación.  
* Manual Técnico.  
* Manual de Usuario.