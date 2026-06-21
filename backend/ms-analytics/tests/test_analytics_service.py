import pytest
from unittest.mock import patch
from datetime import datetime

from app.services.analytics_service import (
    calc_sales_summary,
    calc_top_products,
    calc_critical_stock,
    calc_revenue_by_category,
    calc_top_customers,
    build_full_report,
)


# -----------------------
# FIXTURES SIMPLES
# -----------------------

class FakeItem:
    def __init__(self, productCode, name, category, quantity, subtotal):
        self.productCode = productCode
        self.name = name
        self.category = category
        self.quantity = quantity
        self.subtotal = subtotal


class FakeOrder:
    def __init__(self, status, total, items, userEmail="a@a.com", userName="User"):
        self.status = status
        self.total = total
        self.items = items
        self.userEmail = userEmail
        self.userName = userName


class FakeInventoryItem:
    def __init__(self, code, name, category, quantity, price):
        self.code = code
        self.name = name
        self.category = category
        self.quantity = quantity
        self.price = price


class FakeDataset:
    def __init__(self, orders=None, inventory=None):
        self.orders = orders or []
        self.inventory = inventory or []


# -----------------------
# TESTS
# -----------------------

def test_sales_summary():
    dataset = FakeDataset([
        FakeOrder("completed", 100, []),
        FakeOrder("pending", 50, []),
        FakeOrder("completed", 50, []),
    ])

    result = calc_sales_summary(dataset)

    assert result.totalOrders == 3
    assert result.completedOrders == 2
    assert result.pendingOrders == 1
    assert result.cancelledOrders == 0
    assert result.totalRevenue == 200
    assert result.averageOrderValue == 66.67


def test_top_products():
    item1 = FakeItem("P1", "Prod1", "Cat1", 2, 100)
    item2 = FakeItem("P2", "Prod2", "Cat2", 1, 50)

    dataset = FakeDataset([
        FakeOrder("completed", 150, [item1, item2]),
    ])

    result = calc_top_products(dataset, top_n=2)

    assert result.topN == 2
    assert len(result.products) == 2
    assert result.products[0].productCode in ["P1", "P2"]


@patch("app.services.analytics_service.settings.critical_stock_threshold", 10)
def test_critical_stock():
    dataset = FakeDataset(
        inventory=[
            FakeInventoryItem("A", "ItemA", "Cat", 5, 10),
            FakeInventoryItem("B", "ItemB", "Cat", 20, 10),
        ]
    )

    result = calc_critical_stock(dataset)

    assert result.threshold == 10
    assert result.totalCriticalItems == 1
    assert result.items[0].code == "A"


def test_revenue_by_category():
    item1 = FakeItem("P1", "Prod1", "Electronics", 2, 100)
    item2 = FakeItem("P2", "Prod2", "Books", 1, 50)

    dataset = FakeDataset([
        FakeOrder("completed", 150, [item1, item2]),
    ])

    result = calc_revenue_by_category(dataset)

    assert len(result.categories) == 2
    assert result.categories[0].category in ["Electronics", "Books"]


def test_top_customers():
    dataset = FakeDataset([
        FakeOrder("completed", 100, [], "a@test.com", "A"),
        FakeOrder("completed", 200, [], "a@test.com", "A"),
        FakeOrder("completed", 50, [], "b@test.com", "B"),
    ])

    result = calc_top_customers(dataset, top_n=2)

    assert result.topN == 2
    assert result.customers[0].userEmail == "a@test.com"
    assert result.customers[0].totalSpent == 300


def test_build_full_report():
    dataset = FakeDataset()

    result = build_full_report(dataset)

    assert result.generatedAt is not None
    assert result.salesSummary is not None
    assert result.topProducts is not None
    assert result.criticalStock is not None
    assert result.revenueByCategory is not None
    assert result.topCustomers is not None