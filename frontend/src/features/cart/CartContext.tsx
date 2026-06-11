import { createContext, useEffect, useMemo, useState, type ReactNode } from "react";

export interface CartProduct {
  id: string;
  code: string;
  name: string;
  brand: string;
  price: number;
  quantity: number;
  category: string;
  imageUrl: string;
  stock: number;
}

export interface CartContextType {
  items: CartProduct[];
  totalItems: number;
  totalPrice: number;
  addToCart: (product: Omit<CartProduct, "quantity">, quantity: number) => void;
  updateQuantity: (code: string, quantity: number) => void;
  removeFromCart: (code: string) => void;
  clearCart: () => void;
}

const defaultValue: CartContextType = {
  items: [],
  totalItems: 0,
  totalPrice: 0,
  addToCart: () => {},
  updateQuantity: () => {},
  removeFromCart: () => {},
  clearCart: () => {},
};

export const CartContext = createContext<CartContextType>(defaultValue);

interface Props {
  children: ReactNode;
}

export function CartProvider({ children }: Props) {
  const [items, setItems] = useState<CartProduct[]>(() => {
    try {
      const stored = localStorage.getItem("cart");
      return stored ? JSON.parse(stored) : [];
    } catch {
      return [];
    }
  });

  useEffect(() => {
    try {
      localStorage.setItem("cart", JSON.stringify(items));
    } catch {
      // ignore storage errors
    }
  }, [items]);

  const totalItems = useMemo(
    () => items.reduce((sum, item) => sum + item.quantity, 0),
    [items]
  );

  const totalPrice = useMemo(
    () => items.reduce((sum, item) => sum + item.price * item.quantity, 0),
    [items]
  );

  const addToCart = (product: Omit<CartProduct, "quantity">, quantity: number) => {
    if (quantity < 1) return;

    setItems((current) => {
      const existing = current.find((item) => item.code === product.code);

      if (existing) {
        const newQuantity = existing.quantity + quantity;
        if (newQuantity > product.stock) {
          return current;
        }

        return current.map((item) =>
          item.code === product.code
            ? { ...item, quantity: newQuantity }
            : item
        );
      }

      return [...current, { ...product, quantity }];
    });
  };

  const updateQuantity = (code: string, quantity: number) => {
    if (quantity < 1) return;

    setItems((current) =>
      current.map((item) =>
        item.code === code
          ? { ...item, quantity: Math.min(quantity, item.stock) }
          : item
      )
    );
  };

  const removeFromCart = (code: string) => {
    setItems((current) => current.filter((item) => item.code !== code));
  };

  const clearCart = () => {
    setItems([]);
  };

  return (
    <CartContext.Provider
      value={{
        items,
        totalItems,
        totalPrice,
        addToCart,
        updateQuantity,
        removeFromCart,
        clearCart,
      }}
    >
      {children}
    </CartContext.Provider>
  );
}
