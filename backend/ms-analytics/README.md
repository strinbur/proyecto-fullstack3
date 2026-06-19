# ms-analytics

Microservicio de KPIs y análisis de negocio, desarrollado en **Python + FastAPI**.

## Rol en la arquitectura

```
ms-login  →  JWT
ms-order  \
             →  ms-data-aggregation  →  ms-analytics  →  ms-reporting  →  BFF  →  Frontend
ms-inventory /
```

ms-analytics recibe el dataset consolidado desde `ms-data-aggregation` y calcula métricas de negocio. No tiene base de datos propia; todo el cálculo es en memoria sobre los datos recibidos.

## Endpoints

| Método | Ruta | Descripción |
|--------|------|-------------|
| GET | `/analytics/report` | Reporte KPI completo (recomendado para ms-reporting) |
| GET | `/analytics/sales-summary` | Resumen de ventas (total, completadas, pendientes, canceladas) |
| GET | `/analytics/top-products?top_n=5` | Top N productos más vendidos por cantidad |
| GET | `/analytics/critical-stock` | Productos con stock menor al umbral configurado |
| GET | `/analytics/revenue-by-category` | Ingresos y unidades vendidas por categoría |
| GET | `/analytics/top-customers?top_n=5` | Top N clientes por gasto total |
| GET | `/health` | Health check |

Todos los endpoints requieren `Authorization: Bearer <JWT>` emitido por ms-login.

## Variables de entorno

| Variable | Default | Descripción |
|----------|---------|-------------|
| `DATA_AGGREGATION_URL` | `http://localhost:8085` | URL base de ms-data-aggregation |
| `JWT_SECRET_KEY` | — | Clave secreta compartida con ms-login |
| `JWT_ALGORITHM` | `HS256` | Algoritmo JWT |
| `CRITICAL_STOCK_THRESHOLD` | `5` | Stock mínimo antes de considerarse crítico |

## Levantar localmente

```bash
cp .env.example .env
# editar .env con los valores reales

pip install -r requirements.txt
uvicorn app.main:app --reload --port 8086
```

Swagger disponible en: http://localhost:8086/swagger-ui

## Docker

```bash
docker build -t ms-analytics .
docker run -p 8085:8085 --env-file .env ms-analytics
```
