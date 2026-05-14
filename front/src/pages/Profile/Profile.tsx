import { useState, useContext } from "react";
import toast from "react-hot-toast";
import axios from "axios";
import { AuthContext } from "../../features/auth/AuthContext";
import { updateUser } from "../../features/auth/authApi";
import "./Profile.css";

function Profile() {
  const auth = useContext(AuthContext);

  if (!auth) {
    throw new Error("AuthContext no está disponible");
  }

  const { usuario, logout } = auth;
  const [editando, setEditando] = useState(false);
  const [form, setForm] = useState({ nombre: "", apellido: "", correo: "" });

  if (!usuario) {
    return <h1 style={{ textAlign: "center" }}>No estás logueado</h1>;
  }

  const inicial = usuario.nombre?.charAt(0).toUpperCase();

  // ======================================================
  // LOGICA DE ACCIONES
  // ======================================================
  const iniciarEdicion = () => {
    setForm({
      nombre: usuario.nombre,
      apellido: usuario.apellido,
      correo: usuario.correo,
    });
    setEditando(true);
  };

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setForm((prev) => ({ ...prev, [name]: value }));
  };

  const handleGuardar = async () => {
    try {
      const data = await updateUser(usuario.id, form);

      // ACTUALIZAR LOCALSTORAGE
      const actualizado = {
        ...usuario,
        nombre: data.nombre,
        apellido: data.apellido,
        correo: data.correo,
      };

      localStorage.setItem("usuario", JSON.stringify(actualizado));
      toast.success("Perfil actualizado");
      setEditando(false);
      window.location.reload();
    } catch (error: unknown) {
      console.error(error);
      let mensaje = "Error al actualizar";
      if (axios.isAxiosError(error)) {
        mensaje = typeof error.response?.data === "string" 
          ? error.response.data 
          : "Error al actualizar";
      }
      toast.error(mensaje);
    }
  };

  const handleCancelar = () => {
    setForm({
      nombre: usuario.nombre,
      apellido: usuario.apellido,
      correo: usuario.correo,
    });
    setEditando(false);
  };

  return (
    <div className="profile-container">
      <div className="profile-card">
        <div className="profile-avatar">{inicial}</div>
        <h1 className="profile-title">Mi Perfil</h1>

        <div className="profile-info">
          {editando ? (
            <>
              <input name="nombre" value={form.nombre} onChange={handleChange} className="profile-input" placeholder="Nombre" />
              <input name="apellido" value={form.apellido} onChange={handleChange} className="profile-input" placeholder="Apellido" />
              <input name="correo" value={form.correo} onChange={handleChange} className="profile-input" placeholder="Correo" />
            </>
          ) : (
            <>
              <p><strong>Nombre:</strong> {usuario.nombre}</p>
              <p><strong>Apellido:</strong> {usuario.apellido}</p>
              <p><strong>Correo:</strong> {usuario.correo}</p>
              <p><strong>Rol:</strong> {usuario.rol}</p>
            </>
          )}
        </div>

        <div className="profile-actions">
          {editando ? (
            <>
              <button className="profile-button" onClick={handleGuardar}>Guardar cambios</button>
              <button className="profile-button cancel" onClick={handleCancelar}>Cancelar</button>
            </>
          ) : (
            <button className="profile-button blue" onClick={iniciarEdicion}>Editar perfil</button>
          )}
          <button className="profile-button logout" onClick={logout}>Cerrar sesión</button>
        </div>
      </div>
    </div>
  );
}

export default Profile;