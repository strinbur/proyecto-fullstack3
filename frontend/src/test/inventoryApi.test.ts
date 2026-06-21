import { describe, it, expect, vi, beforeEach, type Mock } from "vitest";
import { api } from "../api/api";
import {
  getAllProducts,
  getProductByCode,
  createProduct,
  updateProduct,
  deleteProduct,
} from "../features/inventory/inventoryApi";

vi.mock("../api/api", () => ({
  api: {
    get: vi.fn(),
    post: vi.fn(),
    put: vi.fn(),
    delete: vi.fn(),
  },
}));

describe("inventoryApi", () => {
  beforeEach(() => {
    vi.clearAllMocks();
  });

  it("getAllProducts envia GET a /inventory", async () => {
    const mockResponse = { data: [{ code: "ABC123" }] };
    (api.get as Mock).mockResolvedValue(mockResponse);

    const result = await getAllProducts();

    expect(api.get).toHaveBeenCalledWith("/inventory");
    expect(result).toEqual(mockResponse.data);
  });

  it("getProductByCode envia GET a /inventory/code/:code", async () => {
    const mockResponse = { data: { code: "ABC123", name: "Producto" } };
    (api.get as Mock).mockResolvedValue(mockResponse);

    const result = await getProductByCode("ABC123");

    expect(api.get).toHaveBeenCalledWith("/inventory/code/ABC123");
    expect(result).toEqual(mockResponse.data);
  });

  it("createProduct envia POST a /inventory con los datos", async () => {
    const payload = { code: "ABC123", name: "Producto" };
    const mockResponse = { data: payload };
    (api.post as Mock).mockResolvedValue(mockResponse);

    const result = await createProduct(payload);

    expect(api.post).toHaveBeenCalledWith("/inventory", payload);
    expect(result).toEqual(mockResponse.data);
  });

  it("updateProduct envia PUT a /inventory/code/:code con los datos", async () => {
    const payload = { name: "Nuevo Nombre" };
    const mockResponse = { data: { code: "ABC123", ...payload } };
    (api.put as Mock).mockResolvedValue(mockResponse);

    const result = await updateProduct("ABC123", payload);

    expect(api.put).toHaveBeenCalledWith("/inventory/code/ABC123", payload);
    expect(result).toEqual(mockResponse.data);
  });

  it("deleteProduct envia DELETE a /inventory/code/:code", async () => {
    (api.delete as Mock).mockResolvedValue({ data: undefined });

    await deleteProduct("ABC123");

    expect(api.delete).toHaveBeenCalledWith("/inventory/code/ABC123");
  });
});