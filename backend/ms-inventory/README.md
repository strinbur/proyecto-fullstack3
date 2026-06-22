# Microservicio de Inventario (ms-inventory)

## Descripción
Microservicio responsable de la gestión del inventario de productos. Ofrece endpoints para crear, leer, actualizar y eliminar productos, con validaciones y reglas de negocio orientadas a inventario, y seguridad basada en JWT.

---

## Diagrama C3 - Componentes de ms-inventory

<img width="1324" alt="Diagrama C3 Login" src="../../docs/diagrams//Fullstack%203%20diagrama%20c3%20inventory.drawio.png" />

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
   - Acceso a datos con repositorios reactivos

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

## Principales Patrones de Diseño
- **Repository Pattern**: Acceso a datos con `MongoRepository`.
- **Service Layer Pattern**: Lógica de negocio separada de los controladores.
- **DTO Pattern**: Transferencia de datos con objetos específicos.
- **Factory Pattern**: `InventoryFactory` mapea y crea objetos respetando reglas de negocio.
- **Filter Pattern**: `JwtAuthenticationFilter` valida tokens en cada petición.
- **Dependency Injection**: Spring gestiona dependencias para bajo acoplamiento.

## Endpoints Principales
- `GET /inventory` — Listar productos (público), opcional `?category={category}`
- `GET /inventory/code/{code}` — Obtener producto por código (restringido según configuración de seguridad)
- `POST /inventory` — Crear producto (requiere rol `ADMIN`)
- `PUT /inventory/code/{code}` — Actualizar producto (requiere rol `ADMIN`)
- `DELETE /inventory/code/{code}` — Eliminar producto (requiere rol `ADMIN`)

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

### Autenticación y Autorización
- **Endpoint público**: `GET /inventory` y `GET /inventory/code/{code}` (sin token requerido)
- **Endpoints protegidos**: Las operaciones de escritura, edición y borrado (`POST`, `PUT`, `DELETE`) requieren autenticación
- **Control de acceso**: Solo usuarios con rol `ADMIN` pueden crear, modificar o eliminar productos
- **Token JWT**: Validez de 24 horas (`86400000 ms`), incluye información de rol y usuario
- **Propagación**: El BFF propaga el token `Authorization` en todas las peticiones

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

### Estructura de Directorios
```
src/main/java/com/grupocordillera/ms_inventory/
├── config/             # Configuraciones de Spring
│   ├── SecurityConfig.java
│   ├── MongockConfig.java
│   └── WebConfig.java
├── controller/         # Controladores REST
│   └── InventoryController.java
├── dto/                # Data Transfer Objects
│   ├── ProductRequestDTO.java
│   └── ProductResponseDTO.java
├── exception/          # Excepciones personalizadas
│   ├── InventoryException.java
│   └── GlobalExceptionHandler.java
├── factory/            # Factory Pattern
│   └── InventoryFactory.java
├── migrations/         # Scripts de migración (Mongock)
│   ├── ChangeLogs.java
│   └── InitialData.java
├── model/              # Entidades de dominio
│   ├── Product.java
│   └── Category.java
├── repository/         # Interfaces MongoRepository
│   └── ProductRepository.java
├── security/           # JWT y Autenticación
│   ├── JwtProvider.java
│   ├── JwtAuthenticationFilter.java
│   └── SecurityUtil.java
├── service/            # Lógica de negocio
│   ├── InventoryService.java
│   └── impl/
│       └── InventoryServiceImpl.java
└── MsInventoryApplication.java
```

### Patrones de Arquitectura
- **Layered Architecture**: Separación clara entre capas (Controller → Service → Repository)
- **Hexagonal Architecture**: Independencia de frameworks externos
- **Domain-Driven Design**: Entidades de dominio bien definidas
- **SOLID Principles**: Responsabilidad única, abierto/cerrado, inversión de dependencias