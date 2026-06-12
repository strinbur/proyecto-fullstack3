import { useState, useEffect } from "react";
import toast from "react-hot-toast";
import axios from "axios";
import { getAllProducts } from "../../features/inventory/inventoryApi";
import { createUser, getAllUsers, deleteUser } from "../../features/auth/authApi";

import "./Dashboard.css";


type Role = "CLIENTE" | "ADMIN" | "VENTAS";

type User = {
  id: string;
  name: string;
  lastname: string;
  email: string;
  role: Role;
  comprado?: number;
};

type Product = {
  id: string;
  code: string;
  name: string;
  price: number;
};

type OrderEntrySimple = {
  id: string;
  totalItems: number;
  totalPrice: number;
};

export default function Dashboard() {

  const [users, setUsers] = useState<User[]>([]);
  const [products, setProducts] = useState<Product[]>([]);
  const [purchaseStats, setPurchaseStats] = useState<Record<string, number>>({});

  const [name, setName] = useState("");
  const [lastname, setLastname] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [role, setRole] = useState<Role>("CLIENTE");

//Carga los usuarios
  const loadPurchaseStats = () => {
    const stored = localStorage.getItem("purchaseStats");
    if (stored) return (JSON.parse(stored) as Record<string, number>);

    // If purchaseStats not present, try to build it from orderHistory
    const ordersStored = localStorage.getItem("orderHistory");
    if (!ordersStored) return {};
    try {
      const history = JSON.parse(ordersStored) as Record<string, OrderEntrySimple[]>;
      const stats: Record<string, number> = {};
      Object.keys(history).forEach((uid) => {
        stats[uid] = history[uid].reduce((s, o) => s + (o.totalItems || 0), 0);
      });
      return stats;
    } catch (e) {
      return {};
    }
  };

  useEffect(() => {
    const fetchUsers = async () => {
      try {
        const data: User[] = await getAllUsers();
        setUsers(data);
        setPurchaseStats(loadPurchaseStats());
      } catch (error) {
        console.error(error);
        toast.error("Error al cargar usuarios");
      }
    };

    fetchUsers();
  }, []);

  useEffect(() => {
    const handleStorage = () => setPurchaseStats(loadPurchaseStats());
    window.addEventListener("storage", handleStorage);
    return () => window.removeEventListener("storage", handleStorage);
  }, []);

  const computeTotalSales = () => {
    // Prefer orderHistory when available
    const ordersStored = localStorage.getItem("orderHistory");
    if (ordersStored) {
      try {
        const history = JSON.parse(ordersStored) as Record<string, OrderEntrySimple[]>;
        return Object.keys(history).reduce((sum, uid) => sum + history[uid].reduce((s, o) => s + (o.totalItems || 0), 0), 0);
      } catch (e) {
        // fallthrough
      }
    }

    // Fallback to purchaseStats totals
    const stats = loadPurchaseStats();
    return Object.values(stats).reduce((s, v) => s + (v || 0), 0);
  };

//Carga los productos
  useEffect(() => {
    const fetchProducts = async () => {
      try {
        const data: Product[] = await getAllProducts();
        setProducts(data);
      } catch (error) {
        console.error(error);
        toast.error("Error al cargar productos");
      }
    };

    fetchProducts();
  }, []);

//Crea un nuevo usuario
  const handleCreateUser = async () => {
    try {
      await createUser({
        name,
        lastname,
        email,
        password,
        role
      });

      toast.success(`Usuario ${role} creado correctamente`);

      setName("");
      setLastname("");
      setEmail("");
      setPassword("");
      setRole("CLIENTE");

      const data: User[] = await getAllUsers();
      setUsers(data);

    } catch (error: unknown) {

      console.error(error);

      if (axios.isAxiosError(error)) {

        const status = error.response?.status;
        const data = error.response?.data;

        const message =
          typeof data === "string"
            ? data
            : data?.message;

        if (status === 409 || message?.toLowerCase().includes("correo")) {
          toast.error("El correo ya está registrado");
          return;
        }

        toast.error(message || "Error al crear usuario");

      } else {
        toast.error("Error inesperado");
      }
    }
  };

//Elimina un usuario por su ID
  const handleDeleteUser = async (id: string) => {
    try {
      await deleteUser(id);
      toast.success("Usuario eliminado");

      const data: User[] = await getAllUsers();
      setUsers(data);

    } catch (error) {
      console.error(error);
      toast.error("Error al eliminar usuario");
    }
  };

  return (
    <div className="dashboard-page">
      <div className="dashboard-container">

        <header className="dashboard-header-centered">
          <h1>Panel de Administración</h1>
          <p>Bienvenido al centro de control de Grupo Cordillera</p>
        </header>

        <div className="stats-grid-centered">

          <div className="stat-card">
            <div className="stat-icon blue">📦</div>
            <div>
              <span className="stat-label">Productos</span>
              <span className="stat-number">{products.length}</span>
            </div>
          </div>

          <div className="stat-card">
            <div className="stat-icon green">💰</div>
            <div>
              <span className="stat-label">Ventas</span>
              <span className="stat-number">{computeTotalSales()}</span>
            </div>
          </div>

          <div className="stat-card">
            <div className="stat-icon orange">👥</div>
            <div>
              <span className="stat-label">Usuarios</span>
              <span className="stat-number">{users.length}</span>
            </div>
          </div>

        </div>

        <div className="create-user-card">

          <h2>Crear Usuario</h2>

          <input placeholder="Nombre" value={name} onChange={(e) => setName(e.target.value)} />
          <input placeholder="Apellido" value={lastname} onChange={(e) => setLastname(e.target.value)} />
          <input placeholder="Correo" value={email} onChange={(e) => setEmail(e.target.value)} />
          <input type="password" placeholder="Password" value={password} onChange={(e) => setPassword(e.target.value)} />

          <select value={role} onChange={(e) => setRole(e.target.value as Role)}>
            <option value="CLIENTE">Cliente</option>
            <option value="ADMIN">Administrador</option>
            <option value="VENTAS">Ventas</option>
          </select>

          <button onClick={handleCreateUser}>
            Crear Usuario
          </button>

        </div>

        <div className="dashboard-main-card">

          <h3>Gestión de Usuarios</h3>

          <table className="custom-table">

            <thead>
              <tr>
                <th>Nombre</th>
                <th>Correo</th>
                <th>Rol</th>
                <th>Comprado</th>
                <th>Gestión</th>
              </tr>
            </thead>

            <tbody>
              {users.map((user) => (
                <tr key={user.id}>
                  <td>{user.name} {user.lastname}</td>
                  <td>{user.email}</td>
                  <td>{user.role}</td>
                  <td>{purchaseStats[user.id] ?? user.comprado ?? 0}</td>
                  <td>
                    <button
                      className="delete-btn"
                      onClick={() => handleDeleteUser(user.id)}
                    >
                      Eliminar
                    </button>
                  </td>
                </tr>
              ))}
            </tbody>

          </table>

        </div>

      </div>
    </div>
  );
}