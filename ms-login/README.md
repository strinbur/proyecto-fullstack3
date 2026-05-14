# Microservicio de Login (ms-login)

## Descripción
Este microservicio maneja la autenticación y gestión de usuarios para la aplicación fullstack. Proporciona endpoints para registro, login y validación de tokens JWT.

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
- **Service Layer Pattern**: La lógica de negocio se encapsula en servicios (`LoginService`), separando la lógica de los controladores.
- **DTO (Data Transfer Object) Pattern**: Se usan DTOs (`RegisterDTO`, `LoginResponseDTO`, etc.) para transferir datos entre capas sin exponer entidades internas.
- **Dependency Injection**: Spring maneja la inyección de dependencias a través del constructor, promoviendo el bajo acoplamiento.
- **MVC (Model-View-Controller)**: Estructura básica con controladores REST, servicios y modelos de datos.
- **Singleton Pattern**: Los beans de Spring (servicios, repositorios) son singletons por defecto.
- **Strategy Pattern**: Implementado en la autenticación JWT, donde `JwtService` maneja la generación y validación de tokens.
- **Filter Pattern**: `JwtAuthenticationFilter` intercepta requests para validar tokens JWT.
- **Builder Pattern**: Facilitado por Lombok en modelos y DTOs para construcción de objetos.
- **Factory Pattern**: El contenedor de Spring actúa como factory para crear y gestionar beans.

## Reglas de Negocio y Validaciones
Este microservicio implementa las siguientes reglas de negocio y validaciones:

### Roles de Usuario
- **CLIENTE**: Rol por defecto para usuarios registrados públicamente.
- **ADMIN**: Rol administrativo con permisos completos.
- **VENTAS**: Rol para operaciones de ventas.

### Validaciones de Registro
- **Campos obligatorios**: Nombre, apellido, correo, contraseña.
- **Correo**: Debe ser un email válido y único en el sistema.
- **Contraseña**: Mínimo 6 caracteres.
- **Rol**: Asignado automáticamente como CLIENTE en registro público.

### Validaciones de Login
- **Campos obligatorios**: Correo y contraseña.
- **Correo**: Debe ser un email válido.
- **Autenticación**: Verifica que el usuario exista y la contraseña coincida.

### Creación de Usuarios (por Admin)
- **Correo único**: No puede existir otro usuario con el mismo correo.
- **Rol**: Especificado en la solicitud (CLIENTE, ADMIN, VENTAS).

### Actualización de Usuarios
- **Campos no vacíos**: Nombre, apellido y correo no pueden estar vacíos.
- **Correo único**: Si se cambia el correo, debe ser único.

### Autenticación JWT
- **Expiración**: Tokens expiran en 24 horas (86400000 ms).
- **Claims**: Incluye rol, nombre y correo del usuario.
- **Endpoints protegidos**: Todos excepto registro y login requieren token válido.

### Manejo de Errores
- Se lanzan excepciones personalizadas (`LoginException`) para errores de negocio.
- Respuestas HTTP apropiadas (400 para validaciones, 401 para autenticación, etc.).

## Dependencias Principales
- `spring-boot-starter-data-mongodb`: Para integración con MongoDB
- `spring-boot-starter-web`: Para crear APIs REST
- `spring-boot-starter-security`: Para seguridad y autenticación
- `jjwt-api`, `jjwt-impl`, `jjwt-jackson`: Para manejo de tokens JWT
- `mongock-springboot-v3`: Para migraciones de base de datos
- `springdoc-openapi-starter-webmvc-ui`: Para documentación OpenAPI
- `spring-boot-starter-validation`: Para validación de datos
- `lombok`: Para reducir código boilerplate
- `spring-boot-devtools`: Para desarrollo (hot reload)

## Puerto
El microservicio utiliza el puerto **8081**.

## Configuración de Base de Datos
- **URI**: `mongodb://host.docker.internal:27017/login_bd`
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
java -jar target/ms-login-0.0.1-SNAPSHOT.jar
```

## Endpoints Principales
- `POST /api/auth/login`: Iniciar sesión
- `POST /api/auth/register`: Registrar nuevo usuario
- `GET /api/auth/validate`: Validar token JWT

## Documentación API
Una vez levantado, accede a la documentación Swagger en:
`http://localhost:8081/swagger-ui.html`

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
src/main/java/com/grupocordillera/ms_login/
├── config/          # Configuraciones de Spring (Seguridad, Mongock)
├── controller/      # Controladores REST
├── dto/             # Data Transfer Objects
├── exception/       # Excepciones personalizadas
├── migrations/      # Scripts de migración de BD (Mongock)
├── model/           # Entidades de dominio
├── repository/      # Interfaces de repositorio
├── security/        # Servicios de JWT y filtros de autenticación
└── service/         # Lógica de negocio
    └── impl/        # Implementaciones de servicios
```