# Microservicio de Agregación de Datos (ms-data-aggregation)

# Diagrama C3 - Componentes de data-aggregation

<img width="1324" alt="Diagrama C3 Login" src="../../docs/diagrams//Fullstack%203%20diagrama%20c3%20data%20aggregation.drawio.png" />

## Descripción
Este microservicio agrega datos de pedidos e inventario para devolver un conjunto único de datos (`dataset`) que puede consumir el frontend o servicios analíticos. Utiliza Feign para comunicarse con `ms-order` y `ms-inventory`, y está protegido con JWT.

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
- `DatasetController` — expone el endpoint `/dataset`
- `DatasetService` — agrega datos desde los clientes externos
- `OrderClient` — cliente Feign hacia `ms-order`
- `InventoryClient` — cliente Feign hacia `ms-inventory`
- `SecurityConfig` y `JwtAuthenticationFilter` — protegen las rutas con JWT

---

## Endpoint principal
Todos los endpoints están protegidos por JWT. Enviar el header:

`Authorization: Bearer <token>`

- `GET /dataset` — Devuelve el dataset combinado de pedidos e inventario.

---

## Configuración
Valores clave en `src/main/resources/application.yaml`:

- `server.port`: `8085`
- `spring.data.mongodb.uri`: `mongodb://mongodb:27017/dataaggregation`
- `order.url`: `http://ms-order:8084`
- `inventory.url`: `http://ms-inventory:8082`
- `jwt.secret.key`: clave secreta para validar tokens
- `jwt.expiration`: tiempo de expiración del token en milisegundos
- `feign.client.config.default.connectTimeout`: `5000`
- `feign.client.config.default.readTimeout`: `5000`

---

## Dependencias externas
- `ms-order` — fuente de datos de pedidos.
- `ms-inventory` — fuente de datos de inventario.
- `MongoDB` — almacenamiento de estado y/o datos agregados.

---

## Ejecución local
### Prerrequisitos
- Java 25 instalado
- Maven instalado
- MongoDB accesible en `mongodb://mongodb:27017`
- `ms-order` disponible en `http://ms-order:8084`
- `ms-inventory` disponible en `http://ms-inventory:8082`

### Ejecutar con Maven
```bash
mvn clean package
java -jar target/ms-data-aggregation-0.0.1-SNAPSHOT.jar
```

### Modo desarrollo
```bash
mvn spring-boot:run
```

---

## Docker
El contenedor expone el puerto `8085`.

El `Dockerfile` usa una build multi-stage con:
- `maven:3.9.11-eclipse-temurin-25` para compilar
- `eclipse-temurin:25-jdk` para ejecutar

---

## Kubernetes
`k8s/deployment.yaml` configura:
- `image: proyecto-fullstack3-ms-data-aggregation:latest`
- `imagePullPolicy: Never`
- `containerPort: 8085`
- `ORDER_SERVICE_URL`: `http://ms-order:8084`
- `INVENTORY_SERVICE_URL`: `http://ms-inventory:8082`
- `livenessProbe`: `/actuator/health`
- `readinessProbe`: `/actuator/health/readiness`

`k8s/service.yaml` expone el servicio en el puerto `8085` como `ClusterIP`.

---

## Pruebas
Ejecuta las pruebas con:

```bash
mvn test
```

---

## Observaciones
- El microservicio está diseñado para ser un agregador ligero y no realiza cálculos complejos en el frontend.
- Requiere JWT válido para todas las solicitudes.
- Es recomendado usarlo junto con `docker-compose` desde la raíz del repositorio para resolver las dependencias de `ms-order`, `ms-inventory` y `mongodb`.

---

## Arquitectura del Proyecto

### Estructura de Directorios
```
backend/ms-data-aggregation/
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
    │   ├── java/com/grupocordillera/ms_data_aggregation/
    │   │   ├── common/config/
    │   │   ├── dataset/client/
    │   │   ├── dataset/controller/
    │   │   ├── dataset/dto/
    │   │   ├── dataset/service/
    │   │   ├── security/
    │   │   └── MsDataAggregationApplication.java
    │   └── resources/
    │       └── application.yaml
    └── test/
        └── java/com/grupocordillera/ms_data_aggregation/
            ├── security/
            └── ...
```

### Explicación de Carpetas
- `src/main/java`: código fuente Java del microservicio.
- `src/main/resources`: configuración del servicio.
- `src/test/java`: pruebas unitarias.
- `k8s`: manifiestos de Kubernetes para despliegue.
- `Dockerfile`: imagen Docker multi-stage para compilación y ejecución.
- `pom.xml`: dependencias y build de Maven.
