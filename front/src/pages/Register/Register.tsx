import { useState } from "react";
import toast from "react-hot-toast";
import axios from "axios";
import { register } from "../../features/auth/authApi";
import "./Register.css";

function Register() {
  const [nombre, setNombre] = useState("");
  const [apellido, setApellido] = useState("");
  const [correo, setCorreo] = useState("");
  const [password, setPassword] = useState("");

  const handleRegister = async () => {
    try {

      // =========================
      // VALIDACIONES FRONTEND
      // =========================

      if (!nombre.trim()) {
        toast.error("El nombre es obligatorio");
        return;
      }

      if (!apellido.trim()) {
        toast.error("El apellido es obligatorio");
        return;
      }

      if (!correo.trim()) {
        toast.error("El correo es obligatorio");
        return;
      }

      if (!correo.includes("@")) {
        toast.error("El correo no es válido");
        return;
      }

      if (!password.trim()) {
        toast.error("La contraseña es obligatoria");
        return;
      }

      // =========================
      // PETICIÓN
      // =========================

      await register({
        nombre,
        apellido,
        correo,
        password,
        rol: "CLIENTE"
      });

      toast.success("Usuario registrado con éxito");

      setNombre("");
      setApellido("");
      setCorreo("");
      setPassword("");

    } catch (error: unknown) {

      console.error(error);

      // =========================
      // ERRORES BACKEND
      // =========================

      if (axios.isAxiosError(error)) {

        const status = error.response?.status;
        const data = error.response?.data;

        const message =
          typeof data === "string"
            ? data
            : data?.message;

        // 🔥 CASO ESPECÍFICO: correo ya registrado
        if (
          status === 409 ||
          message?.toLowerCase().includes("correo")
        ) {
          toast.error("El correo ya está registrado");
          return;
        }

        toast.error(message || "Error al registrar usuario");
        return;
      }

      toast.error("Error inesperado");
    }
  };

  return (
    <div className="register-container">
      <div className="register-card" style={{ fontFamily: "'Outfit', sans-serif" }}>

        <h1 className="register-title">Crear Cuenta</h1>

        <div className="register-form">

          <input
            className="register-input"
            placeholder="Nombre"
            value={nombre}
            onChange={(e) => setNombre(e.target.value)}
          />

          <input
            className="register-input"
            placeholder="Apellido"
            value={apellido}
            onChange={(e) => setApellido(e.target.value)}
          />

          <input
            className="register-input"
            placeholder="Correo"
            value={correo}
            onChange={(e) => setCorreo(e.target.value)}
          />

          <input
            className="register-input"
            type="password"
            placeholder="Password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
          />

          <button className="register-button" onClick={handleRegister}>
            Registrar Ahora
          </button>

        </div>

      </div>
    </div>
  );
}

export default Register;