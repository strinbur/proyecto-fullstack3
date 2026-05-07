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
      if (
        menuRef.current &&
        !menuRef.current.contains(event.target as Node)
      ) {
        setOpen(false);
      }
    };

    document.addEventListener("mousedown", handleClickOutside);
    return () =>
      document.removeEventListener("mousedown", handleClickOutside);
  }, []);

  return (
    <nav
      style={{
        display: "flex",
        justifyContent: "space-between",
        alignItems: "center",
        padding: "10px 20px",
        background: "#1f2937",
        color: "white",
      }}
    >
      {/* LOGO */}
      <Link to="/" style={{ color: "white", textDecoration: "none" }}>
        Grupo Cordillera
      </Link>

      {/* AVATAR + DROPDOWN */}
      <div style={{ position: "relative" }} ref={menuRef}>

        <div
          onClick={() => setOpen(!open)}
          style={{
            width: "40px",
            height: "40px",
            borderRadius: "50%",
            background: "#3b82f6",
            display: "flex",
            alignItems: "center",
            justifyContent: "center",
            cursor: "pointer",
            fontWeight: "bold",
          }}
        >
          {usuario ? inicial : "?"}
        </div>

        {open && (
          <div
            style={{
              position: "absolute",
              right: 0,
              top: "50px",
              background: "white",
              color: "black",
              borderRadius: "8px",
              boxShadow: "0 2px 10px rgba(0,0,0,0.2)",
              padding: "10px",
              minWidth: "150px",
              zIndex: 10,
            }}
          >
            {usuario ? (
              <>
                <Link
                  to="/"
                  onClick={() => setOpen(false)}
                  style={{ display: "block", marginBottom: "10px" }}
                >
                  Home
                </Link>

                <Link
                  to="/profile"
                  onClick={() => setOpen(false)}
                  style={{ display: "block", marginBottom: "10px" }}
                >
                  Mi perfil
                </Link>

                <button
                  onClick={() => {
                    logout?.();
                    setOpen(false);
                    window.location.href = "/";
                  }}
                  style={{
                    background: "red",
                    color: "white",
                    border: "none",
                    padding: "5px",
                    width: "100%",
                    cursor: "pointer",
                  }}
                >
                  Cerrar sesión
                </button>
              </>
            ) : (
              <>
                <Link
                  to="/login"
                  style={{ display: "block", marginBottom: "10px" }}
                  onClick={() => setOpen(false)}
                >
                  Iniciar sesión
                </Link>

                <Link
                  to="/register"
                  onClick={() => setOpen(false)}
                >
                  Registrarse
                </Link>
              </>
            )}
          </div>
        )}
      </div>
    </nav>
  );
}

export default Navbar;