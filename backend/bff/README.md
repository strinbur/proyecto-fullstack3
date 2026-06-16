# Microservicio BFF (ms-bff)

## Descripción
El BFF actúa como punto de entrada unificado entre el frontend y los microservicios de backend (`ms-login`, `ms-inventory`, `ms-cart` y `ms-order`). Adapta las rutas para el cliente, reenvía solicitudes internas mediante OpenFeign y propaga el token `Authorization` a los servicios backend.

---

## Diagrama C3 - Componentes del BFF

```
┌──────────────────────────────────────────────────────────────────────┐
│                     BFF (Spring Boot - Port 8080)                    │
│                                                                      │
│  ┌────────────────────────────────────────────────────────────────┐ │
│  │                    REST Controllers                            │ │
│  │  ┌──────────────┐  ┌──────────────┐  ┌──────────────────────┐ │ │
│  │  │LoginCtrlr    │  │InventoryCtrl │  │CartCtrl / OrderCtrl  │ │ │
│  │  │- POST login  │  │- GET /invent │  │- POST /cart          │ │ │
│  │  │- POST regist │  │- POST invent │  │- GET /order          │ │ │
│  │  │- PUT user    │  │- PUT invent  │  │- PUT status          │ │ │
│  │  │- GET user    │  │- DELETE prod │  │- DELETE order        │ │ │
│  │  └──────┬───────┘  └──────┬───────┘  └──────────┬────────────┘ │ │
│  └─────────┼──────────────────┼──────────────────────┼───────────┘  │
│            │                  │                      │              │
│  ┌─────────▼──────────────────▼──────────────────────▼──────────┐  │
│  │                   Feign Client Proxies                       │  │
│  │  ┌──────────────┐  ┌──────────────┐  ┌──────────────────┐   │  │
│  │  │ LoginClient  │  │Inventory Cl. │  │ CartClient      │   │  │
│  │  │→ms-login:8081│  │→ms-invent:82 │  │→ms-cart:8083    │   │  │
│  │  └──────┬───────┘  └──────┬───────┘  │                │   │  │
│  │         │                  │          └──────────────┘   │  │
│  │  ┌──────▼──────────────────▼──────────────────────────┐  │  │
│  │  │        OrderClient                                │  │  │
│  │  │        → ms-order:8084                            │  │  │
│  │  └──────┬───────────────────────────────────────────┘  │  │
│  └─────────┼────────────────────────────────────────────┘  │
│            │                                               │
│  ┌─────────▼────────────────────────────────────────────┐  │
│  │        FeignInterceptor (JWT Propagation)            │  │
│  │  - Extracts Authorization header                     │  │
│  │  - Propaga a todos los clientes Feign               │  │
│  │  - RequestContextHolder para HttpServletRequest     │  │
│  └─────────┬────────────────────────────────────────────┘  │
│            │                                               │
│  ┌─────────▼────────────────────────────────────────────┐  │
│  │          Configuration & Exception Handling           │  │
│  │  - FeignConfig (timeouts, retries)                  │  │
│  │  - GlobalExceptionHandler                           │  │
│  │  - @EnableFeignClients                              │  │
│  └────────────────────────────────────────────────────────┘ │
└──────────────────────────────────────────────────────────────────┘
```

---

## Stack Tecnológico
- **Lenguaje**: Java 25
- **Framework**: Spring Boot 4.0.6
- **Comunicación**: Spring Cloud OpenFeign
- **Documentación**: OpenAPI/Swagger
- **Validación**: Spring Boot Validation
- **Herramientas**: Lombok, Spring Boot DevTools

## Dependencias
- `spring-boot-starter-webmvc` — servidor web y controladores REST
- `spring-cloud-starter-openfeign` — clientes Feign para llamadas a microservicios
- `spring-boot-starter-validation` — validación de datos y DTOs
- `springdoc-openapi-starter-webmvc-ui` — documentación OpenAPI/Swagger
- `spring-boot-devtools` (runtime/dev) — recarga automática en desarrollo
- `lombok` — reducción de código boilerplate
- `spring-boot-starter-test` (test) — utilidades de pruebas unitarias e integración

## Principales Patrones de Diseño
- **API Gateway / BFF Pattern**: Centraliza la comunicación entre frontend y microservicios.
- **Proxy / Gateway Pattern**: Reenvía peticiones a los servicios internos.
- **Facade Pattern**: Simplifica múltiples APIs en una interfaz única para el frontend.
- **DTO Pattern**: Uso de objetos de transferencia para la capa de entrada y de salida.
- **Dependency Injection**: Spring gestiona inyección de beans y clientes Feign.

## Endpoints Principales
- `POST /bff/login` — Iniciar sesión y devolver token + usuario
- `POST /bff/login/register` — Registrar nuevo usuario (envía la solicitud a `ms-login`)
- `POST /bff/login/admin` — Crear usuario con rol (envía la solicitud a `ms-login`)
- `PUT /bff/login/{id}` — Actualizar usuario (envía la solicitud a `ms-login`)
- `GET /bff/login/{id}` — Obtener usuario por ID (envía la solicitud a `ms-login`)
- `GET /bff/login` — Listar usuarios (envía la solicitud a `ms-login`)
- `DELETE /bff/login/{id}` — Eliminar usuario (envía la solicitud a `ms-login`)
- `GET /bff/inventory` — Listar productos (envía la solicitud a `ms-inventory`, opcional `?category={category}`)
- `POST /bff/inventory` — Crear producto (envía la solicitud a `ms-inventory`)
- `GET /bff/inventory/code/{code}` — Obtener producto por código (envía la solicitud a `ms-inventory`)
- `PUT /bff/inventory/code/{code}` — Actualizar producto (envía la solicitud a `ms-inventory`)
- `DELETE /bff/inventory/code/{code}` — Eliminar producto (envía la solicitud a `ms-inventory`)
- `POST /bff/cart` — Agregar producto al carrito (envía la solicitud a `ms-cart`)
- `GET /bff/cart/{userId}` — Obtener carrito del usuario (envía la solicitud a `ms-cart`)
- `PUT /bff/cart/{userId}` — Actualizar carrito (envía la solicitud a `ms-cart`)
- `DELETE /bff/cart/{userId}/item/{itemId}` — Eliminar item del carrito (envía la solicitud a `ms-cart`)
- `POST /bff/order` — Crear nueva orden (envía la solicitud a `ms-order`)
- `GET /bff/order/{orderId}` — Obtener detalles de orden (envía la solicitud a `ms-order`)
- `GET /bff/order/user/{userId}` — Listar órdenes del usuario (envía la solicitud a `ms-order`)
- `PUT /bff/order/{orderId}` — Actualizar estado de orden (envía la solicitud a `ms-order`)

## Cómo maneja los tokens JWT
- El token lo genera `ms-login` cuando el usuario inicia sesión.
- El frontend envía `Authorization: Bearer <token>` al BFF.
- El BFF reenvía ese header en todas las llamadas Feign hacia `ms-login` y `ms-inventory`.
- Así, los servicios backend pueden validar la autorización sin que el frontend los llame directamente.

## Feign y configuración de clientes
- `LoginClient` consume `ms-login` en `http://ms-login:8081`.
- `InventoryClient` consume `ms-inventory` en `http://ms-inventory:8082`.
- `CartClient` consume `ms-cart` en `http://ms-cart:8083`.
- `OrderClient` consume `ms-order` en `http://ms-order:8084`.
- El interceptor copia el header `Authorization` de la petición entrante.
- `RequestContextHolder` se usa para obtener la petición HTTP actual antes de reenviarla.

## Configuración
### Puerto
- El servicio expone el puerto **8080**.

### Configuración de Backends (Feign Clients)
- `ms-login`: `http://ms-login:8081`
- `ms-inventory`: `http://ms-inventory:8082`
- `ms-cart`: `http://ms-cart:8083`
- `ms-order`: `http://ms-order:8084`
- Tiempo de conexión Feign: 3000 ms
- Tiempo de lectura Feign: 5000 ms

### Variables de Entorno
- `SPRING_APPLICATION_NAME`: ms-bff
- `SERVER_PORT`: 8080
- `FEIGN_CONNECT_TIMEOUT`: 3000
- `FEIGN_READ_TIMEOUT`: 5000

## Levantamiento Individual
### Prerrequisitos
- Java 25 instalado
- Maven instalado
- `ms-login` y `ms-inventory` levantados o disponibles en sus puertos correspondientes

### Opción 1: Ejecutar con Maven
```bash
mvn spring-boot:run
```

### Opción 2: Compilar y ejecutar JAR
```bash
mvn clean install
java -jar target/ms-bff-0.0.1-SNAPSHOT.jar
```

## Documentación API
Accede a Swagger en:
`http://localhost:8080/swagger-ui.html`

## Testing
Ejecuta las pruebas unitarias con:
```bash
mvn test
```

## Notas
- El BFF es ideal para agrupar llamadas del frontend y manejar headers comunes como `Authorization`.
- Para un levantamiento completo de la solución, usa `docker-compose up` desde la raíz.
- Si el frontend no envía `Authorization`, los endpoints protegidos en los backends devolverán errores de autorización.

## Arquitectura del Proyecto
```
src/main/java/com/grupocordillera/ms_bff/
├── config/           # Configuración de Feign, interceptores y beans comunes
├── exception/        # Manejo global de errores y excepciones personalizadas
├── controller/       # Controladores REST para cada dominio
│   ├── LoginController.java
│   ├── InventoryController.java
│   ├── CartController.java
│   └── OrderController.java
├── client/           # Clientes Feign para comunicación con microservicios
│   ├── LoginClient.java
│   ├── InventoryClient.java
│   ├── CartClient.java
│   └── OrderClient.java
├── dto/              # DTOs para requests y responses
│   ├── login/
│   ├── inventory/
│   ├── cart/
│   └── order/
├── interceptor/      # Interceptores HTTP para propagar JWT
│   └── FeignInterceptor.java
└── MsBffApplication.java
```