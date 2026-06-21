# Microservicio de Login (ms-login)

## Descripción
Este microservicio maneja la autenticación y la gestión de usuarios para la aplicación fullstack. Proporciona endpoints para registro, inicio de sesión, consulta, actualización y eliminación de usuarios, con seguridad basada en JWT y roles.

---

## Diagrama C3 - Componentes de ms-login

---

<img width="1324" alt="Diagrama C3 Login" src="../../docs/Fullstack%203%20diagrama%20c3%20login.drawio.png" />

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
   - Persistencia de usuarios y roles

2. **Mongock** (`mongock-springboot-v3`)
   - Migraciones de base de datos en MongoDB
   - Versionado y control de cambios en la BD

3. **Lombok** (`lombok`)
   - Generación automática de getters/setters
   - Reduce código boilerplate significativamente

4. **JWT** (`jjwt-api`, `jjwt-impl`, `jjwt-jackson`)
   - Creación y validación de JSON Web Tokens
   - Autenticación segura con expiración configurable

5. **Spring Validation** (`spring-boot-starter-validation`)
   - Validación de DTOs y datos de entrada
   - Anotaciones declarativas para validación

---

## Principales Patrones de Diseño
- **Repository Pattern**: Acceso a datos con `MongoRepository` para abstraer la BD.
- **Service Layer Pattern**: Lógica de negocio separada de controladores.
- **DTO Pattern**: Transferencia de datos entre capas con objetos específicos.
- **Filter Pattern**: `JwtAuthenticationFilter` valida tokens en cada petición.
- **Factory Pattern**: Creación de usuarios con roles específicos.
- **Dependency Injection**: Spring gestiona dependencias para reducir acoplamiento.
- **Strategy Pattern**: Diferentes estrategias de autenticación (JWT, roles).

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

## Testing
Ejecuta las pruebas unitarias con:
```bash
mvn test
```

## Notas
- Este microservicio forma parte de una solución de microservicios completa.
- Para levantar el sistema completo, usa `docker-compose up` desde la raíz del proyecto.
- Mongock ejecuta las migraciones de base de datos automáticamente al iniciar.

## Arquitectura del Proyecto

### Estructura de Directorios
```
src/main/java/com/grupocordillera/ms_login/
├── config/             # Configuraciones de Spring
│   ├── SecurityConfig.java
│   ├── MongockConfig.java
│   └── WebConfig.java
├── controller/         # Controladores REST
│   └── AuthController.java
├── dto/                # Data Transfer Objects
│   ├── LoginRequestDTO.java
│   ├── RegisterRequestDTO.java
│   ├── UserResponseDTO.java
│   └── AuthResponseDTO.java
├── exception/          # Excepciones personalizadas
│   ├── AuthException.java
│   ├── UserNotFoundException.java
│   └── GlobalExceptionHandler.java
├── factory/            # Factory Pattern
│   └── UserFactory.java
├── migrations/         # Scripts de migración (Mongock)
│   ├── ChangeLogs.java
│   └── InitialData.java
├── model/              # Entidades de dominio
│   ├── User.java
│   └── Role.java
├── repository/         # Interfaces MongoRepository
│   └── UserRepository.java
├── security/           # JWT y Autenticación
│   ├── JwtProvider.java
│   ├── JwtAuthenticationFilter.java
│   ├── PasswordEncoder.java
│   └── SecurityUtil.java
├── service/            # Lógica de negocio
│   ├── AuthService.java
│   └── impl/
│       └── AuthServiceImpl.java
└── MsLoginApplication.java
```

### Patrones de Arquitectura
- **Layered Architecture**: Separación clara entre capas (Controller → Service → Repository)
- **Hexagonal Architecture**: Independencia de frameworks externos
- **Domain-Driven Design**: Entidades de dominio bien definidas (User, Role)
- **SOLID Principles**: Responsabilidad única, abierto/cerrado, inversión de dependencias