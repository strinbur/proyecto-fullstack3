# Fullstack 3 Grupo Cordillera

## Contexto

Este proyecto es una demostración de una arquitectura de microservicios para una plataforma de e-commerce de Grupo Cordillera. La arquitectura está diseñada para proporcionar escalabilidad, mantenibilidad y separación de responsabilidades, permitiendo que cada componente del sistema funcione de forma independiente.

---

## Descripción General

Este repositorio contiene una solución completa de fullstack con arquitectura de microservicios. Incluye un Frontend en React/TypeScript que consume un BFF (Backend for Frontend), el cual actúa como punto central de comunicación con múltiples microservicios especializados. El sistema implementa autenticación y autorización basada en JWT, persistencia de datos en MongoDB, y está completamente containerizado con Docker para facilitar el despliegue.

---

## Componentes Principales

### Frontend
- [Ver README de frontend](./frontend/README.md)

### Backend - Microservicios
- [Ver README de bff](./backend/bff/README.md)
- [Ver README de ms-login](./backend/ms-login/README.md)
- [Ver README de ms-inventory](./backend/ms-inventory/README.md)
- [Ver README de ms-cart](./backend/ms-cart/README.md)
- [Ver README de ms-order](./backend/ms-order/README.md)

---

## Diagrama C3 - Componentes del Sistema

```
┌─────────────────────────────────────────────────────────────────────────┐
│                     Frontend (React + TypeScript)                        │
│  ┌────────────────┐  ┌──────────────┐  ┌─────────────────────────────┐ │
│  │  App Router    │  │  Components  │  │  Features (Context)         │ │
│  │  - Home        │  │  - Navbar    │  │  - AuthContext/Provider     │ │
│  │  - Login       │  │  - Header    │  │  - InventoryAPI             │ │
│  │  - Register    │  │  - Footer    │  │  - CartAPI                  │ │
│  │  - Products    │  │  - Protected │  │  - OrderAPI                 │ │
│  └────────────────┘  └──────────────┘  └─────────────────────────────┘ │
└────────────────────────────┬────────────────────────────────────────────┘
                             │ HTTP/JSON + JWT
    ┌────────────────────────▼───────────────────────────┐
    │          BFF (Spring Boot - Port 8080)             │
    │  ┌──────────────┐  ┌──────────────┐  ┌──────────┐ │
    │  │ Controllers  │  │ Feign Clients│  │ Service  │ │
    │  │ - Login      │  │ - LoginClient │  │ Layer    │ │
    │  │ - Inventory  │  │ - Inventory  │  │(Adapters)│ │
    │  │ - Cart       │  │ - CartClient │  └──────────┘ │
    │  │ - Order      │  │ - OrderClient│                │
    │  └──────────────┘  └──────────────┘  ┌──────────┐ │
    │                                       │Interceptor│ │
    │                                       │(JWT PropG)│ │
    │                                       └──────────┘ │
    └────┬─────────────┬──────────────┬─────────────┬──┘
         │             │              │             │
    ┌────▼──┐  ┌──────▼──┐  ┌──────▼───┐  ┌──────▼──┐
    │ms-lgn │  │ms-invent│  │ ms-cart  │  │ms-order│
    │:8081  │  │:8082    │  │  :8083   │  │ :8084  │
    └───────┘  └─────────┘  └──────────┘  └────────┘
         │             │              │             │
         └─────────────┴──────────────┴─────────────┘
                      │
           ┌──────────▼──────────┐
           │  MongoDB Database   │
           │  (puerto 27017)     │
           │  - login_bd         │
           │  - inventory_bd     │
           │  - cart_bd          │
           │  - order_bd         │
           └─────────────────────┘
```

---

## Requerimientos Funcionales

Los siguientes puntos representan el alcance funcional esperado de la plataforma y los objetivos de esta demo:

- Autenticación y autorización de usuarios
- Gestión integral del inventario de productos
- Visualización de dashboard con información relevante
- Gestión de perfil de usuario
- Operaciones CRUD completas en inventario

---

## Requerimientos No Funcionales

- **Escalabilidad**: Arquitectura de microservicios para escalado independiente
- **Mantenibilidad**: Código modular y documentación clara
- **Rendimiento**: Respuesta rápida (< 3s) en operaciones críticas
- **Seguridad**: Autenticación JWT y autorización basada en roles
- **Disponibilidad**: Despliegue mediante Docker Compose para reproducibilidad

---

## Estructura del Repositorio

```
.
├── frontend/                  # Aplicación frontend (React + TypeScript + Vite)
│   ├── src/
│   │   ├── api/               # Configuración de cliente HTTP (Axios)
│   │   ├── components/        # Componentes reutilizables
│   │   ├── features/          # Funcionalidades (auth, inventory, cart)
│   │   ├── pages/             # Páginas de la aplicación
│   │   └── utils/             # Utilidades y helpers
│   ├── Dockerfile
│   └── package.json
├── backend/                   # Microservicios backend (Java + Spring Boot)
│   ├── bff/                   # Backend for Frontend (API Gateway)
│   ├── ms-login/              # Microservicio de Autenticación
│   ├── ms-inventory/          # Microservicio de Inventario
│   ├── ms-cart/               # Microservicio de Carrito
│   ├── ms-order/              # Microservicio de Órdenes
│   └── README.md
├── k8s/                       # Configuración de Kubernetes
├── docker-compose.yml         # Orquestación de servicios con Docker Compose
└── README.md                  # Este archivo
```

## Requisitos Previos

- **Docker** y **Docker Compose** (recomendado para desarrollo)
- **Node.js** >= 18 (para desarrollo local del frontend)
- **npm** (gestor de paquetes Node)
- **Java JDK** 25 (para desarrollo local de microservicios)
- **Maven** (incluido mediante wrapper `mvnw`/`mvnw.cmd`)

## Levantamiento del Proyecto

### Opción 1: Con Docker Compose (Recomendado)

1. **Construir e iniciar todos los servicios**:

```bash
docker-compose up --build
```

2. **Acceder a los servicios**:

- Frontend: http://localhost:5173
- BFF: http://localhost:8080
- ms-login: http://localhost:8081
- ms-inventory: http://localhost:8082
- ms-cart: http://localhost:8083
- ms-order http://localhost:8084
- MongoDB: localhost:27017

3. **Detener los servicios**:

```bash
docker-compose down
```

4. **Detener y eliminar volúmenes de datos**:

```bash
docker-compose down -v
```

### Opción 2: Desarrollo Local (Sin Docker)

#### Frontend

```bash
cd front
npm install
npm run dev
```

El frontend estará disponible en http://localhost:5173

## Arquitectura del Sistema


<img width="1324" height="989" alt="Fullstack 3 diagrama drawio" src="https://github.com/user-attachments/assets/1014b7be-8d6a-4904-9918-37850dfb4f55" />


El sistema implementa una arquitectura de microservicios donde:
- El **Frontend** se comunica únicamente con el **BFF**
- El **BFF** orquesta las llamadas a ms-login y ms-inventory
- Cada microservicio usa su propia base de datos dentro de MongoDB
- MongoDB actúa como almacenamiento centralizado con bases de datos independientes por servicio

## Guía de Contribución

Para mantener la calidad y consistencia del proyecto, seguir estos pasos:

### 1. Configuración Inicial

```bash
git clone https://github.com/tu-organizacion/proyecto-fullstack3.git
cd proyecto-fullstack3
```

### 2. Crear una Rama de Características

Las ramas siempre deben derivar de la rama `develop`:

```bash
git checkout develop
git pull origin develop
git checkout -b feature-nombre-tarea-o-colaborador
```

**Nomenclatura de ramas**: `feature-descripcion` o `feature-nombre-colaborador`

Ejemplo: `feature-login-validation` o `feature-juan-perez`

### 3. Realizar Cambios

- Mantener commits pequeños y descriptivos
- Seguir los estándares de codificación del proyecto
- Actualizar documentación según sea necesario
- Ejecutar pruebas locales antes de hacer push

```bash
git add .
git commit -m "feat: descripción clara del cambio"
```

### 4. Enviar Pull Request

```bash
git push origin feature-nombre-tarea-o-colaborador
```

Crear un Pull Request hacia `develop` con:
- Título descriptivo
- Descripción detallada de cambios

### 5. Revisión y Merge

- Esperar revisión de código por al menos un miembro del equipo
- Abordar comentarios y feedback
- Una vez aprobado, hacer merge a `develop`

---

## Gestión del Proyecto

Para ver el estado de tareas y seguimiento del proyecto:

[📋 Tablero de Gestión (Trello) - Grupo Cordillera](https://trello.com/invite/b/67f58f82a158aeba95daa089/ATTI81f05e6842618d1af0297687e8fd257eA1481734/fullstack-3)
