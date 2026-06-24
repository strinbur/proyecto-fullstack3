from datetime import datetime, timezone
from collections import defaultdict

import numpy as np
import pandas as pd

from app.core.config import settings
from app.schemas.analytics import (
    CombinedDataset,
    CriticalStockItem,
    CriticalStockKPI,
    FullAnalyticsReport,
    RevenueByCategory,
    RevenueByCategoryKPI,
    SalesSummaryKPI,
    TopCustomer,
    TopCustomersKPI,
    TopProductItem,
    TopProductsKPI,
)


def _orders_to_df(dataset: CombinedDataset) -> pd.DataFrame:
    """
    Aplana orders + items en un DataFrame fila por line-item.
    Columnas: order_id, userEmail, userName, status, order_total,
              productCode, name, category, quantity, subtotal
    """
    rows = []
    for order in dataset.orders:
        for item in order.items:
            rows.append({
                "order_id":    order.id,
                "userEmail":   order.userEmail,
                "userName":    order.userName,
                "status":      order.status.lower(),
                "order_total": order.total,
                "productCode": item.productCode,
                "name":        item.name,
                "category":    item.category,
                "quantity":    item.quantity,
                "subtotal":    item.subtotal,
            })
    return pd.DataFrame(rows)


def _inventory_to_df(dataset: CombinedDataset) -> pd.DataFrame:
    return pd.DataFrame([item.model_dump() for item in dataset.inventory])


# ── KPIs ─────────────────────────────────────────────────────────────────────

def calc_sales_summary(dataset: CombinedDataset) -> SalesSummaryKPI:
    orders = dataset.orders
    if not orders:
        return SalesSummaryKPI(
            totalOrders=0,
            completedOrders=0,
            pendingOrders=0,
            cancelledOrders=0,
            totalRevenue=0.0,
            averageOrderValue=0.0,
        )

    df = pd.DataFrame([
        {"status": o.status.lower(), "total": o.total}
        for o in orders
    ])

    totals    = np.array(df["total"].values, dtype=np.float64)
    total_rev = float(np.sum(totals))
    avg_order = float(np.mean(totals))

    status_counts = df["status"].value_counts()

    return SalesSummaryKPI(
        totalOrders=len(df),
        completedOrders=int(status_counts.get("completado", 0)),
        pendingOrders=int(status_counts.get("pendiente", 0)),
        cancelledOrders=int(status_counts.get("cancelado", 0)),
        totalRevenue=round(total_rev, 2),
        averageOrderValue=round(avg_order, 2),
    )


def calc_top_products(dataset: CombinedDataset, top_n: int = 5) -> TopProductsKPI:
    df = _orders_to_df(dataset)
    if df.empty:
        return TopProductsKPI(topN=top_n, products=[])

    # Agrupar por producto con pandas
    grouped = (
        df.groupby("productCode")
        .agg(
            name=("name", "first"),
            category=("category", "first"),
            totalQuantitySold=("quantity", "sum"),
            totalRevenue=("subtotal", "sum"),
        )
        .reset_index()
    )

    # Ordenar por cantidad vendida con numpy argsort
    qty_arr = grouped["totalQuantitySold"].values.astype(np.int64)
    top_idx = np.argsort(qty_arr)[::-1][:top_n]
    top_df  = grouped.iloc[top_idx]

    products = [
        TopProductItem(
            productCode=row.productCode,
            name=row.name,
            category=row.category,
            totalQuantitySold=int(row.totalQuantitySold),
            totalRevenue=round(float(row.totalRevenue), 2),
        )
        for row in top_df.itertuples()
    ]

    return TopProductsKPI(topN=top_n, products=products)


def calc_critical_stock(dataset: CombinedDataset) -> CriticalStockKPI:
    df = _inventory_to_df(dataset)
    if df.empty:
        return CriticalStockKPI(threshold=settings.critical_stock_threshold, totalCriticalItems=0, items=[])

    threshold = settings.critical_stock_threshold

    # Filtro vectorizado con numpy
    qty_arr  = df["quantity"].values.astype(np.int64)
    mask     = qty_arr < threshold
    critical_df = df[mask].copy()

    # Ordenar por quantity ascendente
    critical_df = critical_df.sort_values("quantity")

    items = [
        CriticalStockItem(
            code=row.code,
            name=row.name,
            category=row.category,
            currentQuantity=int(row.quantity),
            price=float(row.price),
        )
        for row in critical_df.itertuples()
    ]

    return CriticalStockKPI(
        threshold=threshold,
        totalCriticalItems=len(items),
        items=items,
    )


def calc_revenue_by_category(dataset: CombinedDataset) -> RevenueByCategoryKPI:
    df = _orders_to_df(dataset)
    if df.empty:
        return RevenueByCategoryKPI(categories=[])

    grouped = (
        df.groupby("category")
        .agg(
            totalRevenue=("subtotal", "sum"),
            totalUnitsSold=("quantity", "sum"),
        )
        .reset_index()
    )

    # Porcentaje con numpy
    rev_arr   = grouped["totalRevenue"].values.astype(np.float64)
    total_rev = np.sum(rev_arr)
    pct_arr   = np.where(total_rev > 0, rev_arr / total_rev * 100, 0.0)

    # Ordenar descendente por revenue
    order_idx = np.argsort(rev_arr)[::-1]

    categories = [
        RevenueByCategory(
            category=grouped.iloc[i]["category"],
            totalRevenue=round(float(rev_arr[i]), 2),
            totalUnitsSold=int(grouped.iloc[i]["totalUnitsSold"]),
            percentageOfRevenue=round(float(pct_arr[i]), 2),
        )
        for i in order_idx
    ]

    return RevenueByCategoryKPI(categories=categories)


def calc_top_customers(dataset: CombinedDataset, top_n: int = 5) -> TopCustomersKPI:
    df = _orders_to_df(dataset)
    if df.empty:
        return TopCustomersKPI(topN=top_n, customers=[])

    # Una fila por orden para no duplicar el total por cada line-item
    orders_df = df[["order_id", "userEmail", "userName", "order_total"]].drop_duplicates("order_id")

    grouped = (
        orders_df.groupby("userEmail")
        .agg(
            userName=("userName", "first"),
            totalOrders=("order_id", "count"),
            totalSpent=("order_total", "sum"),
        )
        .reset_index()
    )

    # Top N por gasto con numpy
    spent_arr = grouped["totalSpent"].values.astype(np.float64)
    top_idx   = np.argsort(spent_arr)[::-1][:top_n]
    top_df    = grouped.iloc[top_idx]

    customers = [
        TopCustomer(
            userEmail=row.userEmail,
            userName=row.userName,
            totalOrders=int(row.totalOrders),
            totalSpent=round(float(row.totalSpent), 2),
        )
        for row in top_df.itertuples()
    ]

    return TopCustomersKPI(topN=top_n, customers=customers)


def build_full_report(dataset: CombinedDataset) -> FullAnalyticsReport:
    return FullAnalyticsReport(
        generatedAt=datetime.now(timezone.utc),
        salesSummary=calc_sales_summary(dataset),
        topProducts=calc_top_products(dataset),
        criticalStock=calc_critical_stock(dataset),
        revenueByCategory=calc_revenue_by_category(dataset),
        topCustomers=calc_top_customers(dataset),
    )