import { useState, useEffect } from "react";
import toast from "react-hot-toast";
import axios from "axios";

import { listarProductos } from "../../features/inventory/inventoryApi";
import {
  createUserWithRole,
  getUsers,
  deleteUser
} from "../../features/auth/authApi";

import "./Dashboard.css";

/* =========================
   TIPADO
========================= */

type Rol = "CLIENTE" | "ADMIN" | "VENTAS";

type User = {
  id: string;
  nombre: string;
  apellido: string;
  correo: string;
  rol: Rol;
};

type Producto = {
  id: string;
  nombre: string;
  precio: number;
};

export default function Dashboard() {

  /* =========================
     OCULTAR EN CELULAR
  ========================== */

  if (window.innerWidth <= 768) {
    return null;
  }

  /* =========================
     STATES
  ========================== */

  const [users, setUsers] = useState<User[]>([]);
  const [productos, setProductos] = useState<Producto[]>([]);

  const [nombre, setNombre] = useState("");
  const [apellido, setApellido] = useState("");
  const [correo, setCorreo] = useState("");
  const [password, setPassword] = useState("");
  const [rol, setRol] = useState<Rol>("CLIENTE");

  /* =========================
     CARGAR USUARIOS
  ========================== */

  useEffect(() => {

    const fetchUsers = async () => {

      try {

        const data: User[] = await getUsers();
        setUsers(data);

      } catch (error) {

        console.error(error);
        toast.error("Error al cargar usuarios");

      }

    };

    fetchUsers();

  }, []);

  /* =========================
     CARGAR PRODUCTOS
  ========================== */

  useEffect(() => {

    const fetchProducts = async () => {

      try {

        const data: Producto[] = await listarProductos();
        setProductos(data);

      } catch (error) {

        console.error(error);
        toast.error("Error al cargar productos");

      }

    };

    fetchProducts();

  }, []);

  /* =========================
     CREAR USUARIO
  ========================== */

  const handleCreateUser = async () => {

    try {

      await createUserWithRole({
        nombre,
        apellido,
        correo,
        password,
        rol
      });

      toast.success(
        `Usuario ${rol} creado correctamente`
      );

      setNombre("");
      setApellido("");
      setCorreo("");
      setPassword("");
      setRol("CLIENTE");

      const data: User[] = await getUsers();
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

        if (
          status === 409 ||
          message?.toLowerCase().includes("correo")
        ) {

          toast.error(
            "El correo ya está registrado"
          );

          return;
        }

        toast.error(
          message || "Error al crear usuario"
        );

      } else {

        toast.error("Error inesperado");

      }

    }

  };

  /* =========================
     ELIMINAR USUARIO
  ========================== */

  const handleDeleteUser = async (id: string) => {

    try {

      await deleteUser(id);

      toast.success("Usuario eliminado");

      const data: User[] = await getUsers();
      setUsers(data);

    } catch (error) {

      console.error(error);
      toast.error(
        "Error al eliminar usuario"
      );

    }

  };

  /* =========================
     JSX
  ========================== */

  return (

    <div className="dashboard-page">

      <div className="dashboard-container">

        {/* HEADER */}

        <header className="dashboard-header-centered">

          <h1>
            Panel de Administración
          </h1>

          <p>
            Bienvenido al centro de control
            de Grupo Cordillera
          </p>

        </header>

        {/* STATS */}

        <div className="stats-grid-centered">

          <div className="stat-card">

            <div className="stat-icon blue">
              📦
            </div>

            <div>

              <span className="stat-label">
                Productos
              </span>

              <span className="stat-number">
                {productos.length}
              </span>

            </div>

          </div>

          <div className="stat-card">

            <div className="stat-icon green">
              💰
            </div>

            <div>

              <span className="stat-label">
                Ventas
              </span>

              <span className="stat-number">
                128
              </span>

            </div>

          </div>

          <div className="stat-card">

            <div className="stat-icon orange">
              👥
            </div>

            <div>

              <span className="stat-label">
                Usuarios
              </span>

              <span className="stat-number">
                {users.length}
              </span>

            </div>

          </div>

        </div>

        {/* CREAR USUARIO */}

        <div className="create-user-card">

          <h2>
            Crear Usuario
          </h2>

          <input
            placeholder="Nombre"
            value={nombre}
            onChange={(e) =>
              setNombre(e.target.value)
            }
          />

          <input
            placeholder="Apellido"
            value={apellido}
            onChange={(e) =>
              setApellido(e.target.value)
            }
          />

          <input
            placeholder="Correo"
            value={correo}
            onChange={(e) =>
              setCorreo(e.target.value)
            }
          />

          <input
            type="password"
            placeholder="Password"
            value={password}
            onChange={(e) =>
              setPassword(e.target.value)
            }
          />

          <select
            value={rol}
            onChange={(e) =>
              setRol(e.target.value as Rol)
            }
          >

            <option value="CLIENTE">
              Cliente
            </option>

            <option value="ADMIN">
              Administrador
            </option>

            <option value="VENTAS">
              Ventas
            </option>

          </select>

          <button onClick={handleCreateUser}>
            Crear Usuario
          </button>

        </div>

        {/* TABLA */}

        <div className="dashboard-main-card">

          <h3>
            Gestión de Usuarios
          </h3>

          <table className="custom-table">

            <thead>

              <tr>
                <th>Nombre</th>
                <th>Correo</th>
                <th>Rol</th>
                <th>Gestión</th>
              </tr>

            </thead>

            <tbody>

              {users.map((user) => (

                <tr key={user.id}>

                  <td>
                    {user.nombre}
                    {" "}
                    {user.apellido}
                  </td>

                  <td>
                    {user.correo}
                  </td>

                  <td>
                    {user.rol}
                  </td>

                  <td>

                    <button
                      className="delete-btn"
                      onClick={() =>
                        handleDeleteUser(user.id)
                      }
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