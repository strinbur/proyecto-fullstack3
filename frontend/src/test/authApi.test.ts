import { describe, it, expect, vi, beforeEach, type Mock } from "vitest";
import { api } from "../api/api";
import {
  login,
  createClient,
  createUser,
  updateUser,
  getAllUsers,
  deleteUser,
} from "../features/auth/authApi";

vi.mock("../api/api", () => ({
  api: {
    get: vi.fn(),
    post: vi.fn(),
    put: vi.fn(),
    delete: vi.fn(),
  },
}));

describe("authApi", () => {
  beforeEach(() => {
    vi.clearAllMocks();
  });

  it("login envia POST a /login con email y password", async () => {
    const mockResponse = { data: { token: "abc", user: { id: "1" } } };
    (api.post as Mock).mockResolvedValue(mockResponse);

    const result = await login("test@test.cl", "123456");

    expect(api.post).toHaveBeenCalledWith("/login", {
      email: "test@test.cl",
      password: "123456",
    });
    expect(result).toEqual(mockResponse.data);
  });

  it("createClient envia POST a /login/register con los datos", async () => {
    const payload = { name: "Pato", email: "pa@test.cl" };
    const mockResponse = { data: { id: "1" } };
    (api.post as Mock).mockResolvedValue(mockResponse);

    const result = await createClient(payload);

    expect(api.post).toHaveBeenCalledWith("/login/register", payload);
    expect(result).toEqual(mockResponse.data);
  });

  it("createUser envia POST a /login/admin con los datos", async () => {
    const payload = { name: "Admin", role: "ADMIN" };
    const mockResponse = { data: { id: "2" } };
    (api.post as Mock).mockResolvedValue(mockResponse);

    const result = await createUser(payload);

    expect(api.post).toHaveBeenCalledWith("/login/admin", payload);
    expect(result).toEqual(mockResponse.data);
  });

  it("updateUser envia PUT a /login/:id con los datos", async () => {
    const payload = { name: "Nuevo Nombre" };
    const mockResponse = { data: { id: "1", name: "Nuevo Nombre" } };
    (api.put as Mock).mockResolvedValue(mockResponse);

    const result = await updateUser("1", payload);

    expect(api.put).toHaveBeenCalledWith("/login/1", payload);
    expect(result).toEqual(mockResponse.data);
  });

  it("getAllUsers envia GET a /login", async () => {
    const mockResponse = { data: [{ id: "1" }, { id: "2" }] };
    (api.get as Mock).mockResolvedValue(mockResponse);

    const result = await getAllUsers();

    expect(api.get).toHaveBeenCalledWith("/login");
    expect(result).toEqual(mockResponse.data);
  });

  it("deleteUser envia DELETE a /login/:id", async () => {
    const mockResponse = { data: { success: true } };
    (api.delete as Mock).mockResolvedValue(mockResponse);

    const result = await deleteUser("1");

    expect(api.delete).toHaveBeenCalledWith("/login/1");
    expect(result).toEqual(mockResponse.data);
  });
});