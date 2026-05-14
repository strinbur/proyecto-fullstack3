# Proyecto Fullstack3 — Documentación

Resumen breve
----------------
Aplicación fullstack basada en un conjunto de microservicios Java (Spring Boot) y un frontend en React + Vite. Contiene:
- `front`: SPA en React (Vite, TypeScript).
- `ms-bff`: Backend-for-Frontend (BFF) que orquesta llamadas a microservicios.
- `ms-login`: Microservicio de autenticación (JWT + MongoDB).
- `ms-inventory`: Microservicio de inventario (MongoDB).

**Contexto**
----------------
Este proyecto busca separar responsabilidades en microservicios para manejar autenticación y gestión de inventario, y exponer una experiencia unificada al cliente mediante un BFF. Está pensado para entornos de desarrollo con Docker Compose y despliegue local.

**Problemas identificados**
----------------
- Necesidad de desacoplar responsabilidades de autenticación y datos de inventario.
- Gestión de credenciales y tokens (JWT) entre cliente y servicios.
- Configuración de infraestructura reproducible (bases de datos, puertos, volúmenes).
- Comunicaciones seguras y manejo de timeouts entre servicios.
- Necesidad de migraciones y control de esquema para MongoDB.

**Conclusión / Cómo se solucionó**
----------------
Se optó por una arquitectura basada en microservicios con un BFF central que expone la API al frontend. Cada servicio es responsable de su dominio y datos (MongoDB). El proyecto se orquesta con `docker-compose` para levantar contenedores reproducibles y con un volumen persistente para MongoDB (`mongo_data`). Para las migraciones se usa Mongock; para la seguridad se usa JWT manejado por `ms-login`.

Requisitos
----------------

Requisitos funcionales
- Autenticación de usuarios (login/register) con JWT.
- Endpoints para CRUD de productos/inventario.
- Frontend que consume el BFF y mantiene sesión (token en localStorage).

Requisitos no funcionales
- Despliegue reproducible con Docker Compose.
- Persistencia de datos de MongoDB en volumen Docker.
- Timeouts y resiliencia en llamadas inter-servicio (OpenFeign timeouts configurados).
- Documentación mínima (OpenAPI / springdoc para servicios).

README técnico — Levantamiento con Docker
----------------

Requisitos previos
- Docker y Docker Compose instalados.

Comandos básicos

```bash
# Construir y levantar (desarrollo)
docker compose up --build

# Levantar en background
docker compose up -d --build

# Parar y eliminar contenedores (mantiene volúmenes a menos que se indique)
docker compose down

# Eliminar contenedores y volúmenes (cuidado: borra datos)
docker compose down -v
```

Variables de entorno y configuración
- El repositorio incluye `docker-compose.yml` que define nombres de contenedor y un volumen persistente:
  - Contenedores: `mongodb`, `ms-login`, `ms-inventory`, `ms-bff`, `front`.
  - Volumen persistente: `mongo_data` (contiene datos de MongoDB).

- Propiedades Spring que se pueden sobrescribir con variables de entorno (ejemplos):
  - `SPRING_APPLICATION_NAME` — nombre de la app.
  - `SERVER_PORT` — puerto del servicio (ej. 8081, 8082, 8080).
  - `SPRING_MONGODB_URI` — URI de conexión a MongoDB (ej. `mongodb://mongodb:27017/inventory_bd`).
  - `JWT_SECRET_KEY` — secreto para firmar tokens JWT (mapea a `jwt.secret.key`).

Ejemplo de `.env` local (opcional)

```env
# Variables para sobrescribir en docker compose (opcional)
JWT_SECRET_KEY=f9A2kL7pQzR4vX9mN3bH5jW8sT1yC6xD
SPRING_MONGODB_URI=mongodb://mongodb:27017
FRONT_API_BASE=http://ms-bff:8080/bff
```

Notas sobre el frontend
- El cliente Axios por defecto apunta a `http://localhost:8080/bff` en desarrollo (`front/src/api/api.ts`).
- En entorno Docker, para que el `front` dentro de un contenedor apunte al BFF, cambie la `baseURL` a `http://ms-bff:8080/bff` o use una variable de entorno en tiempo de build (por ejemplo `VITE_API_BASE_URL` / `FRONT_API_BASE`) y adapte `api.ts` para `import.meta.env.VITE_API_BASE_URL`.

Persistencia y nombres
----------------
- Nombre del volumen: `mongo_data` (definido en `docker-compose.yml`).
- Nombres de contenedor (fáciles de reconocer): `mongodb`, `ms-login`, `ms-inventory`, `ms-bff`, `front`.

Arquitectura y diagrama (alta nivel)
----------------

Diagrama ASCII (flujo):

```
 [Browser/React Front] --(HTTP)--> [ms-bff (BFF) ] --(Feign/HTTP)--> [ms-login]
                                             |--(Feign/HTTP)--> [ms-inventory]
                                                       
                      [MongoDB <-> ms-login/ms-inventory]
```

Descripción del arquetipo elegido
- Arquetipo: Microservicios con BFF (Backend For Frontend).
  - Motivo: desacoplar UI de múltiples servicios backend, centralizar políticas de sesión, CORS y agregación de datos.

Librerías clave por pieza
- `front` (React + Vite):
  - `react`, `react-dom`, `react-router-dom` — UI y rutas.
  - `axios` — llamadas HTTP; configuración de interceptors para JWT.
  - `vite`, `typescript` — tooling.

- `ms-bff`:
  - Spring Boot Web, OpenFeign — cliente HTTP declarativo hacia microservicios.
  - Configuración de timeouts y retry a nivel de feign (según `application.yaml`).

- `ms-login` y `ms-inventory`:
  - `spring-boot-starter-data-mongodb` — acceso a MongoDB.
  - `spring-boot-starter-web` — controllers / REST endpoints.
  - `spring-boot-starter-security` (en `ms-login`) + `jjwt` — autenticación JWT.
  - `mongock` — migraciones de esquema/datos para MongoDB.
  - `springdoc-openapi` — documentación automática de API.

Patrones de diseño aplicados (por servicio)
- Front:
  - Context/Provider: `AuthContext` para manejar sesión y estado auth global.
  - Protected Route: `ProtectedRoute` para rutas que requieren autenticación.

- ms-bff:
  - Façade / Aggregator: expone una API simple al cliente y orquesta llamadas a varios microservicios.
  - Client (OpenFeign): desacopla implementación de llamadas HTTP.

- ms-login / ms-inventory:
  - Controller-Service-Repository: separación de responsabilidad (Controllers expone endpoints, Services contienen lógica de negocio, Repositories acceden a MongoDB).
  - DTOs y mappers para separar modelo de dominio vs payloads HTTP.
  - Security: `ms-login` implementa emisión/verificación de JWT.
  - Migraciones: Mongock para versionado de cambios en la base de datos.


Archivo específico de interés:
- [Docker Compose root](docker-compose.yml)
- Frontend API: [front/src/api/api.ts](front/src/api/api.ts)
- ms-login config: [ms-login/src/main/resources/application.yaml](ms-login/src/main/resources/application.yaml)
- ms-inventory config: [ms-inventory/src/main/resources/application.yaml](ms-inventory/src/main/resources/application.yaml)


## Contribuir

1. Crear una rama (`git checkout -b feature-mi-cambio`)
2. Hacer commits pequeños y claros
3. Abrir un Pull Request describiendo el cambio

## Diagrama de draw.io
<img width="1324" height="989" alt="Fullstack 3 diagrama drawio" src="https://github.com/user-attachments/assets/b9872c5e-cb80-4673-a76a-ac6345d95740" />


## Trello 
[📋 Acceder al Tablero de Gestión (Trello) - Grupo Cordillera](https://trello.com/invite/b/67f58f82a158aeba95daa089/ATTI81f05e6842618d1af0297687e8fd257eA1481734/fullstack-3)

