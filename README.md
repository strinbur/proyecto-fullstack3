# Fullstack-3-Grupo-Cordillera

Proyecto fullstack desarrollado por el Grupo Cordillera. Contiene un frontend en React (Vite) y dos microservicios backend en Spring Boot que usan MongoDB.

## Contenido
- `front/` – Aplicación frontend (React + Vite)
- `inventario/` – Servicio backend Inventario (Spring Boot, MongoDB)
- `login/` – Servicio backend Login (Spring Boot, MongoDB)

## Stack tecnológico

- Frontend:
  - React 19
  - Vite
  - react-router-dom
  - Desarrollo con Node.js / npm

- Backend (cada servicio):
  - Java (versión en los `pom.xml`: 25)
  - Spring Boot (starter parent 4.x)
  - MongoDB (drivers Spring Data MongoDB)
  - Lombok (opcional en tiempo de compilación)

## Requisitos previos

- Node.js (recomendado >= 18)
- npm
- Java (según `pom.xml` — la configuración actual indica `java.version=25`; usar la JDK que prefieras compatible)
- Maven (o usar el wrapper `mvnw` / `mvnw.cmd` incluido)
- MongoDB corriendo localmente o una URI accesible

## Configuración y ejecución

1) Frontend

```bash
cd front
npm install
npm run dev
```

El frontend usa Vite y servirá típicamente en `http://localhost:5173` (por defecto de Vite).

2) Backend — `inventario` y `login`

Cada servicio es una aplicación Spring Boot. En Windows puedes usar el wrapper:

```powershell
cd inventario
.\mvnw.cmd spring-boot:run
# en otra terminal
cd login
.\mvnw.cmd spring-boot:run
```

O con Maven instalado:

```bash
mvn -f inventario spring-boot:run
mvn -f login spring-boot:run
```

Por defecto Spring Boot usa el puerto `8080`. Si ambos servicios arrancan sin configurar puertos específicos, habrá conflicto. Se recomienda configurar `server.port` en `src/main/resources/application.yaml` de cada servicio (por ejemplo `8081` y `8082`).

### MongoDB

Los `application.yaml` actuales apuntan a URIs locales:

- `inventario`: `mongodb://localhost:27017/inventario_bd`
- `login`: `mongodb://localhost:27017/login_bd`

Si usas un servicio remoto, puedes cambiar estas URIs o proporcionar la variable de entorno correspondiente que uses en tu configuración.

## Variables de entorno sugeridas

- `SPRING_DATA_MONGODB_URI` o configurar `spring.mongodb.uri` en `application.yaml`.
- `SERVER_PORT` (o `server.port` en `application.yaml`) para cambiar puertos de los servicios.

## Estructura del repositorio

- `front/` — código fuente del cliente React
- `inventario/` — microservicio Inventario (Java/Spring)
- `login/` — microservicio Login (Java/Spring)

## Testing

Para ejecutar pruebas unitarias en los servicios Java:

```bash
mvn -f inventario test
mvn -f login test
```

## Contribuir

1. Crear una rama (`git checkout -b feature/mi-cambio`)
2. Hacer commits pequeños y claros
3. Abrir un Pull Request describiendo el cambio

## Diagrama de draw.io
<img width="1324" height="989" alt="Fullstack 3 diagrama drawio" src="https://github.com/user-attachments/assets/b9872c5e-cb80-4673-a76a-ac6345d95740" />


## Trello 
[📋 Acceder al Tablero de Gestión (Trello) - Grupo Cordillera](https://trello.com/invite/b/67f58f82a158aeba95daa089/ATTI81f05e6842618d1af0297687e8fd257eA1481734/fullstack-3)



```mermaid

---
config:
  layout: fixed
---
flowchart LR
 subgraph Vercel["Vercel"]
        Frontend["Frontend <br> React + Vite"]
  end
 subgraph K8s["Kubernetes"]
        Ingress["Ingress Controller <br> Traefik"]
        Gateway["API Gateway <br> Krakend"]
        BFF["BFF <br> Spring Boot + Java"]
        PVC_C("PVC Carrito")
        BD_C[("BD Carrito <br> Mongo 8")]
        PVC_I("PVC Inventario")
        BD_I[("BD Inventario <br> Mongo 8")]
        PVC_Co("PVC Compra")
        BD_Co[("BD Compra <br> Mongo 8")]
        PVC_R("PVC Reportes")
        BD_R[("BD Reportes <br> Mongo 8")]
        PVC_U("PVC Usuario")
        BD_U[("BD Usuario <br> Mongo 8")]
        Gestion["Gestión Datos <br> Spring Boot + Java 25"]
        KPI["KPI <br> FastAPI + Python 3.14"]
        PVC_G("PVC Gestión")
        BD_G[("BD Gestión <br> Mongo 8")]
        PVC_K("PVC KPI")
        BD_K[("BD KPI <br> Mongo 8")]
  end
    BD_C --- PVC_C
    BD_I --- PVC_I
    BD_Co --- PVC_Co
    BD_R --- PVC_R
    BD_U --- PVC_U
    BD_G --- PVC_G
    BD_K --- PVC_K
    Usuario(("Usuario")) --> Vercel
    Frontend --> Ingress
    Ingress --> Gateway
    Gateway --> BFF
    BFF --> Compra["Compra <br> Spring Boot + Java 25"] & Inventario["Inventario <br> Spring Boot + Java 25"] & Carrito["Carrito <br> Spring Boot + Java 25"] & Reportes["Reportes <br> FastAPI + Python 3.14"] & UserMS["Usuario <br> Spring Boot + Java 25"]
    Carrito --> BD_C
    Inventario --> BD_I & Gestion
    Compra --> BD_Co & Inventario & Gestion
    Reportes --> BD_R
    UserMS --> BD_U
    Gestion --> KPI & BD_G
    KPI --> BD_K & Reportes
    BD_G --> BD_K

    classDef cloud fill:#f9f9f9,stroke:#333,stroke-width:2px
    classDef service fill:#fff,stroke:#0055ff,stroke-width:2px
    classDef database fill:#fff,stroke:#0055ff,stroke-dasharray: 5 5
    classDef storage fill:#fff,stroke:#0055ff,stroke-width:1px

    L_BFF_UserMS_0@{ curve: linear }

```





























