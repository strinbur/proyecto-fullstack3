# Microservicio de Carrito (ms-cart)

## Descripción
Microservicio responsable de la gestión del carrito de compras. Permite a los usuarios agregar, actualizar y eliminar productos del carrito, mantener la persistencia de datos y sincronizar con el inventario. Implementa autenticación basada en JWT y validaciones de negocio.

---

## Diagrama C3 - Componentes de ms-cart

```
┌──────────────────────────────────────────────────────────────────────┐
│              ms-cart (Spring Boot - Port 8083)                      │
│                                                                      │
│  ┌────────────────────────────────────────────────────────────────┐ │
│  │            CartController                                    │ │
│  │  @RestController @RequestMapping(/cart)                    │ │
│  │  - POST / (Add to cart, requires AUTH)                     │ │
│  │  - GET /{userId} (Get cart, requires AUTH)                │ │
│  │  - PUT /{userId} (Update cart, requires AUTH)             │ │
│  │  - DELETE /{userId}/item/{itemId} (Remove item, AUTH)    │ │
│  │  - DELETE /{userId} (Empty cart, requires AUTH)           │ │
│  │  - GET /{userId}/total (Get total, requires AUTH)         │ │
│  └────────┬──────────────────────────────────────────────────┘ │
│           │                                                     │
│  ┌────────▼──────────────────────────────────────────────────┐ │
│  │            CartService (Interface)                       │ │
│  │  ┌──────────────────────────────────────────────────┐   │ │
│  │  │  CartServiceImpl                                 │   │ │
│  │  │  - addItemToCart(userId, item)                 │   │ │
│  │  │  - removeItemFromCart(userId, itemId)          │   │ │
│  │  │  - calculateTotal(cart)                        │   │ │
│  │  │  - applyDiscount(cart, discount)               │   │ │
│  │  │  - syncWithInventory(cart)                     │   │ │
│  │  │  - validateCartItems(items)                    │   │ │
│  │  │  - getCartByUserId(userId)                     │   │ │
│  │  │  - clearCart(userId)                           │   │ │
│  │  └──────────┬───────────────────────────────────┘   │ │\n│  └─────────────┼───────────────────────────────────────┘ │
│                │                                         │
│  ┌─────────────▼───────────────────────────────────────┐ │
│  │       CartRepository (MongoRepository)             │ │
│  │  extends MongoRepository<Cart, String>             │ │
│  │  - findByUserId(userId): Optional<Cart>            │ │
│  │  - deleteByUserId(userId): void                    │ │
│  │  - existsByUserId(userId): boolean                 │ │
│  └─────────────┬───────────────────────────────────────┘ │
│                │                                         │
│  ┌─────────────▼───────────────────────────────────────┐ │
│  │       Security & Validation Layer                  │ │
│  │  ┌───────────────────────────────────────────────┐ │ │
│  │  │ JwtAuthenticationFilter                       │ │ │
│  │  │ - Validates JWT from Authorization header    │ │ │
│  │  │ - Extracts userId from token                │ │ │
│  │  │ - Enforces user owns their cart             │ │ │
│  │  └───────────────────────────────────────────────┘ │ │\n│  │  ┌───────────────────────────────────────────────┐ │ │\n│  │  │ JwtProvider                                   │ │ │\n│  │  │ - validateToken(token)                       │ │ │\n│  │  │ - extractUserId(token)                       │ │ │\n│  │  └───────────────────────────────────────────────┘ │ │\n│  └────────────────────────────────────────────────────┘ │\n│                                                          │\n│  ┌────────────────────────────────────────────────────┐ │\n│  │  Validation & Exception Handling                   │ │\n│  │  - @Valid on CartItemDTO                          │ │\n│  │  - @NotNull, @Min validators                      │ │\n│  │  - GlobalExceptionHandler                        │ │\n│  │  - CartException, CartItemException              │ │\n│  └────────────────────────────────────────────────────┘ │\n│                                                          │\n│  ┌────────────────────────────────────────────────────┐ │\n│  │  Configuration                                     │ │\n│  │  - SecurityConfig (JWT, CORS)                    │ │\n│  │  - MongockConfig (DB migrations)                 │ │\n│  │  - CartFactory (Cart creation)                   │ │\n│  └────────────────────────────────────────────────────┘ │\n└──────────────────────────────────────────────────────────┘\n                         │\n          ┌──────────────▼──────────────┐\n          │    MongoDB (Port 27017)     │\n          │  Database: cart_bd          │\n          │  Collections:               │\n          │  - carts                    │\n          │  - cart_items               │\n          └─────────────────────────────┘\n```

---

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
   - Persistencia de carritos de usuario

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

## Dependencias Completas
- `spring-boot-starter-data-mongodb` — integración con MongoDB usando Spring Data
- `spring-boot-starter-webmvc` — base para APIs REST con Spring MVC
- `spring-boot-starter-security` — seguridad y filtros de autenticación
- `spring-boot-starter-validation` — validación de DTOs y datos de entrada
- `spring-boot-starter-test` (test) — utilidades de pruebas unitarias e integración
- `spring-boot-devtools` (runtime/dev) — recarga automática y herramientas de desarrollo
- `lombok` — generación de getters/setters y código boilerplate
- `mongock-springboot-v3` — migraciones de base de datos en MongoDB
- `mongodb-springdata-v4-driver` — driver MongoDB para Spring Data
- `springdoc-openapi-starter-webmvc-ui` — documentación OpenAPI/Swagger
- `jjwt-api` — interfaz para creación/validación de JWT
- `jjwt-impl` (runtime) — implementación de JWT
- `jjwt-jackson` (runtime) — serialización JSON para JWT

## Principales Patrones de Diseño
- **Repository Pattern**: Acceso a datos con `MongoRepository`.
- **Service Layer Pattern**: Lógica de negocio separada de los controladores.
- **DTO Pattern**: Transferencia de datos con objetos específicos.
- **Factory Pattern**: Mapeo y creación de objetos respetando reglas de negocio.
- **Filter Pattern**: `JwtAuthenticationFilter` valida tokens en cada petición.
- **Dependency Injection**: Spring gestiona dependencias para bajo acoplamiento.
- **Strategy Pattern**: Diferentes estrategias para cálculo de totales y descuentos.

## Endpoints Principales
- `POST /cart` — Agregar producto al carrito (requiere autenticación)
- `GET /cart/{userId}` — Obtener carrito del usuario (requiere autenticación)
- `PUT /cart/{userId}` — Actualizar carrito completo (requiere autenticación)
- `DELETE /cart/{userId}/item/{itemId}` — Eliminar item del carrito (requiere autenticación)
- `DELETE /cart/{userId}` — Vaciar carrito del usuario (requiere autenticación)
- `GET /cart/{userId}/total` — Obtener total del carrito (requiere autenticación)

## Reglas de Negocio y Validaciones
### Validaciones de Carrito
- El userId es obligatorio y debe existir.
- Cada item debe tener: productId, cantidad y precio unitario.
- La cantidad debe ser mayor a 0.
- El productId debe ser válido.
- Un usuario solo puede tener un carrito activo.

### Reglas Automáticas
- El carrito se crea automáticamente en la primera compra.
- Se calcula automáticamente el subtotal, impuestos y total.
- Se aplican descuentos según cantidad de productos.
- El carrito se sincroniza con el inventario.
- Timestamp de actualización automático.

### Autenticación y Autorización
- **Endpoints protegidos**: Todos requieren token JWT válido
- **Control de acceso**: Un usuario solo puede acceder a su propio carrito
- **Token JWT**: Validez de 24 horas (`86400000 ms`)
- **Propagación**: El BFF propaga el token en todas las peticiones

### Manejo de Errores
- Respuestas adecuadas: 400 para validaciones, 401 para autenticación, 404 para no encontrado
- Se usan excepciones personalizadas para errores de negocio
- Mensajes de error claros y descriptivos

## Configuración
### Puerto
- El servicio expone el puerto **8083**.

### Base de Datos
- URI por defecto: `mongodb://host.docker.internal:27017/cart_bd`
- Requiere MongoDB corriendo en el puerto 27017.

### Variables de Entorno
- `SPRING_APPLICATION_NAME`: ms-cart
- `SERVER_PORT`: 8083
- `SPRING_DATA_MONGODB_URI`: `mongodb://host.docker.internal:27017/cart_bd`

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
java -jar target/ms-cart-0.0.1-SNAPSHOT.jar
```

## Documentación API
Accede a Swagger en:
`http://localhost:8083/swagger-ui.html`

## Testing
Ejecuta las pruebas unitarias con:
```bash
mvn test
```

## Arquitectura del Proyecto

### Estructura de Directorios
```
src/main/java/com/grupocordillera/ms_cart/
├── config/             # Configuraciones de Spring
│   ├── SecurityConfig.java
│   ├── MongockConfig.java
│   └── WebConfig.java
├── controller/         # Controladores REST
│   └── CartController.java
├── dto/                # Data Transfer Objects
│   ├── CartItemDTO.java
│   ├── CartRequestDTO.java
│   └── CartResponseDTO.java
├── exception/          # Excepciones personalizadas
│   ├── CartException.java
│   └── GlobalExceptionHandler.java
├── factory/            # Factory Pattern
│   └── CartFactory.java
├── migrations/         # Scripts de migración (Mongock)
│   ├── ChangeLogs.java
│   └── InitialData.java
├── model/              # Entidades de dominio
│   ├── Cart.java
│   └── CartItem.java
├── repository/         # Interfaces MongoRepository
│   └── CartRepository.java
├── security/           # JWT y Autenticación
│   ├── JwtProvider.java
│   ├── JwtAuthenticationFilter.java
│   └── SecurityUtil.java
├── service/            # Lógica de negocio
│   ├── CartService.java
│   └── impl/
│       └── CartServiceImpl.java
└── MsCartApplication.java
```

### Patrones de Arquitectura
- **Layered Architecture**: Separación clara entre capas (Controller → Service → Repository)
- **Hexagonal Architecture**: Independencia de frameworks externos
- **Domain-Driven Design**: Entidades de dominio bien definidas
- **SOLID Principles**: Responsabilidad única, abierto/cerrado, inversión de dependencias

## Notas
- Forma parte de la arquitectura completa del proyecto.
- Para un levantamiento completo, usa `docker-compose up` desde la raíz.
- El carrito es un dominio crítico que sincroniza con ms-inventory.
- Mongock ejecuta las migraciones de base de datos al iniciar.