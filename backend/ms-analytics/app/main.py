from fastapi import FastAPI
from fastapi.middleware.cors import CORSMiddleware

from app.core.monitoring import init_sentry
from app.core.exception_handlers import register_exception_handlers
from app.middlewares.request_id import RequestIdMiddleware
from app.routers.analytics import router as analytics_router

init_sentry()

app = FastAPI(
    title="ms-analytics",
    description=(
        "Microservicio de análisis y KPIs. Consume datos desde ms-data-aggregation "
        "y expone métricas de negocio para ms-reporting."
    ),
    version="1.0.0",
    docs_url="/swagger-ui",
    redoc_url="/redoc",
)

app.add_middleware(RequestIdMiddleware)

app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

register_exception_handlers(app)

app.include_router(analytics_router)


@app.get("/health", tags=["health"])
def health():
    return {"status": "up", "service": "ms-analytics"}