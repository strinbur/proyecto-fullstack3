import { describe, it, expect, beforeEach } from "vitest";
import { renderHook, act } from "@testing-library/react";
import { useContext } from "react";
import { AuthContext, type User } from "../features/auth/AuthContext";
import { AuthProvider } from "../features/auth/AuthProvider";

const mockUser: User = {
  id: "1",
  name: "Patricio",
  lastname: "Olguin",
  email: "pa.olguine@duocuc.cl",
  role: "ADMIN",
};

function setup() {
  return renderHook(() => useContext(AuthContext), {
    wrapper: ({ children }) => <AuthProvider>{children}</AuthProvider>,
  });
}

describe("AuthProvider", () => {
  beforeEach(() => {
    localStorage.clear();
  });

  it("inicia sin usuario cuando no hay nada en localStorage", () => {
    const { result } = setup();

    expect(result.current?.user).toBeNull();
  });

  it("carga el usuario existente desde localStorage al iniciar", () => {
    localStorage.setItem("user", JSON.stringify(mockUser));

    const { result } = setup();

    expect(result.current?.user).toEqual(mockUser);
  });

  it("loginUser guarda el token y el usuario en localStorage", () => {
    const { result } = setup();

    act(() => {
      result.current?.loginUser({ token: "fake-jwt-token", user: mockUser });
    });

    expect(localStorage.getItem("token")).toBe("fake-jwt-token");
    expect(JSON.parse(localStorage.getItem("user") || "{}")).toEqual(mockUser);
  });

  it("loginUser actualiza el estado user del contexto", () => {
    const { result } = setup();

    act(() => {
      result.current?.loginUser({ token: "fake-jwt-token", user: mockUser });
    });

    expect(result.current?.user).toEqual(mockUser);
  });

  it("logout limpia token y user de localStorage", () => {
    const { result } = setup();

    act(() => {
      result.current?.loginUser({ token: "fake-jwt-token", user: mockUser });
    });

    act(() => {
      result.current?.logout();
    });

    expect(localStorage.getItem("token")).toBeNull();
    expect(localStorage.getItem("user")).toBeNull();
  });

  it("logout deja el user del contexto en null", () => {
    const { result } = setup();

    act(() => {
      result.current?.loginUser({ token: "fake-jwt-token", user: mockUser });
    });

    act(() => {
      result.current?.logout();
    });

    expect(result.current?.user).toBeNull();
  });

  it("loginUser sobrescribe un usuario previo", () => {
    const { result } = setup();
    const otherUser: User = { ...mockUser, id: "2", email: "otro@test.cl" };

    act(() => {
      result.current?.loginUser({ token: "token-1", user: mockUser });
    });

    act(() => {
      result.current?.loginUser({ token: "token-2", user: otherUser });
    });

    expect(result.current?.user).toEqual(otherUser);
    expect(localStorage.getItem("token")).toBe("token-2");
  });
});