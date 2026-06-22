# ms-analytics

Microservicio de KPIs y análisis de negocio, desarrollado en **Python + FastAPI**.

# Diagrama C3 - Componentes de analytics

<img width="1324" alt="Diagrama C3 Login" src="../../docs/diagrams//Fullstack%203%20diagrama%20c3%20analytics.drawio.png" />


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
