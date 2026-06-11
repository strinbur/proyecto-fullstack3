import { useContext, useState } from "react";
import { CartContext } from "../../features/cart/CartContext";
import { AuthContext } from "../../features/auth/AuthContext";
import "./Cart.css";

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

  const getPurchaseStats = () => {
    const stored = localStorage.getItem("purchaseStats");
    return stored ? (JSON.parse(stored) as Record<string, number>) : {};
  };

  const savePurchaseStats = (stats: Record<string, number>) => {
    localStorage.setItem("purchaseStats", JSON.stringify(stats));
  };

  const getOrderHistory = () => {
    const stored = localStorage.getItem("orderHistory");
    return stored ? (JSON.parse(stored) as Record<string, OrderEntry[]>) : {};
  };

  const saveOrderHistory = (history: Record<string, OrderEntry[]>) => {
    localStorage.setItem("orderHistory", JSON.stringify(history));
  };

  const handleQuantityChange = (code: string, value: number, stock: number) => {
    if (value < 1) return;
    if (value > stock) {
      setCheckoutMessage(`No puedes pedir más de ${stock} unidades`);
      return;
    }

    setCheckoutMessage("");
    updateQuantity(code, value);
  };

  const handleCheckout = () => {
    if (!auth?.user) {
      setCheckoutMessage("Debes iniciar sesión para pagar");
      return;
    }

    if (items.length === 0) {
      setCheckoutMessage("Agrega productos al carrito antes de pagar");
      return;
    }

    const purchasedCount = items.reduce((sum, item) => sum + item.quantity, 0);
    const orderEntry: OrderEntry = {
      id: `${Date.now()}`,
      createdAt: new Date().toISOString(),
      totalItems,
      totalPrice: Number(totalPrice.toFixed(2)),
      status: "COMPLETADO",
      items: items.map((item) => ({
        name: item.name,
        quantity: item.quantity,
        price: item.price,
        subtotal: Number((item.price * item.quantity).toFixed(2)),
      })),
    };

    const history = getOrderHistory();
    history[auth.user.id] = [orderEntry, ...(history[auth.user.id] ?? [])];
    saveOrderHistory(history);

    const stats = getPurchaseStats();
    stats[auth.user.id] = (stats[auth.user.id] || 0) + purchasedCount;
    savePurchaseStats(stats);
    clearCart();
    setCheckoutMessage(`Compra realizada: ${purchasedCount} producto${purchasedCount > 1 ? "s" : ""}`);
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
              <p>Total: ${totalPrice.toFixed(2)}</p>
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
                      <p className="cart-price">Precio: ${item.price.toFixed(2)}</p>
                      <p className="cart-stock">Stock disponible: {item.stock}</p>
                      <p className="cart-subtotal">Subtotal: ${(item.price * item.quantity).toFixed(2)}</p>
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
                  <p className="checkout-total">Total: ${totalPrice.toFixed(2)}</p>
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
