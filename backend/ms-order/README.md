# Microservicio de Órdenes (ms-order)

## Descripción
Microservicio responsable de la gestión de órdenes de compra. Permite crear, consultar, actualizar y gestionar el estado de órdenes, procesando transacciones desde el carrito y manteniendo un historial completo de compras. Implementa autenticación basada en JWT y validaciones de negocio.

---

## Diagrama C3 - Componentes de ms-order


## Stack Tecnológico
- **Lenguaje**: Java 25
- **Framework**: Spring Boot 4.0.6
- **Base de Datos**: MongoDB
- **Autenticación**: JWT (JSON Web Tokens)
- **Documentación**: OpenAPI/Swagger
- **Herramientas**: Lombok, Mongock, Spring Security

---

## Dependencias Principales (Top 5)

1. **MongoDB** (`spring-boot-starter-data-mongodb`)
   - Integración con MongoDB usando Spring Data
   - Persistencia de órdenes y auditoría

2. **Mongock** (`mongock-springboot-v3`)
   - Migraciones de base de datos en MongoDB
   - Versionado y control de cambios en la BD

3. **Lombok** (`lombok`)
   - Generación automática de getters/setters
   - Reduce código boilerplate significativamente

4. **JWT** (`jjwt-api`, `jjwt-impl`, `jjwt-jackson`)
   - Creación y validación de JSON Web Tokens
   - Autenticación y autorización segura

5. **Spring Validation** (`spring-boot-starter-validation`)
   - Validación de DTOs y datos de entrada
   - Anotaciones declarativas para validación

---

- `jjwt-jackson` (runtime) — serialización JSON para JWT

## Principales Patrones de Diseño
- **Repository Pattern**: Acceso a datos con `MongoRepository`.
- **Service Layer Pattern**: Lógica de negocio separada de los controladores.
- **DTO Pattern**: Transferencia de datos con objetos específicos.
- **Factory Pattern**: Creación de órdenes con validaciones de negocio.
- **Filter Pattern**: `JwtAuthenticationFilter` valida tokens en cada petición.
- **Dependency Injection**: Spring gestiona dependencias para bajo acoplamiento.
- **Observer Pattern**: Notificación de cambios de estado de órdenes.
- **State Pattern**: Gestión de diferentes estados de órdenes.

## Endpoints Principales
- `POST /order` — Crear nueva orden (requiere autenticación)
- `GET /order/{orderId}` — Obtener detalles de orden (requiere autenticación)
- `GET /order/user/{userId}` — Listar órdenes del usuario (requiere autenticación)
- `PUT /order/{orderId}` — Actualizar estado de orden (requiere rol `ADMIN` o propietario)
- `DELETE /order/{orderId}` — Cancelar orden (requiere autenticación y validaciones)
- `GET /order/{orderId}/status` — Obtener estado actual de orden (requiere autenticación)

## Reglas de Negocio y Validaciones
### Validaciones de Órdenes
- El userId es obligatorio y debe existir.
- Cada orden debe tener al menos un item.
- Los items deben tener: productId, cantidad, precio unitario.
- El monto total debe ser mayor a 0.
- La dirección de envío es obligatoria.
- La orden debe existir para actualizar o cancelar.

### Reglas Automáticas
- Se generan automáticamente OrderId único.
- Se calcula automáticamente el subtotal, impuestos y total.
- Se aplican descuentos según cantidad y cupones.
- Se registra timestamp de creación y última actualización.
- Se mantiene historial de cambios de estado.
- Se desconecta inventario al confirmar orden.

### Estados de Orden
- **PENDING**: Orden creada, esperando confirmación de pago
- **CONFIRMED**: Pago confirmado, procesando envío
- **SHIPPED**: Orden enviada
- **DELIVERED**: Orden entregada
- **CANCELLED**: Orden cancelada

### Autenticación y Autorización
- **Endpoints protegidos**: Todos requieren token JWT válido
- **Control de acceso**: Un usuario solo puede ver sus propias órdenes
- **Administración**: Solo rol `ADMIN` puede cambiar estados manualmente
- **Token JWT**: Validez de 24 horas (`86400000 ms`)

### Manejo de Errores
- Respuestas adecuadas: 400 para validaciones, 401 para autenticación, 404 para no encontrado
- Se usan excepciones personalizadas para errores de negocio
- Mensajes de error claros y descriptivos

## Configuración
### Puerto
- El servicio expone el puerto **8084**.

### Base de Datos
- URI por defecto: `mongodb://host.docker.internal:27017/order_bd`
- Requiere MongoDB corriendo en el puerto 27017.

### Variables de Entorno
- `SPRING_APPLICATION_NAME`: ms-order
- `SERVER_PORT`: 8084
- `SPRING_DATA_MONGODB_URI`: `mongodb://host.docker.internal:27017/order_bd`

## Levantamiento Individual
### Prerrequisitos
- Java 25 instalado
- Maven instalado
- MongoDB corriendo en el puerto 27017

### Opción 1: Ejecutar con Maven
```bash
mvn spring-boot:run
```

### Opción 2: Compilar y ejecutar JAR
```bash
mvn clean install
java -jar target/ms-order-0.0.1-SNAPSHOT.jar
```

## Documentación API
Accede a Swagger en:
`http://localhost:8084/swagger-ui.html`

## Testing
Ejecuta las pruebas unitarias con:
```bash
mvn test
```

## Arquitectura del Proyecto

### Estructura de Directorios
```
src/main/java/com/grupocordillera/ms_order/
├── config/             # Configuraciones de Spring
│   ├── SecurityConfig.java
│   ├── MongockConfig.java
│   └── WebConfig.java
├── controller/         # Controladores REST
│   └── OrderController.java
├── dto/                # Data Transfer Objects
│   ├── OrderItemDTO.java
│   ├── OrderRequestDTO.java
│   ├── OrderResponseDTO.java
│   └── OrderStatusDTO.java
├── exception/          # Excepciones personalizadas
│   ├── OrderException.java
│   ├── OrderNotFoundException.java
│   └── GlobalExceptionHandler.java
├── factory/            # Factory Pattern
│   └── OrderFactory.java
├── migrations/         # Scripts de migración (Mongock)
│   ├── ChangeLogs.java
│   └── InitialData.java
├── model/              # Entidades de dominio
│   ├── Order.java
│   ├── OrderItem.java
│   ├── OrderStatus.java
│   └── ShippingInfo.java
├── repository/         # Interfaces MongoRepository
│   └── OrderRepository.java
├── security/           # JWT y Autenticación
│   ├── JwtProvider.java
│   ├── JwtAuthenticationFilter.java
│   └── SecurityUtil.java
├── service/            # Lógica de negocio
│   ├── OrderService.java
│   ├── OrderStatusService.java
│   └── impl/
│       ├── OrderServiceImpl.java
│       └── OrderStatusServiceImpl.java
└── MsOrderApplication.java
```

### Patrones de Arquitectura
- **Layered Architecture**: Separación clara entre capas (Controller → Service → Repository)
- **Hexagonal Architecture**: Independencia de frameworks externos
- **Domain-Driven Design**: Entidades de dominio bien definidas (Order, OrderItem)
- **SOLID Principles**: Responsabilidad única, abierto/cerrado, inversión de dependencias

## Notas
- Forma parte de la arquitectura completa del proyecto.
- Para un levantamiento completo, usa `docker-compose up` desde la raíz.
- Las órdenes son críticas: requieren integridad de datos y auditoria.
- Mongock ejecuta las migraciones de base de datos al iniciar.
- Se recomienda implementar una cola de eventos para cambios de estado.