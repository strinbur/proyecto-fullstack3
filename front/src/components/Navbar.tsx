import { Link } from "react-router-dom";
import { useState, useRef, useEffect, useContext } from "react";
import { AuthContext } from "../features/auth/AuthContext";

function Navbar() {
  const auth = useContext(AuthContext);
  const usuario = auth?.usuario;
  const logout = auth?.logout;

  const [open, setOpen] = useState(false);
  const menuRef = useRef<HTMLDivElement | null>(null);

  const inicial = usuario?.nombre?.charAt(0).toUpperCase();

  useEffect(() => {
    const handleClickOutside = (event: MouseEvent) => {
      if (menuRef.current && !menuRef.current.contains(event.target as Node)) {
        setOpen(false);
      }
    };
    document.addEventListener("mousedown", handleClickOutside);
    return () => document.removeEventListener("mousedown", handleClickOutside);
  }, []);

  return (
    <nav style={{
      display: "flex", justifyContent: "space-between", alignItems: "center",
      padding: "15px 30px", background: "linear-gradient(90deg, #1e3a8a, #2563eb)",
      color: "white", boxShadow: "0 4px 12px rgba(0,0,0,0.15)", fontFamily: "'Outfit', sans-serif"
    }}>
      <div style={{ display: "flex", alignItems: "center", gap: "35px" }}>
        <Link to="/" style={{ color: "white", textDecoration: "none", fontSize: "22px", fontWeight: "800" }}>
          GRUPO CORDILLERA
        </Link>

        <div style={{ display: "flex", gap: "25px" }}>
          <Link to="/" style={{ color: "white", textDecoration: "none", fontWeight: "500" }}>Inicio</Link>
          
          {/* 🛡️ SOLO APARECE SI ES ADMIN */}
          {usuario?.rol === "ADMIN" && (
            <Link to="/dashboard" style={{
              color: "#fde047", textDecoration: "none", fontWeight: "bold",
              border: "1px solid #fde047", padding: "2px 8px", borderRadius: "6px"
            }}>
              🚀 Dashboard Admin
            </Link>
          )}
        </div>
      </div>

      <div style={{ position: "relative" }} ref={menuRef}>
        <div onClick={() => setOpen(!open)} style={{
          width: "42px", height: "42px", borderRadius: "12px", background: "#60a5fa",
          display: "flex", alignItems: "center", justifyContent: "center",
          cursor: "pointer", fontWeight: "bold", border: "2px solid white"
        }}>
          {usuario ? inicial : "?"}
        </div>

        {open && (
          <div style={{
            position: "absolute", right: 0, top: "55px", background: "white",
            color: "#1e293b", borderRadius: "12px", boxShadow: "0 10px 25px rgba(0,0,0,0.2)",
            padding: "15px", minWidth: "200px", zIndex: 100
          }}>
            {usuario ? (
              <>
                <p style={{ margin: "0 0 10px 0", fontSize: "14px", color: "#64748b" }}>Bienvenido,</p>
                <p style={{ margin: "0 0 15px 0", fontWeight: "bold", color: "#1e3a8a" }}>{usuario.nombre} ({usuario.rol})</p>
                <hr style={{ border: "0.5px solid #f1f5f9", marginBottom: "15px" }} />
                <button onClick={() => { logout?.(); setOpen(false); window.location.href = "/"; }}
                  style={{ background: "#ef4444", color: "white", border: "none", padding: "10px", width: "100%", borderRadius: "8px", cursor: "pointer", fontWeight: "600" }}>
                  Cerrar Sesión
                </button>
              </>
            ) : (
              <div style={{ display: "flex", flexDirection: "column", gap: "10px" }}>
                <Link to="/login" onClick={() => setOpen(false)} style={{ textDecoration: "none", color: "#1e3a8a", fontWeight: "600" }}>Iniciar Sesión</Link>
                <Link to="/register" onClick={() => setOpen(false)} style={{ textDecoration: "none", color: "#1e3a8a", fontWeight: "600" }}>Registrarse</Link>
              </div>
            )}
          </div>
        )}
      </div>
    </nav>
  );
}
export default Navbar;