# Fullstack 3 Grupo Cordillera

## Contexto

En este documento se dara a conocer el diseño arquitectonico para la nueva plataforma de monitoreo de Grupo Cordillera. Esta empresa se centra en la comercialización de productos para el hogar y tecnologia. Actual mente presenta el desafio para la gestion y analisis de informacion de sus sistemas, nosotros proponemos un sistema basado en microservicios, gracias a este enfoque nos permitira consolidar la informacion, asegurar su disponibilidad y facilitar la toma de desiciones generales de la empresa.


---

## Descripción General

Este repositorio es una demostracion funcional para el Grupo Cordillera. La cual implementa una arquitectura basada en microservicios desacoplados e integrados a traves de un BFF, la cual cuenta con un Frontend que maneja la sesion con AtuhContext y persistencia de datos basicos en localstorage, el BFF centralizar la comunicacion entre el frontend y los microservicios, ambos microservicios usan validacion mediante JWT.

---

## Componentes Principales

## front
[Ver README de front](./front/README.md)

## ms-bff
[Ver README de ms-bff](./ms-bff/README.md)

## ms-login
[Ver README de ms-login](./ms-login/README.md)

## ms-inventory
[Ver README de ms-inventory](./ms-inventory/README.md)

## ms-cart
[Ver README de ms-cart](./ms-cart/README.md)

## ms-cart
[Ver README de ms-order](./ms-order/README.md)

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
├── front/                  # Aplicación frontend (React + Vite)
├── ms-bff/                 # Backend for Frontend (Spring Boot)
├── ms-login/               # Microservicio de Autenticación (Spring Boot)
├── ms-inventory/           # Microservicio de Inventario (Spring Boot)
├── ms-cart/                # Microservicio de Carrito (Spring Boot)
├── ms-order/               # Microservicio de Compra (Spring Boot)
├── docker-compose.yml      # Orquestación de contenedores
└── README.md              # Este archivo
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
