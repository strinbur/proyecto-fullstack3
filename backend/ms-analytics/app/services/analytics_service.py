from collections import defaultdict
from datetime import datetime, timezone

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


def calc_sales_summary(dataset: CombinedDataset) -> SalesSummaryKPI:
    orders = dataset.orders
    total = len(orders)

    status_map = defaultdict(int)
    total_revenue = 0.0

    for order in orders:
        status_map[order.status.lower()] += 1
        total_revenue += order.total

    avg = total_revenue / total if total > 0 else 0.0

    return SalesSummaryKPI(
        totalOrders=total,
        completedOrders=status_map.get("completed", 0),
        pendingOrders=status_map.get("pending", 0),
        cancelledOrders=status_map.get("cancelled", 0),
        totalRevenue=round(total_revenue, 2),
        averageOrderValue=round(avg, 2),
    )


def calc_top_products(dataset: CombinedDataset, top_n: int = 5) -> TopProductsKPI:
    # Acumular ventas por código de producto
    product_qty: dict[str, int] = defaultdict(int)
    product_revenue: dict[str, float] = defaultdict(float)
    product_meta: dict[str, tuple[str, str]] = {}  # code → (name, category)

    for order in dataset.orders:
        for item in order.items:
            product_qty[item.productCode] += item.quantity
            product_revenue[item.productCode] += item.subtotal
            product_meta[item.productCode] = (item.name, item.category)

    sorted_products = sorted(product_qty.keys(), key=lambda c: product_qty[c], reverse=True)

    result = []
    for code in sorted_products[:top_n]:
        name, category = product_meta[code]
        result.append(
            TopProductItem(
                productCode=code,
                name=name,
                category=category,
                totalQuantitySold=product_qty[code],
                totalRevenue=round(product_revenue[code], 2),
            )
        )

    return TopProductsKPI(topN=top_n, products=result)


def calc_critical_stock(dataset: CombinedDataset) -> CriticalStockKPI:
    threshold = settings.critical_stock_threshold
    critical = [
        CriticalStockItem(
            code=item.code,
            name=item.name,
            category=item.category,
            currentQuantity=item.quantity,
            price=item.price,
        )
        for item in dataset.inventory
        if item.quantity < threshold
    ]

    critical.sort(key=lambda x: x.currentQuantity)

    return CriticalStockKPI(
        threshold=threshold,
        totalCriticalItems=len(critical),
        items=critical,
    )


def calc_revenue_by_category(dataset: CombinedDataset) -> RevenueByCategoryKPI:
    category_revenue: dict[str, float] = defaultdict(float)
    category_units: dict[str, int] = defaultdict(int)

    for order in dataset.orders:
        for item in order.items:
            category_revenue[item.category] += item.subtotal
            category_units[item.category] += item.quantity

    total_revenue = sum(category_revenue.values())

    categories = []
    for cat, revenue in sorted(category_revenue.items(), key=lambda x: x[1], reverse=True):
        pct = (revenue / total_revenue * 100) if total_revenue > 0 else 0.0
        categories.append(
            RevenueByCategory(
                category=cat,
                totalRevenue=round(revenue, 2),
                totalUnitsSold=category_units[cat],
                percentageOfRevenue=round(pct, 2),
            )
        )

    return RevenueByCategoryKPI(categories=categories)


def calc_top_customers(dataset: CombinedDataset, top_n: int = 5) -> TopCustomersKPI:
    customer_orders: dict[str, int] = defaultdict(int)
    customer_spent: dict[str, float] = defaultdict(float)
    customer_name: dict[str, str] = {}

    for order in dataset.orders:
        email = order.userEmail
        customer_orders[email] += 1
        customer_spent[email] += order.total
        customer_name[email] = order.userName

    sorted_customers = sorted(customer_spent.keys(), key=lambda e: customer_spent[e], reverse=True)

    result = [
        TopCustomer(
            userEmail=email,
            userName=customer_name[email],
            totalOrders=customer_orders[email],
            totalSpent=round(customer_spent[email], 2),
        )
        for email in sorted_customers[:top_n]
    ]

    return TopCustomersKPI(topN=top_n, customers=result)


def build_full_report(dataset: CombinedDataset) -> FullAnalyticsReport:
    return FullAnalyticsReport(
        generatedAt=datetime.now(timezone.utc),
        salesSummary=calc_sales_summary(dataset),
        topProducts=calc_top_products(dataset),
        criticalStock=calc_critical_stock(dataset),
        revenueByCategory=calc_revenue_by_category(dataset),
        topCustomers=calc_top_customers(dataset),
    )
