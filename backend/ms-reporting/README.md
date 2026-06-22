# Microservicio de Reportes (ms-reporting)

# Diagrama C3 - Componentes de reporting

<img width="1324" alt="Diagrama C3 Reporting" src="../../docs/diagrams/Fullstack 3 diagrama c3 reporting.drawio.png" />

## Descripción
Este microservicio centraliza la generación y consulta de reportes analíticos para la plataforma. Consume información desde `ms-analytics`, guarda un historial de solicitudes en MongoDB y expone endpoints REST que permiten obtener reportes completos, resúmenes de ventas, métricas de stock y ranking de clientes y productos.

---

## Stack Tecnológico
- Java 25
- Spring Boot 4.0.6
- Spring Cloud OpenFeign
- Spring Security
- Spring Data MongoDB
- Springdoc OpenAPI
- Maven

---

## Componentes principales
- `ReportController` — expone los endpoints REST.
- `ReportService` — implementa la lógica de negocio.
- `AnalyticsClient` — cliente Feign hacia `ms-analytics`.
- `ReportLogRepository` — repositorio MongoDB para historial de reportes.
- `SecurityConfig` y `JwtAuthenticationFilter` — seguridad JWT para proteger los endpoints.

---

## Endpoints
Todos los endpoints están protegidos por JWT, por lo que se debe enviar el header:

`Authorization: Bearer <token>`

- `GET /reports` — Reporte analítico completo
- `GET /reports/history` — Historial de solicitudes de reportes
- `GET /reports/sales-summary` — Resumen de ventas
- `GET /reports/top-products?topN=5` — Top productos (por defecto 5)
- `GET /reports/critical-stock` — Productos con stock crítico
- `GET /reports/revenue-by-category` — Ingresos por categoría
- `GET /reports/top-customers?topN=5` — Top clientes (por defecto 5)

---

## Configuración
Valores clave en `src/main/resources/application.yaml`:

- `server.port`: `8087`
- `spring.data.mongodb.uri`: `mongodb://mongodb:27017/reporting_bd`
- `analytics.url`: `http://ms-analytics:8086`
- `jwt.secret.key`: clave secreta para validar JWT
- `springdoc.swagger-ui.path`: `/swagger-ui.html`

---

## Dependencias externas
- `ms-analytics` — fuente de datos analíticos.
- `MongoDB` — persistencia de `ReportLog`.

---

## Ejecución local
### Prerrequisitos
- Java 25 instalado
- Maven instalado
- MongoDB accesible en `mongodb://mongodb:27017`
- `ms-analytics` disponible en `http://ms-analytics:8086`

### Ejecutar con Maven
```bash
mvn clean package
java -jar target/ms-reporting-0.0.1-SNAPSHOT.jar
```

### Modo desarrollo
```bash
mvn spring-boot:run
```

---

## Docker
El contenedor expone el puerto `8087`.

El `Dockerfile` usa una build multi-stage con:
- `maven:3.9-eclipse-temurin-25` para compilar
- `eclipse-temurin:25-jre` para ejecutar

---

## Kubernetes
`k8s/deployment.yaml` configura:
- `image: proyecto-fullstack3-ms-reporting:latest`
- `imagePullPolicy: Never`
- `containerPort: 8087`
- `ANALYTICS_URL: http://ms-analytics:8086`
- `JWT_SECRET_KEY` con la clave JWT
- `livenessProbe`: `/actuator/health`
- `readinessProbe`: `/actuator/health/readiness`

`k8s/service.yaml` expone el servicio en el puerto `8087` como `ClusterIP`.

---

## Pruebas
Ejecuta las pruebas unitarias con:

```bash
mvn test
```

---

## Observaciones
- Este microservicio actúa como agregador de reportes, delegando los cálculos a `ms-analytics`.
- Los reportes completos se guardan en MongoDB como `ReportLog` para auditoría y seguimiento.

---

## Arquitectura del Proyecto

### Estructura de Directorios
```
backend/ms-reporting/
├── Dockerfile
├── k8s/
│   ├── deployment.yaml
│   ├── kustomization.yaml
│   └── service.yaml
├── mvnw
├── mvnw.cmd
├── pom.xml
├── README.md
└── src/
    ├── main/
    │   ├── java/com/grupocordillera/ms_reporting/
    │   │   ├── common/config/
    │   │   ├── report/client/
    │   │   ├── report/controller/
    │   │   ├── report/dto/
    │   │   ├── report/document/
    │   │   ├── report/repository/
    │   │   ├── report/service/
    │   │   ├── security/
    │   │   └── MsReportingApplication.java
    │   └── resources/
    │       └── application.yaml
    └── test/
        └── java/com/grupocordillera/ms_reporting/
            └── report/
```

### Explicación de Carpetas
- `src/main/java`: código fuente Java del microservicio.
- `src/main/resources`: configuración del servicio.
- `src/test/java`: pruebas unitarias.
- `k8s`: manifiestos de Kubernetes para despliegues.
- `Dockerfile`: imagen Docker multi-stage para compilación y ejecución.
- `pom.xml`: configuración de Maven y dependencias.

