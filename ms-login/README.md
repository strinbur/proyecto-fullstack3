# Microservicio de Login (ms-login)

## Descripción
Este microservicio maneja la autenticación y la gestión de usuarios para la aplicación fullstack. Proporciona endpoints para registro, inicio de sesión, consulta, actualización y eliminación de usuarios, con seguridad basada en JWT y roles.

## Stack Tecnológico
- **Lenguaje**: Java 25
- **Framework**: Spring Boot 4.0.6
- **Base de Datos**: MongoDB
- **Autenticación**: JWT (JSON Web Tokens)
- **Documentación**: OpenAPI/Swagger
- **Herramientas**: Lombok, Mongock, Spring Security

## Dependencias
- `spring-boot-starter-data-mongodb` — integración con MongoDB usando Spring Data
- `spring-boot-starter-web` — base para APIs REST con Spring MVC
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
- **Service Layer Pattern**: Lógica de negocio separada de controladores.
- **DTO Pattern**: Transferencia de datos entre capas con objetos específicos.
- **Filter Pattern**: `JwtAuthenticationFilter` valida tokens en cada petición.
- **Dependency Injection**: Spring gestiona dependencias para reducir acoplamiento.

## Endpoints Principales
- `POST /login/auth` — Iniciar sesión (público)
- `POST /login/register` — Registrar nuevo usuario (público, crea usuario con rol `CLIENTE` por defecto)
- `GET /login` — Listar usuarios (requiere rol `ADMIN` o `VENTAS`)
- `GET /login/{id}` — Obtener usuario por ID (requiere rol `ADMIN` o `VENTAS`)
- `PUT /login/{id}` — Actualizar usuario (requiere usuario autenticado: `ADMIN`, `VENTAS`, `CLIENTE`)
- `DELETE /login/{id}` — Eliminar usuario (requiere rol `ADMIN`)
- `POST /login/admin/create` — Crear usuario con rol (requiere rol `ADMIN`)

## Reglas de Negocio y Validaciones
### Roles de Usuario
- **CLIENTE**: Rol por defecto en registro público.
- **ADMIN**: Permisos administrativos.
- **VENTAS**: Permisos para operaciones de ventas.

### Registro
- Campos obligatorios: nombre, apellido, correo, contraseña.
- El correo debe ser válido y único.
- La contraseña debe tener mínimo 6 caracteres.
- El rol se asigna como `CLIENTE` en registro público.

### Login
- Campos obligatorios: correo y contraseña.
- El correo debe ser válido.
- Se verifica existencia del usuario y coincidencia de contraseña.

### Gestión de Usuarios
- El correo debe ser único al crear o actualizar un usuario.
- Nombre, apellido y correo no pueden quedar vacíos.
- El rol puede ser `CLIENTE`, `ADMIN` o `VENTAS`.

### Autenticación JWT
- Los tokens expiran en 24 horas (`86400000 ms`).
- Incluyen datos clave como rol, nombre y correo.
- Solo los endpoints de login y registro están abiertos sin token.

### Manejo de Errores
- Se usan excepciones personalizadas para errores de negocio.
- Respuestas HTTP adecuadas: 400 para validaciones, 401 para autenticación, etc.

## Configuración
### Puerto
- El servicio expone el puerto **8081**.

### Base de Datos
- URI por defecto: `mongodb://host.docker.internal:27017/login_bd`
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
java -jar target/ms-login-0.0.1-SNAPSHOT.jar
```

## Documentación API
Una vez levantado, accede a Swagger en:
`http://localhost:8081/swagger-ui.html`

## Notas
- Este microservicio forma parte de una solución de microservicios completa.
- Para levantar el sistema completo, usa `docker-compose up` desde la raíz del proyecto.
- Mongock ejecuta las migraciones de base de datos automáticamente al iniciar.

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