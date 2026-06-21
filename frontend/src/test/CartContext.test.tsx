import { describe, it, expect, beforeEach, vi } from "vitest";
import { renderHook, act } from "@testing-library/react";
import { useContext } from "react";
import { CartProvider, CartContext, CartProduct } from "../features/cart/CartContext";

const baseProduct: Omit<CartProduct, "quantity"> = {
  id: "1",
  code: "ABC123",
  name: "Producto Test",
  brand: "MarcaTest",
  price: 1000,
  category: "categoria",
  imageUrl: "img.png",
  stock: 5,
};

function setup() {
  return renderHook(() => useContext(CartContext), {
    wrapper: ({ children }) => <CartProvider>{children}</CartProvider>,
  });
}

describe("CartContext", () => {
  beforeEach(() => {
    localStorage.clear();
  });

  it("inicia vacio cuando no hay nada en localStorage", () => {
    const { result } = setup();

    expect(result.current.items).toEqual([]);
    expect(result.current.totalItems).toBe(0);
    expect(result.current.totalPrice).toBe(0);
  });

  it("carga items existentes desde localStorage al iniciar", () => {
    localStorage.setItem(
      "cart",
      JSON.stringify([{ ...baseProduct, quantity: 2 }])
    );

    const { result } = setup();

    expect(result.current.items).toHaveLength(1);
    expect(result.current.totalItems).toBe(2);
  });

  it("si localStorage tiene JSON invalido, inicia vacio sin crashear", () => {
    localStorage.setItem("cart", "{esto no es json valido");

    const { result } = setup();

    expect(result.current.items).toEqual([]);
  });

  it("addToCart agrega un producto nuevo", () => {
    const { result } = setup();

    act(() => {
      result.current.addToCart(baseProduct, 2);
    });

    expect(result.current.items).toHaveLength(1);
    expect(result.current.items[0].quantity).toBe(2);
    expect(result.current.totalItems).toBe(2);
    expect(result.current.totalPrice).toBe(2000);
  });

  it("addToCart suma cantidad si el producto ya existe", () => {
    const { result } = setup();

    act(() => {
      result.current.addToCart(baseProduct, 2);
    });

    act(() => {
      result.current.addToCart(baseProduct, 1);
    });

    expect(result.current.items).toHaveLength(1);
    expect(result.current.items[0].quantity).toBe(3);
  });

  it("addToCart no agrega si la cantidad es menor a 1", () => {
    const { result } = setup();

    act(() => {
      result.current.addToCart(baseProduct, 0);
    });

    expect(result.current.items).toHaveLength(0);
  });

  it("addToCart no supera el stock disponible al sumar cantidades", () => {
    const { result } = setup();

    act(() => {
      result.current.addToCart(baseProduct, 4);
    });

    act(() => {
      // stock es 5, ya hay 4, intenta sumar 3 mas (total 7) -> debe rechazar
      result.current.addToCart(baseProduct, 3);
    });

    expect(result.current.items[0].quantity).toBe(4);
  });

  it("updateQuantity actualiza la cantidad de un item existente", () => {
    const { result } = setup();

    act(() => {
      result.current.addToCart(baseProduct, 1);
    });

    act(() => {
      result.current.updateQuantity("ABC123", 3);
    });

    expect(result.current.items[0].quantity).toBe(3);
  });

  it("updateQuantity limita la cantidad al stock maximo", () => {
    const { result } = setup();

    act(() => {
      result.current.addToCart(baseProduct, 1);
    });

    act(() => {
      result.current.updateQuantity("ABC123", 999);
    });

    expect(result.current.items[0].quantity).toBe(baseProduct.stock);
  });

  it("updateQuantity no hace nada si la cantidad es menor a 1", () => {
    const { result } = setup();

    act(() => {
      result.current.addToCart(baseProduct, 2);
    });

    act(() => {
      result.current.updateQuantity("ABC123", 0);
    });

    expect(result.current.items[0].quantity).toBe(2);
  });

  it("removeFromCart elimina el producto indicado", () => {
    const { result } = setup();

    act(() => {
      result.current.addToCart(baseProduct, 1);
    });

    act(() => {
      result.current.removeFromCart("ABC123");
    });

    expect(result.current.items).toHaveLength(0);
  });

  it("clearCart vacia todo el carrito", () => {
    const { result } = setup();

    act(() => {
      result.current.addToCart(baseProduct, 1);
      result.current.addToCart({ ...baseProduct, code: "XYZ789" }, 1);
    });

    act(() => {
      result.current.clearCart();
    });

    expect(result.current.items).toHaveLength(0);
    expect(result.current.totalItems).toBe(0);
  });

  it("persiste los items en localStorage al modificar el carrito", () => {
    const { result } = setup();

    act(() => {
      result.current.addToCart(baseProduct, 1);
    });

    const stored = JSON.parse(localStorage.getItem("cart") || "[]");
    expect(stored).toHaveLength(1);
    expect(stored[0].code).toBe("ABC123");
  });

  it("no crashea si localStorage.setItem falla", () => {
    const setItemSpy = vi
      .spyOn(Storage.prototype, "setItem")
      .mockImplementation(() => {
        throw new Error("quota exceeded");
      });

    const { result } = setup();

    expect(() => {
      act(() => {
        result.current.addToCart(baseProduct, 1);
      });
    }).not.toThrow();

    setItemSpy.mockRestore();
  });
});