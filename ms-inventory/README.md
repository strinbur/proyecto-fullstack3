# Microservicio de Inventario (ms-inventory)

## Descripción
Microservicio responsable de la gestión del inventario de productos. Ofrece endpoints para crear, leer, actualizar y eliminar productos, con validaciones y reglas de negocio orientadas a inventario, y seguridad basada en JWT.

## Stack Tecnológico
- **Lenguaje**: Java 25
- **Framework**: Spring Boot 4.0.6
- **Base de Datos**: MongoDB
- **Autenticación**: JWT (JSON Web Tokens)
- **Documentación**: OpenAPI/Swagger
- **Herramientas**: Lombok, Mongock, Spring Security

## Dependencias
- `spring-boot-starter-data-mongodb` — integración con MongoDB usando Spring Data
- `spring-boot-starter-webmvc` — base para APIs REST con Spring MVC
- `spring-boot-starter-security` — seguridad y filtros de autenticación
- `spring-boot-starter-validation` — validación de DTOs y datos de entrada
- `spring-boot-starter-data-mongodb-test` (test) — pruebas de integración con MongoDB
- `spring-boot-starter-webmvc-test` (test) — utilidades de prueba para MVC
- `spring-boot-devtools` (runtime/dev) — recarga automática y herramientas de desarrollo
- `lombok` — generación de getters/setters y código boilerplate
- `mongock-springboot-v3` — migraciones de base de datos en MongoDB
- `mongodb-springdata-v4-driver` — driver MongoDB para Spring Data
- `springdoc-openapi-starter-webmvc-ui` — documentación OpenAPI/Swagger
- `jjwt-api` — interfaz para creación/validación de JWT
- `jjwt-impl` (runtime) — implementación de JWT
- `jjwt-jackson` (runtime) — soporte JSON para JWT

## Principales Patrones de Diseño
- **Repository Pattern**: Acceso a datos con `MongoRepository`.
- **Service Layer Pattern**: Lógica de negocio separada de los controladores.
- **DTO Pattern**: Transferencia de datos con objetos específicos.
- **Factory Pattern**: `InventoryFactory` mapea y crea objetos respetando reglas de negocio.
- **Filter Pattern**: `JwtAuthenticationFilter` valida tokens en cada petición.
- **Dependency Injection**: Spring gestiona dependencias para bajo acoplamiento.

## Endpoints Principales
- `GET /inventario` — Listar productos (público), opcional `?categoria={categoria}`
- `GET /inventario/{codigo}` — Obtener producto por código (restringido según configuración de seguridad)
- `POST /inventario` — Crear producto (requiere rol `ADMIN`)
- `PUT /inventario/{codigo}` — Actualizar producto (requiere rol `ADMIN`)
- `DELETE /inventario/{codigo}` — Eliminar producto (requiere rol `ADMIN`)

## Reglas de Negocio y Validaciones
### Validaciones de Productos
- Campos obligatorios: código, nombre, marca, categoría.
- Código único para cada producto.
- Precio mayor o igual a 0.
- Cantidad mayor o igual a 0.
- El producto debe existir para actualizar o eliminar.

### Reglas Automáticas
- Categoría `Tecnología` agrega atributo `garantia: 12 meses`.
- Cantidad cero agrega atributo `estado: sin stock`.
- Soporta atributos dinámicos adicionales en el producto.

### Seguridad
- `GET /inventario` es público.
- Crear/actualizar/eliminar productos requiere rol `ADMIN`.
- Tokens JWT expiran en 24 horas (`86400000 ms`).

### Manejo de Errores
- Respuestas adecuadas: 400 para validaciones, 404 para no encontrado, etc.
- Se usan excepciones con mensajes claros para errores de negocio.

## Configuración
### Puerto
- El servicio expone el puerto **8082**.

### Base de Datos
- URI por defecto: `mongodb://host.docker.internal:27017/inventory_bd`
- Requiere MongoDB corriendo en el puerto 27017.

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
java -jar target/ms-inventory-0.0.1-SNAPSHOT.jar
```

## Documentación API
Accede a Swagger en:
`http://localhost:8082/swagger-ui.html`

## Testing
Ejecuta las pruebas unitarias con:
```bash
mvn test
```

## Notas
- Forma parte de la arquitectura completa del proyecto.
- Para un levantamiento completo, usa `docker-compose up` desde la raíz.
- Mongock ejecuta las migraciones de base de datos al iniciar.

## Arquitectura del Proyecto
```
src/main/java/com/grupocordillera/ms_inventory/
├── config/          # Configuraciones de Spring (Seguridad, Mongock)
├── controller/      # Controladores REST
├── dto/             # Data Transfer Objects
├── exception/       # Excepciones personalizadas
├── factory/         # Factory para creación y mapeo de objetos
├── migrations/      # Scripts de migración de BD (Mongock)
├── model/           # Entidades de dominio
├── repository/      # Interfaces de repositorio
├── security/        # Servicios de JWT y filtros de autenticación
└── service/         # Lógica de negocio
    └── impl/        # Implementaciones de servicios
```