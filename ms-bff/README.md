# Microservicio BFF (ms-bff)

## Descripción
El BFF actúa como un punto de entrada unificado entre el frontend y los microservicios de backend (`ms-login` y `ms-inventory`). Expone rutas diseñadas para el front y delega las llamadas hacia los servicios internos usando Spring Cloud OpenFeign.

## Stack Tecnológico
- **Lenguaje**: Java 25
- **Framework**: Spring Boot 4.0.6
- **Comunicación**: Spring Cloud OpenFeign
- **Documentación**: OpenAPI/Swagger
- **Validación**: Spring Boot Validation
- **Herramientas**: Lombok, Spring Boot DevTools

## Patrones de Diseño
- **API Gateway / BFF Pattern**: Centraliza la comunicación entre frontend y microservicios, adaptando endpoints para el cliente.
- **Proxy / Gateway Pattern**: Reenvía peticiones a los microservicios de login e inventory.
- **Repository/Service Layer Pattern**: Aunque no maneja datos propios, separa lógica de negocio en servicios y controladores.
- **DTO Pattern**: Usa DTOs para transportar datos entre frontend y los servicios de backend.
- **Dependency Injection**: Spring inyecta clientes Feign y servicios de forma automática.
- **Facade Pattern**: Simplifica y unifica múltiples APIs de backend en una interfaz más amigable para el front.

## Cómo maneja los tokens JWT
- El token es emitido por el microservicio `ms-login` cuando el usuario inicia sesión.
- El frontend envía la cabecera `Authorization: Bearer <token>` al BFF.
- `FeignConfig` copia esta misma cabecera desde la petición entrante y la añade a todas las peticiones Feign hacia los backend.
- De este modo, los microservicios internos (`ms-login`, `ms-inventory`) reciben el token y pueden validar la autorización.

## Feign y configuración de clientes
- `@EnableFeignClients` está habilitado en `MsBffApplication`.
- `LoginClient` consume el servicio `ms-login` en `http://ms-login:8081`.
- `InventoryClient` consume el servicio `ms-inventory` en `http://ms-inventory:8082`.
- `FeignConfig` implementa un `RequestInterceptor` que:
  - obtiene la petición HTTP actual de `RequestContextHolder`
  - lee el header `Authorization`
  - lo reenvía en el header de la petición Feign hacia el backend

## Integración con el frontend
- El frontend debe conectarse al BFF en `http://localhost:8080` (o el host donde esté desplegado).
- Rutas expuestas para el front:
  - `POST /bff/login`: Login de usuario, devuelve token y datos del usuario.
  - `POST /bff/login/register`: Registro de usuario.
  - `POST /bff/login/admin`: Crear usuario con rol (admin only, pero la autorización la valida backend).
  - `PUT /bff/login/{id}`: Actualizar usuario.
  - `GET /bff/login/{id}`: Obtener usuario por ID.
  - `GET /bff/login`: Listar usuarios.
  - `DELETE /bff/login/{id}`: Eliminar usuario.
  - `GET /bff/inventory`: Listar productos, opcional `?categoria=`.
  - `POST /bff/inventory`: Crear producto.
  - `GET /bff/inventory/codigo/{codigo}`: Obtener producto por código.
  - `PUT /bff/inventory/codigo/{codigo}`: Actualizar producto.
  - `DELETE /bff/inventory/codigo/{codigo}`: Eliminar producto.

## Dependencias Principales
- `spring-boot-starter-webmvc`: Servidor web y controladores REST
- `spring-cloud-starter-openfeign`: Clientes Feign para llamadas a microservicios
- `spring-boot-starter-validation`: Validación de DTOs
- `springdoc-openapi-starter-webmvc-ui`: Documentación OpenAPI
- `spring-boot-devtools`: Desarrollo con recarga automática
- `lombok`: Reducción de código boilerplate
- `spring-boot-starter-test`: Pruebas unitarias

## Puerto
- El BFF utiliza el puerto **8080**.

## Configuración de Backends
- `ms-login`: `http://ms-login:8081`
- `ms-inventory`: `http://ms-inventory:8082`
- Tiempo de conexión Feign: 3000 ms
- Tiempo de lectura Feign: 5000 ms

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
Una vez levantado, accede a la documentación Swagger en:
`http://localhost:8080/swagger-ui.html`

## Notas
- El BFF es ideal para agrupar llamadas del frontend y manejar headers comunes como `Authorization`.
- Para un levantamiento completo de la solución, usa `docker-compose up` desde la raíz del proyecto.
- Si el frontend no envía `Authorization`, los endpoints protegidos en los backends devolverán errores de autorización.

## Arquitectura del Proyecto
```
src/main/java/com/grupocordillera/ms_bff/
├── config/          # Configuración de Feign y beans comunes
├── exception/       # Manejo global de errores
├── login/           # Controladores, DTOs y servicios para login
├── inventory/       # Controladores, DTOs y servicios para inventario
└── MsBffApplication.java
```