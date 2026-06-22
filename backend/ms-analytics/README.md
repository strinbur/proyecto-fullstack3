# ms-analytics

Microservicio de KPIs y análisis de negocio, desarrollado en **Python + FastAPI**.

# Diagrama C3 - Componentes de analytics

<img width="1324" alt="Diagrama C3 Analytics" src="../../docs/diagrams/Fullstack 3 diagrama c3 analytics.drawio.png" />

## Descripción
`ms-analytics` calcula indicadores de negocio y expone métricas para el ecosistema de microservicios. Recibe un JWT válido, agrega datos desde `ms-data-aggregation` y guarda reportes completos en MongoDB.

---

## Endpoints
Todos los endpoints requieren el header:

`Authorization: Bearer <JWT>`

| Método | Ruta | Descripción |
|--------|------|-------------|
| GET | `/analytics/report` | Reporte KPI completo. Calcula métricas y guarda el reporte en MongoDB. |
| GET | `/analytics/history` | Historial de reportes generados. Retorna los últimos reportes guardados. |
| GET | `/analytics/sales-summary` | Resumen de ventas (totales, completadas, pendientes, canceladas). |
| GET | `/analytics/top-products?top_n=5` | Top N productos más vendidos. |
| GET | `/analytics/critical-stock` | Productos con stock crítico. |
| GET | `/analytics/revenue-by-category` | Ingresos y unidades vendidas por categoría. |
| GET | `/analytics/top-customers?top_n=5` | Top N clientes por gasto total. |
| GET | `/health` | Health check del servicio. |

---

## Stack Tecnológico
- Python 3.12
- FastAPI
- Uvicorn
- MongoDB (motor y persistencia)
- httpx
- python-jose
- Pydantic

---

## Configuración
Utiliza `.env.example` como plantilla para la configuración local.

Variables principales:

- `DATA_AGGREGATION_URL` — URL de `ms-data-aggregation` (por ejemplo `http://localhost:8085`).
- `JWT_SECRET_KEY` — clave secreta para validar JWT.
- `JWT_ALGORITHM` — algoritmo JWT (`HS256`).
- `CRITICAL_STOCK_THRESHOLD` — umbral de stock para el KPI de stock crítico.
- `MONGODB_URL` — conexión a MongoDB.
- `MONGODB_DATABASE` — nombre de la base de datos.

---

## Levantar localmente
### Prerrequisitos
- Python 3.12
- pip
- MongoDB en ejecución
- `ms-data-aggregation` accesible
- Token JWT válido emitido por `ms-login`

### Pasos
```bash
cp .env.example .env
# editar .env con los valores reales
pip install -r requirements.txt
uvicorn app.main:app --reload --host 0.0.0.0 --port 8086
```

Swagger disponible en: `http://localhost:8086/swagger-ui`

---

## Docker
Construye la imagen con:

```bash
docker build -t ms-analytics .
```

Ejecuta el contenedor con:

```bash
docker run -p 8086:8086 --env-file .env ms-analytics
```

---

## Arquitectura del Proyecto

### Estructura de Directorios
```
backend/ms-analytics/
├── Dockerfile
├── .env.example
├── requirements.txt
├── README.md
└── app/
    ├── core/
    ├── repositories/
    ├── routers/
    │   └── analytics.py
    ├── schemas/
    ├── services/
    ├── main.py
    └── __init__.py
```

### Explicación de Carpetas
- `app/core`: seguridad y utilidades para el servicio.
- `app/repositories`: persistencia de reportes en MongoDB.
- `app/routers`: rutas FastAPI y definición de endpoints.
- `app/schemas`: modelos Pydantic para respuestas y validación.
- `app/services`: lógica de cálculo de KPIs y agregación de datos.
- `app/main.py`: punto de entrada de la aplicación.
- `Dockerfile`: imagen Docker para despliegue.
- `.env.example`: plantilla de variables de entorno.
- `requirements.txt`: dependencias Python.
