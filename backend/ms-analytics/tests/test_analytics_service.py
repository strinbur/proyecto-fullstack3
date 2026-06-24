import pytest
from unittest.mock import patch

from app.services.analytics_service import (
    calc_sales_summary,
    calc_top_products,
    calc_critical_stock,
    calc_revenue_by_category,
    calc_top_customers,
    build_full_report,
)


# -----------------------
# FIXTURES
# -----------------------

class FakeItem:
    def __init__(self, productCode, name, category, quantity, subtotal):
        self.productCode = productCode
        self.name = name
        self.category = category
        self.quantity = quantity
        self.subtotal = subtotal


class FakeOrder:
    def __init__(self, id, status, total, items, userEmail="a@a.com", userName="User"):
        self.id = id
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

    def model_dump(self):
        return {
            "code": self.code,
            "name": self.name,
            "category": self.category,
            "quantity": self.quantity,
            "price": self.price,
        }


class FakeDataset:
    def __init__(self, orders=None, inventory=None):
        self.orders = orders or []
        self.inventory = inventory or []


# -----------------------
# TESTS
# -----------------------

def test_sales_summary():
    dataset = FakeDataset([
        FakeOrder("o1", "COMPLETADO", 100, []),
        FakeOrder("o2", "PENDIENTE", 50, []),
        FakeOrder("o3", "COMPLETADO", 50, []),
    ])

    result = calc_sales_summary(dataset)

    assert result.totalOrders == 3
    assert result.completedOrders == 2
    assert result.pendingOrders == 1
    assert result.cancelledOrders == 0
    assert result.totalRevenue == 200.0
    assert result.averageOrderValue == 66.67


def test_sales_summary_empty():
    result = calc_sales_summary(FakeDataset())

    assert result.totalOrders == 0
    assert result.totalRevenue == 0.0
    assert result.averageOrderValue == 0.0


def test_top_products():
    item1 = FakeItem("P1", "Prod1", "Cat1", 2, 100.0)
    item2 = FakeItem("P2", "Prod2", "Cat2", 1, 50.0)

    dataset = FakeDataset([
        FakeOrder("o1", "completed", 150.0, [item1, item2]),
    ])

    result = calc_top_products(dataset, top_n=2)

    assert result.topN == 2
    assert len(result.products) == 2
    # P1 tiene más cantidad vendida, debe ser primero
    assert result.products[0].productCode == "P1"
    assert result.products[0].totalQuantitySold == 2
    assert result.products[1].productCode == "P2"


def test_top_products_empty():
    result = calc_top_products(FakeDataset(), top_n=3)

    assert result.topN == 3
    assert result.products == []


def test_top_products_respects_top_n():
    items = [FakeItem(f"P{i}", f"Prod{i}", "Cat", i, float(i * 10)) for i in range(1, 6)]
    dataset = FakeDataset([FakeOrder("o1", "completed", 100.0, items)])

    result = calc_top_products(dataset, top_n=3)

    assert len(result.products) <= 3


@patch("app.services.analytics_service.settings")
def test_critical_stock(mock_settings):
    mock_settings.critical_stock_threshold = 10

    dataset = FakeDataset(
        inventory=[
            FakeInventoryItem("A", "ItemA", "Cat", 5, 10.0),
            FakeInventoryItem("B", "ItemB", "Cat", 20, 10.0),
        ]
    )

    result = calc_critical_stock(dataset)

    assert result.threshold == 10
    assert result.totalCriticalItems == 1
    assert result.items[0].code == "A"
    assert result.items[0].currentQuantity == 5


@patch("app.services.analytics_service.settings")
def test_critical_stock_empty_inventory(mock_settings):
    mock_settings.critical_stock_threshold = 10

    result = calc_critical_stock(FakeDataset())

    assert result.totalCriticalItems == 0
    assert result.items == []


@patch("app.services.analytics_service.settings")
def test_critical_stock_sorted_ascending(mock_settings):
    mock_settings.critical_stock_threshold = 10

    dataset = FakeDataset(
        inventory=[
            FakeInventoryItem("A", "ItemA", "Cat", 7, 10.0),
            FakeInventoryItem("B", "ItemB", "Cat", 2, 10.0),
            FakeInventoryItem("C", "ItemC", "Cat", 5, 10.0),
        ]
    )

    result = calc_critical_stock(dataset)

    quantities = [i.currentQuantity for i in result.items]
    assert quantities == sorted(quantities)


def test_revenue_by_category():
    item1 = FakeItem("P1", "Prod1", "Electronics", 2, 100.0)
    item2 = FakeItem("P2", "Prod2", "Books", 1, 50.0)

    dataset = FakeDataset([
        FakeOrder("o1", "completed", 150.0, [item1, item2]),
    ])

    result = calc_revenue_by_category(dataset)

    assert len(result.categories) == 2
    # Electronics tiene más revenue, debe ir primero
    assert result.categories[0].category == "Electronics"
    assert result.categories[0].totalRevenue == 100.0
    assert result.categories[0].percentageOfRevenue == pytest.approx(66.67, abs=0.01)
    assert result.categories[1].category == "Books"


def test_revenue_by_category_empty():
    result = calc_revenue_by_category(FakeDataset())

    assert result.categories == []


def test_top_customers():
    dataset = FakeDataset([
        FakeOrder("o1", "completed", 100.0, [], "a@test.com", "UserA"),
        FakeOrder("o2", "completed", 200.0, [], "a@test.com", "UserA"),
        FakeOrder("o3", "completed", 50.0,  [], "b@test.com", "UserB"),
    ])

    result = calc_top_customers(dataset, top_n=2)

    assert result.topN == 2
    assert len(result.customers) == 2
    assert result.customers[0].userEmail == "a@test.com"
    assert result.customers[0].totalOrders == 2
    assert result.customers[0].totalSpent == 300.0
    assert result.customers[1].userEmail == "b@test.com"


def test_top_customers_empty():
    result = calc_top_customers(FakeDataset(), top_n=5)

    assert result.topN == 5
    assert result.customers == []


def test_top_customers_respects_top_n():
    orders = [
        FakeOrder(f"o{i}", "completed", float(i * 10), [], f"user{i}@test.com", f"User{i}")
        for i in range(1, 8)
    ]
    dataset = FakeDataset(orders)

    result = calc_top_customers(dataset, top_n=3)

    assert len(result.customers) <= 3


def test_build_full_report():
    result = build_full_report(FakeDataset())

    assert result.generatedAt is not None
    assert result.salesSummary is not None
    assert result.topProducts is not None
    assert result.criticalStock is not None
    assert result.revenueByCategory is not None
    assert result.topCustomers is not None