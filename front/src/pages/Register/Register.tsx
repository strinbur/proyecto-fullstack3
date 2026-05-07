import { useState } from "react";
import { register } from "../../features/auth/authApi";
import "./Register.css";

function Register() {
  const [nombre, setNombre] = useState("");
  const [apellido, setApellido] = useState("");
  const [correo, setCorreo] = useState("");
  const [password, setPassword] = useState("");

  const handleRegister = async () => {
    try {
      await register({
        nombre,
        apellido,
        correo,
        password,
      });

      alert("Usuario registrado correctamente");


      setNombre("");
      setApellido("");
      setCorreo("");
      setPassword("");

    } catch (error) {
      console.error(error);
      alert("Error al registrar usuario");
    }
  };

  return (
    <div className="register-container">
      <div className="register-card">

        <h1 className="register-title">Registro</h1>

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
            Registrar
          </button>

        </div>

      </div>
    </div>
  );
}

export default Register;