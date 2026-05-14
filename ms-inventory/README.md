# Microservicio de Inventario (ms-inventory)

## Descripción
Este microservicio gestiona el inventario de productos para la aplicación fullstack. Proporciona endpoints para crear, leer, actualizar y eliminar productos, con soporte para categorías y atributos dinámicos.

## Stack Tecnológico
- **Lenguaje**: Java 25
- **Framework**: Spring Boot 4.0.6
- **Base de Datos**: MongoDB
- **Autenticación**: JWT (JSON Web Tokens)
- **Documentación**: OpenAPI/Swagger
- **Herramientas**: Lombok, Mongock (para migraciones de BD), Spring Security

## Patrones de Diseño
Este microservicio implementa varios patrones de diseño para mantener un código modular, mantenible y escalable:

- **Repository Pattern**: Utilizado para el acceso a datos con `MongoRepository`, abstrae las operaciones CRUD sobre MongoDB.
- **Service Layer Pattern**: La lógica de negocio se encapsula en servicios (`InventoryService`), separando la lógica de los controladores.
- **DTO (Data Transfer Object) Pattern**: Se usan DTOs (`InventoryCreateDTO`, `InventoryResponseDTO`, etc.) para transferir datos entre capas sin exponer entidades internas.
- **Factory Pattern**: `InventoryFactory` se utiliza para crear y mapear objetos de inventario, aplicando reglas de negocio durante la creación.
- **Dependency Injection**: Spring maneja la inyección de dependencias a través del constructor, promoviendo el bajo acoplamiento.
- **MVC (Model-View-Controller)**: Estructura básica con controladores REST, servicios y modelos de datos.
- **Singleton Pattern**: Los beans de Spring (servicios, repositorios) son singletons por defecto.
- **Strategy Pattern**: Implementado en la autenticación JWT, donde `JwtService` maneja la generación y validación de tokens.
- **Filter Pattern**: `JwtAuthenticationFilter` intercepta requests para validar tokens JWT.
- **Builder Pattern**: Facilitado por Lombok en modelos y DTOs para construcción de objetos.

## Reglas de Negocio y Validaciones
Este microservicio implementa las siguientes reglas de negocio y validaciones:

### Validaciones de Productos
- **Campos obligatorios**: Código, nombre, marca, categoría.
- **Código único**: No puede existir otro producto con el mismo código.
- **Precio**: Debe ser mayor o igual a 0.
- **Cantidad**: Debe ser mayor o igual a 0.
- **Producto debe existir**: Para actualizar o eliminar, el producto debe existir por código.

### Reglas de Negocio Automáticas
- **Categoría "Tecnología"**: Agrega automáticamente atributo "garantia" con valor "12 meses".
- **Cantidad cero**: Agrega automáticamente atributo "estado" con valor "sin stock".
- **Atributos dinámicos**: Soporta atributos adicionales como Map<String, Object>.

### Autenticación y Autorización
- **Listar productos**: Público (sin autenticación requerida).
- **Crear/Actualizar/Eliminar productos**: Requiere rol ADMIN.
- **JWT**: Tokens expiran en 24 horas (86400000 ms).

### Manejo de Errores
- Se lanzan excepciones `ResponseStatusException` con códigos HTTP apropiados (400 para validaciones, 404 para no encontrado, etc.).

## Dependencias Principales
- `spring-boot-starter-data-mongodb`: Para integración con MongoDB
- `spring-boot-starter-webmvc`: Para crear APIs REST
- `spring-boot-starter-security`: Para seguridad y autenticación
- `jjwt-api`, `jjwt-impl`, `jjwt-jackson`: Para manejo de tokens JWT
- `mongock-springboot-v3`: Para migraciones de base de datos
- `springdoc-openapi-starter-webmvc-ui`: Para documentación OpenAPI
- `spring-boot-starter-validation`: Para validación de datos
- `lombok`: Para reducir código boilerplate
- `spring-boot-devtools`: Para desarrollo (hot reload)

## Puerto
El microservicio utiliza el puerto **8082**.

## Configuración de Base de Datos
- **URI**: `mongodb://host.docker.internal:27017/inventory_bd`
- Requiere MongoDB corriendo en el puerto 27017 (típicamente en Docker)

## Levantamiento Individual
Si necesitas levantar este microservicio de forma individual (fuera del docker-compose), sigue estos pasos:

### Prerrequisitos
- Java 25 instalado
- Maven instalado
- MongoDB corriendo (puerto 27017)

### Opción 1: Ejecutar directamente con Maven (Recomendado para desarrollo)
```bash
mvn spring-boot:run
```

### Opción 2: Compilar y ejecutar JAR
```bash
# Compilar el proyecto
mvn clean install

# Ejecutar el JAR generado
java -jar target/ms-inventory-0.0.1-SNAPSHOT.jar
```

## Endpoints Principales
- `GET /inventario`: Listar todos los productos (público), opcional `?categoria={categoria}`
- `GET /inventario/{codigo}`: Obtener producto por código
- `POST /inventario`: Crear nuevo producto (requiere ADMIN)
- `PUT /inventario/{codigo}`: Actualizar producto (requiere ADMIN)
- `DELETE /inventario/{codigo}`: Eliminar producto (requiere ADMIN)

## Documentación API
Una vez levantado, accede a la documentación Swagger en:
`http://localhost:8082/swagger-ui.html`

## Notas
- Este microservicio forma parte de una arquitectura de microservicios completa.
- Para un levantamiento completo del sistema, usa `docker-compose up` desde la raíz del proyecto.
- Las migraciones de base de datos se ejecutan automáticamente al iniciar gracias a Mongock.

## Testing
Para ejecutar las pruebas unitarias:
```bash
mvn test
```

Actualmente incluye pruebas básicas de integración de Spring Boot.

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