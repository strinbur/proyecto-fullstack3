from fastapi import APIRouter, Depends, Query
from fastapi.security import HTTPAuthorizationCredentials, HTTPBearer

from app.core.security import verify_token
from app.repositories import report_repository
from app.schemas.analytics import (
    CriticalStockKPI,
    FullAnalyticsReport,
    RevenueByCategoryKPI,
    SalesSummaryKPI,
    TopCustomersKPI,
    TopProductsKPI,
)
from app.services import analytics_service
from app.services.data_client import fetch_dataset

router = APIRouter(prefix="/analytics", tags=["analytics"])
bearer_scheme = HTTPBearer()


def _get_token(credentials: HTTPAuthorizationCredentials = Depends(bearer_scheme)) -> str:
    return credentials.credentials


# ── Reporte completo (guarda en MongoDB) ─────────────────────────────────────

@router.get(
    "/report",
    response_model=FullAnalyticsReport,
    summary="Reporte KPI completo",
    description="Calcula todos los KPIs, guarda el reporte en MongoDB y lo retorna.",
)
async def full_report(
    token: str = Depends(_get_token),
    _: dict = Depends(verify_token),
):
    dataset = await fetch_dataset(token)
    report = analytics_service.build_full_report(dataset)
    await report_repository.save_report(report)
    return report


# ── Historial de reportes ─────────────────────────────────────────────────────

@router.get(
    "/history",
    summary="Historial de reportes generados",
    description="Retorna los últimos N reportes guardados en MongoDB, del más reciente al más antiguo.",
)
async def report_history(
    limit: int = Query(default=10, ge=1, le=100, description="Cantidad de reportes a retornar"),
    _: dict = Depends(verify_token),
):
    return await report_repository.get_all_reports(limit=limit)


# ── KPIs individuales (solo calculan, no guardan) ────────────────────────────

@router.get(
    "/sales-summary",
    response_model=SalesSummaryKPI,
    summary="Resumen de ventas",
)
async def sales_summary(
    token: str = Depends(_get_token),
    _: dict = Depends(verify_token),
):
    dataset = await fetch_dataset(token)
    return analytics_service.calc_sales_summary(dataset)


@router.get(
    "/top-products",
    response_model=TopProductsKPI,
    summary="Top productos más vendidos",
)
async def top_products(
    top_n: int = Query(default=5, ge=1, le=20),
    token: str = Depends(_get_token),
    _: dict = Depends(verify_token),
):
    dataset = await fetch_dataset(token)
    return analytics_service.calc_top_products(dataset, top_n=top_n)


@router.get(
    "/critical-stock",
    response_model=CriticalStockKPI,
    summary="Productos con stock crítico",
)
async def critical_stock(
    token: str = Depends(_get_token),
    _: dict = Depends(verify_token),
):
    dataset = await fetch_dataset(token)
    return analytics_service.calc_critical_stock(dataset)


@router.get(
    "/revenue-by-category",
    response_model=RevenueByCategoryKPI,
    summary="Ingresos por categoría",
)
async def revenue_by_category(
    token: str = Depends(_get_token),
    _: dict = Depends(verify_token),
):
    dataset = await fetch_dataset(token)
    return analytics_service.calc_revenue_by_category(dataset)


@router.get(
    "/top-customers",
    response_model=TopCustomersKPI,
    summary="Top clientes por gasto",
)
async def top_customers(
    top_n: int = Query(default=5, ge=1, le=20),
    token: str = Depends(_get_token),
    _: dict = Depends(verify_token),
):
    dataset = await fetch_dataset(token)
    return analytics_service.calc_top_customers(dataset, top_n=top_n)