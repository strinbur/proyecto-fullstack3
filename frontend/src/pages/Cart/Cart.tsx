import { useContext, useState } from "react";
import { CartContext } from "../../features/cart/CartContext";
import { AuthContext } from "../../features/auth/AuthContext";
import "./Cart.css";
import { formatCurrency } from "../../utils/format";
import { getAllProducts } from "../../features/inventory/inventoryApi";
import { api } from "../../api/api";
import axios from "axios";

interface OrderItem {
  name: string;
  quantity: number;
  price: number;
  subtotal: number;
}

interface OrderEntry {
  id: string;
  createdAt: string;
  totalItems: number;
  totalPrice: number;
  status: string;
  items: OrderItem[];
}

export default function Cart() {
  const { items, totalItems, totalPrice, updateQuantity, removeFromCart, clearCart } = useContext(CartContext);
  const auth = useContext(AuthContext);
  const [checkoutMessage, setCheckoutMessage] = useState("");

  // server-side cart and order will be used (BFF + ms-order).

  const handleQuantityChange = (code: string, value: number, stock: number) => {
    if (value < 1) return;
    if (value > stock) {
      setCheckoutMessage(`No puedes pedir más de ${stock} unidades`);
      return;
    }

    setCheckoutMessage("");
    updateQuantity(code, value);
  };

  const handleCheckout = async () => {
    if (!auth?.user) {
      setCheckoutMessage("Debes iniciar sesión para pagar");
      return;
    }

    if (items.length === 0) {
      setCheckoutMessage("Agrega productos al carrito antes de pagar");
      return;
    }

    // Validate against current stock from inventory
    try {
      const currentProducts = await getAllProducts();
      for (const item of items) {
        const prod = currentProducts.find((p: any) => p.code === item.code || p.id === item.id);
        const available = prod ? prod.quantity : 0;
        if (available === 0) {
          setCheckoutMessage(`Producto agotado: ${item.name}`);
          return;
        }
        if (item.quantity > available) {
          setCheckoutMessage(`No hay suficientes unidades de ${item.name}. Disponible: ${available}`);
          return;
        }
      }
    } catch (e) {
      setCheckoutMessage("No se pudo validar stock actual. Intenta más tarde.");
      return;
    }

    // Sync cart to server via BFF (/bff/cart)
    try {
      await api.delete(`/cart/clear`);
      for (const item of items) {
        await api.post(`/cart/add`, { productCode: item.code, quantity: item.quantity });
      }

      // Call ms-order to create order (ms-order will use Feign to read the server cart)
      const token = localStorage.getItem("token");
      const headers: Record<string, string> = { userEmail: auth.user.email };
      if (token) headers.Authorization = `Bearer ${token}`;

      await axios.post("http://localhost:8084/orders", null, { headers });

      // success
      clearCart();
      setCheckoutMessage("Compra realizada correctamente");
    } catch (e: any) {
      const message = e?.response?.data || e.message || "Error al procesar la compra";
      setCheckoutMessage(String(message));
    }
  };

  return (
    <div className="cart-page">
      <div className="cart-container">
        <h1>Carrito de Compras</h1>

        {items.length === 0 ? (
          <p>Tu carrito está vacío. Agrega productos desde la página de productos.</p>
        ) : (
          <>
            <div className="cart-summary">
              <p>Productos: {totalItems}</p>
              <p>Total: {formatCurrency(totalPrice)}</p>
              <button className="clear-cart-btn" onClick={clearCart}>
                Vaciar carrito
              </button>
            </div>

            <div className="cart-content">
              <div className="cart-items">
                {items.map((item) => (
                  <div key={item.code} className="cart-item">
                    <div className="cart-item-details">
                      <h3>{item.name}</h3>
                      <p className="cart-item-brand">{item.brand}</p>
                      <div className="cart-quantity">
                        <label htmlFor={`quantity-${item.code}`}>Cantidad</label>
                        <input
                          id={`quantity-${item.code}`}
                          type="number"
                          value={item.quantity}
                          min={1}
                          max={item.stock}
                          onChange={(e) => handleQuantityChange(item.code, Number(e.target.value), item.stock)}
                        />
                      </div>
                      <p className="cart-price">Precio: {formatCurrency(item.price)}</p>
                      <p className="cart-stock">Stock disponible: {item.stock}</p>
                      <p className="cart-subtotal">Subtotal: {formatCurrency(item.price * item.quantity)}</p>
                      <button className="remove-item-btn" onClick={() => removeFromCart(item.code)}>
                        Eliminar
                      </button>
                    </div>

                    <div className="cart-item-image">
                      <img src={item.imageUrl} alt={item.name} />
                    </div>
                  </div>
                ))}
              </div>

              <aside className="cart-checkout">
                <div className="checkout-card">
                  <h2>Resumen</h2>
                  <p>Productos: {totalItems}</p>
                  <p className="checkout-total">Total: {formatCurrency(totalPrice)}</p>
                  {checkoutMessage && <p className="checkout-message">{checkoutMessage}</p>}
                  <button className="checkout-btn" onClick={handleCheckout}>Pagar</button>
                </div>
              </aside>
            </div>
          </>
        )}
      </div>
    </div>
  );
}
