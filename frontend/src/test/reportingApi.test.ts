import { describe, it, expect, vi, beforeEach, type Mock } from "vitest";
import { api } from "../api/api";
import {
  getSalesSummary,
  getTopProducts,
  getCriticalStock,
  getHistory,
} from "../features/reporting/reportingApi";

vi.mock("../api/api", () => ({
  api: {
    get: vi.fn(),
    post: vi.fn(),
    put: vi.fn(),
    delete: vi.fn(),
  },
}));

describe("reportingApi", () => {
  beforeEach(() => {
    vi.clearAllMocks();
    localStorage.clear();
  });

  it("getSalesSummary envia GET a /reports/sales-summary", async () => {
    const mockResponse = { data: { totalOrders: 10 } };
    (api.get as Mock).mockResolvedValue(mockResponse);

    const result = await getSalesSummary();

    expect(api.get).toHaveBeenCalledWith("/reports/sales-summary");
    expect(result).toEqual(mockResponse.data);
  });

  it("getTopProducts envia GET a /reports/top-products con topN por defecto (5)", async () => {
    const mockResponse = { data: { topN: 5, products: [] } };
    (api.get as Mock).mockResolvedValue(mockResponse);

    const result = await getTopProducts();

    expect(api.get).toHaveBeenCalledWith("/reports/top-products", {
      params: { topN: 5 },
    });
    expect(result).toEqual(mockResponse.data);
  });

  it("getTopProducts envia el topN indicado por parametro", async () => {
    const mockResponse = { data: { topN: 10, products: [] } };
    (api.get as Mock).mockResolvedValue(mockResponse);

    await getTopProducts(10);

    expect(api.get).toHaveBeenCalledWith("/reports/top-products", {
      params: { topN: 10 },
    });
  });

  it("getCriticalStock envia GET a /reports/critical-stock", async () => {
    const mockResponse = { data: { totalCriticalItems: 2, items: [] } };
    (api.get as Mock).mockResolvedValue(mockResponse);

    const result = await getCriticalStock();

    expect(api.get).toHaveBeenCalledWith("/reports/critical-stock");
    expect(result).toEqual(mockResponse.data);
  });

  it("getHistory envia GET a /reports/history con el header Authorization usando el token de localStorage", async () => {
    localStorage.setItem("token", "fake-jwt-token");
    const mockResponse = { data: [{ id: "1" }] };
    (api.get as Mock).mockResolvedValue(mockResponse);

    const result = await getHistory();

    expect(api.get).toHaveBeenCalledWith("/reports/history", {
      headers: { Authorization: "Bearer fake-jwt-token" },
    });
    expect(result).toEqual(mockResponse.data);
  });

  it("getHistory funciona aunque no haya token (Authorization: Bearer null)", async () => {
    const mockResponse = { data: [] };
    (api.get as Mock).mockResolvedValue(mockResponse);

    await getHistory();

    expect(api.get).toHaveBeenCalledWith("/reports/history", {
      headers: { Authorization: "Bearer null" },
    });
  });
});