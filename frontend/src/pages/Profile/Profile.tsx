import { useState, useEffect, useContext } from "react";
import toast from "react-hot-toast";
import axios from "axios";
import { AuthContext } from "../../features/auth/AuthContext";
import { updateUser } from "../../features/auth/authApi";
import "./Profile.css";

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

function Profile() {
  const auth = useContext(AuthContext);

  if (!auth) {
    throw new Error("AuthContext no está disponible");
  }

  const { user, logout } = auth;
  const [editing, setEditing] = useState(false);
  const [form, setForm] = useState({ name: "", lastname: "", email: "" });
  const [orderHistory, setOrderHistory] = useState<OrderEntry[]>([]);

  useEffect(() => {
    if (!user) return;

    const fetchOrderHistory = async () => {
      try {
        const token = localStorage.getItem("token");
        const headers: Record<string, string> = {};

        if (token) {
          headers.Authorization = `Bearer ${token}`;
        }

        const response = await axios.get<OrderEntry[]>(
          `http://localhost:8084/orders/user/${encodeURIComponent(user.email)}`,
          { headers }
        );

        const orders = response.data.map((order) => ({
          id: order.id,
          createdAt: order.createdAt,
          totalItems: order.items?.reduce((sum, item) => sum + (item.quantity ?? 0), 0) ?? 0,
          totalPrice: (order as any).total ?? order.totalPrice ?? 0,
          status: String(order.status),
          items: order.items ?? [],
        }));

        setOrderHistory(orders);
      } catch (error) {
        console.error("Error cargando historial de pedidos:", error);
        setOrderHistory([]);
      }
    };

    fetchOrderHistory();
  }, [user]);

  if (!user) {
    return <h1 style={{ textAlign: "center" }}>No estás logueado</h1>;
  }

  const initial = user.name?.charAt(0).toUpperCase();
  const showRole = !["cliente", "client"].includes(user.role?.toLowerCase() ?? "");

  const startEditing = () => {
    setForm({
      name: user.name,
      lastname: user.lastname,
      email: user.email,
    });
    setEditing(true);
  };

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setForm((prev) => ({ ...prev, [name]: value }));
  };

  const handleSave = async () => {
    try {

      const data = await updateUser(user.id, form);


      const updatedUser = {
        ...user,
        name: data.name,
        lastname: data.lastname,
        email: data.email,
      };

      localStorage.setItem("user", JSON.stringify(updatedUser));

      toast.success("Perfil actualizado");

      setEditing(false);

      window.location.reload();

    } catch (error: unknown) {

      console.error(error);

      let message = "Error al actualizar";

      if (axios.isAxiosError(error)) {

        message = typeof error.response?.data === "string"
          ? error.response.data
          : "Error al actualizar";
      }

      toast.error(message);
    }
  };

  const handleCancel = () => {

    setForm({
      name: user.name,
      lastname: user.lastname,
      email: user.email,
    });

    setEditing(false);
  };

  return (
    <div className="profile-container">
      <div className="profile-main">
        <div className="profile-card">

          <div className="profile-avatar">{initial}</div>

          <h1 className="profile-title">Mi Perfil</h1>

          <div className="profile-info">

          {editing ? (
            <>
              <input
                name="name"
                value={form.name}
                onChange={handleChange}
                className="profile-input"
                placeholder="Nombre"
              />

              <input
                name="lastname"
                value={form.lastname}
                onChange={handleChange}
                className="profile-input"
                placeholder="Apellido"
              />

              <input
                name="email"
                value={form.email}
                onChange={handleChange}
                className="profile-input"
                placeholder="Correo"
              />
            </>
          ) : (
            <>
              <p><strong>Nombre:</strong> {user.name}</p>
              <p><strong>Apellido:</strong> {user.lastname}</p>
              <p><strong>Correo:</strong> {user.email}</p>
              {showRole && <p><strong>Rol:</strong> {user.role}</p>}
            </>
          )}

        </div>

        <div className="profile-actions">

          {editing ? (
            <>
              <button
                className="profile-button"
                onClick={handleSave}
              >
                Guardar cambios
              </button>

              <button
                className="profile-button cancel"
                onClick={handleCancel}
              >
                Cancelar
              </button>
            </>
          ) : (
            <button
              className="profile-button blue"
              onClick={startEditing}
            >
              Editar perfil
            </button>
          )}

          <button
            className="profile-button logout"
            onClick={logout}
          >
            Cerrar sesión
          </button>

        </div>
        </div>
      </div>

      <div className="profile-orders">
          <h2>Pedidos recientes</h2>

        {orderHistory.length === 0 ? (
          <p className="no-orders">Aún no has registrado pedidos en esta sesión.</p>
        ) : (
          <div className="orders-table-wrapper">
            <table className="orders-table">
              <thead>
                <tr>
                  <th>ID pedido</th>
                  <th>Fecha</th>
                  <th>Estado</th>
                  <th>Productos</th>
                  <th>Total</th>
                  <th>Unidades</th>
                </tr>
              </thead>
              <tbody>
                {orderHistory.map((order) => (
                  <tr key={order.id}>
                    <td>{order.id.slice(-6)}</td>
                    <td>{new Date(order.createdAt).toLocaleString("es-ES", { dateStyle: "short", timeStyle: "short" })}</td>
                    <td>
                      <span className="order-status">{order.status}</span>
                    </td>
                    <td className="order-products">
                      {order.items.map((item, index) => (
                        <span key={`${order.id}-${item.name}-${index}`}>
                          {item.name} x{item.quantity}
                          {index < order.items.length - 1 ? ", " : ""}
                        </span>
                      ))}
                    </td>
                    <td>${order.totalPrice.toFixed(2)}</td>
                    <td>{order.totalItems}</td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        )}
      </div>
    </div>
  );
}

export default Profile;