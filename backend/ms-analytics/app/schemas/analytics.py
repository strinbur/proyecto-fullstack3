from datetime import datetime
from typing import List, Optional

from pydantic import BaseModel


# ── Dataset schemas (espejo de ms-data-aggregation) ──────────────────────────

class OrderItem(BaseModel):
    productCode: str
    name: str
    price: float
    quantity: int
    category: str
    subtotal: float


class Order(BaseModel):
    id: str
    userEmail: str
    userName: str
    items: List[OrderItem]
    total: float
    status: str
    createdAt: datetime


class InventoryItem(BaseModel):
    code: str
    name: str
    quantity: int
    price: float
    category: str


class CombinedDataset(BaseModel):
    orders: List[Order]
    inventory: List[InventoryItem]


# ── KPI response schemas ──────────────────────────────────────────────────────

class TopProductItem(BaseModel):
    productCode: str
    name: str
    category: str
    totalQuantitySold: int
    totalRevenue: float


class TopProductsKPI(BaseModel):
    topN: int
    products: List[TopProductItem]


class CriticalStockItem(BaseModel):
    code: str
    name: str
    category: str
    currentQuantity: int
    price: float


class CriticalStockKPI(BaseModel):
    threshold: int
    totalCriticalItems: int
    items: List[CriticalStockItem]


class SalesSummaryKPI(BaseModel):
    totalOrders: int
    completedOrders: int
    pendingOrders: int
    cancelledOrders: int
    totalRevenue: float
    averageOrderValue: float


class RevenueByCategory(BaseModel):
    category: str
    totalRevenue: float
    totalUnitsSold: int
    percentageOfRevenue: float


class RevenueByCategoryKPI(BaseModel):
    categories: List[RevenueByCategory]


class TopCustomer(BaseModel):
    userEmail: str
    userName: str
    totalOrders: int
    totalSpent: float


class TopCustomersKPI(BaseModel):
    topN: int
    customers: List[TopCustomer]


class FullAnalyticsReport(BaseModel):
    generatedAt: datetime
    salesSummary: SalesSummaryKPI
    topProducts: TopProductsKPI
    criticalStock: CriticalStockKPI
    revenueByCategory: RevenueByCategoryKPI
    topCustomers: TopCustomersKPI
