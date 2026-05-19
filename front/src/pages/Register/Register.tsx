import { useState } from "react";
import toast from "react-hot-toast";
import axios from "axios";
import { createClient } from "../../features/auth/authApi";
import "./Register.css";

function Register() {
  const [name, setName] = useState("");
  const [lastname, setLastname] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const handleRegister = async () => {
    try {

      // Validaciones en el frontend

      if (!name.trim()) {
        toast.error("El nombre es obligatorio");
        return;
      }

      if (!lastname.trim()) {
        toast.error("El apellido es obligatorio");
        return;
      }

      if (!email.trim()) {
        toast.error("El correo es obligatorio");
        return;
      }

      if (!email.includes("@")) {
        toast.error("El correo no es válido");
        return;
      }

      if (!password.trim()) {
        toast.error("La contraseña es obligatoria");
        return;
      }

      // LLama a la api para crear el cliente
      await createClient({
        name,
        lastname,
        email,
        password
      });

      toast.success("Usuario registrado con éxito");

      setName("");
      setLastname("");
      setEmail("");
      setPassword("");

    } catch (error: unknown) {

      console.error(error);


      // Manejo de errores específicos de Axios
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
            value={name}
            onChange={(e) => setName(e.target.value)}
          />

          <input
            className="register-input"
            placeholder="Apellido"
            value={lastname}
            onChange={(e) => setLastname(e.target.value)}
          />

          <input
            className="register-input"
            placeholder="Correo"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
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