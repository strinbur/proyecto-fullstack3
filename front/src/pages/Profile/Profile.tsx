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

  const { user, logout } = auth;
  const [editing, setEditing] = useState(false);
  const [form, setForm] = useState({ name: "", lastname: "", email: "" });

  if (!user) {
    return <h1 style={{ textAlign: "center" }}>No estás logueado</h1>;
  }

  const initial = user.name?.charAt(0).toUpperCase();


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
              <p><strong>Rol:</strong> {user.role}</p>
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
  );
}

export default Profile;