import { useState, useContext } from "react";
import { AuthContext } from "../../features/auth/AuthContext";
import { updateUser } from "../../features/auth/authApi";
import "./Profile.css";

function Profile() {
  const auth = useContext(AuthContext);

  if (!auth) {
    throw new Error("AuthContext no está disponible");
  }

  const { usuario, logout, loginUser } = auth;

  const [editando, setEditando] = useState(false);


  const [form, setForm] = useState({
    nombre: "",
    apellido: "",
    correo: "",
  });

  if (!usuario) {
    return <h1 style={{ textAlign: "center" }}>No estás logueado</h1>;
  }

  const inicial = usuario.nombre?.charAt(0).toUpperCase();


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

    setForm((prev) => ({
      ...prev,
      [name]: value,
    }));
  };


  const handleGuardar = async () => {
    try {
      const data = await updateUser(usuario.id, form);

      loginUser(data);
      setEditando(false);

      alert("Perfil actualizado correctamente");
    } catch (error) {
      console.error(error);
      alert("Error al actualizar perfil");
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

        <div className="profile-avatar">
          {inicial}
        </div>

        <h1 className="profile-title">Mi Perfil</h1>

        <div className="profile-info">

          {editando ? (
            <>
              <input
                name="nombre"
                value={form.nombre}
                onChange={handleChange}
                className="profile-input"
                placeholder="Nombre"
              />

              <input
                name="apellido"
                value={form.apellido}
                onChange={handleChange}
                className="profile-input"
                placeholder="Apellido"
              />

              <input
                name="correo"
                value={form.correo}
                onChange={handleChange}
                className="profile-input"
                placeholder="Correo"
              />
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
              <button className="profile-button" onClick={handleGuardar}>
                Guardar cambios
              </button>

              <button className="profile-button cancel" onClick={handleCancelar}>
                Cancelar
              </button>
            </>
          ) : (
            <button
              className="profile-button blue"
              onClick={iniciarEdicion}
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