import { useState, useContext } from "react";
import toast from "react-hot-toast";
import { login } from "../../features/auth/authApi";
import { AuthContext } from "../../features/auth/AuthContext";
import { useNavigate } from "react-router-dom";
import "./Login.css";

function Login() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const auth = useContext(AuthContext);
  const navigate = useNavigate();

  const handleLogin = async () => {
    try {
      const user = await login(email, password);

      auth?.loginUser(user);

      toast.success("Login correcto");

      navigate("/");
    } catch (error) {
      console.error(error);
      toast.error("Error");
    }
  };

  return (
    <div className="login-container">
      <div className="login-card">
        <h1 className="login-title">Iniciar Sesión</h1>

        <div className="login-form">
          <input
            className="login-input"
            placeholder="Correo"
            onChange={(e) => setEmail(e.target.value)}
          />

          <input
            className="login-input"
            placeholder="Password"
            type="password"
            onChange={(e) => setPassword(e.target.value)}
          />

          <button className="login-button" onClick={handleLogin}>
            Entrar
          </button>
        </div>
      </div>
    </div>
  );
}

export default Login;