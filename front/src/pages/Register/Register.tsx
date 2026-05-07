import { useState } from "react";
import toast from "react-hot-toast";
import { register } from "../../features/auth/authApi";
import "./Register.css";

function Register() {
  const [nombre, setNombre] = useState("");
  const [apellido, setApellido] = useState("");
  const [correo, setCorreo] = useState("");
  const [password, setPassword] = useState("");
  const [rol, setRol] = useState("CLIENTE"); // Estado inicial

  const handleRegister = async () => {
    try {
      // Enviamos el objeto con el ROL incluido
      await register({
        nombre,
        apellido,
        correo,
        password,
        rol: rol // Enviará "ADMIN" o "CLIENTE"
      });

      toast.success(`Usuario ${rol} registrado con éxito`);
      
      // Limpiar campos
      setNombre(""); setApellido(""); setCorreo(""); setPassword("");
    } catch (error) {
      console.error(error);
      toast.error("Error al registrar: verifica los datos");
    }
  };

  return (
    <div className="register-container">
      <div className="register-card" style={{ fontFamily: "'Outfit', sans-serif" }}>
        <h1 className="register-title">Crear Cuenta</h1>
        <div className="register-form">
          <input className="register-input" placeholder="Nombre" value={nombre} onChange={(e) => setNombre(e.target.value)} />
          <input className="register-input" placeholder="Apellido" value={apellido} onChange={(e) => setApellido(e.target.value)} />
          <input className="register-input" placeholder="Correo" value={correo} onChange={(e) => setCorreo(e.target.value)} />
          <input className="register-input" type="password" placeholder="Password" value={password} onChange={(e) => setPassword(e.target.value)} />
          
          {/* SELECTOR DE ROL */}
          <div style={{ textAlign: "left", marginBottom: "10px" }}>
            <label style={{ fontSize: "12px", color: "#64748b", fontWeight: "bold" }}>TIPO DE CUENTA:</label>
            <select 
              value={rol} 
              onChange={(e) => setRol(e.target.value)}
              style={{ width: "100%", padding: "10px", borderRadius: "8px", border: "1px solid #d1d5db", marginTop: "5px" }}
            >
              <option value="CLIENTE">Cliente Estándar</option>
              <option value="ADMIN">Administrador</option>
              <option value="VENTAS">Ventas</option>
            </select>
          </div>

          <button className="register-button" onClick={handleRegister}>
            Registrar Ahora
          </button>
        </div>
      </div>
    </div>
  );
}

export default Register;