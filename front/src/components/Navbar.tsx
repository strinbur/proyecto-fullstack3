import "./Navbar.css";

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

    <nav className="navbar">

      <div className="navbar-left">

        <Link to="/" className="navbar-logo">
          GRUPO CORDILLERA
        </Link>

        <div className="navbar-links">

          <Link to="/" className="navbar-link">
            Inicio
          </Link>

          <Link to="/products" className="navbar-link">
            Productos
          </Link>

          {usuario && (

            <Link to="/profile" className="navbar-link">
              Perfil
            </Link>

          )}

          {usuario?.rol === "ADMIN" && (

            <Link
              to="/dashboard"
              className="navbar-admin-link"
            >
              Administracion
            </Link>

          )}

        </div>

      </div>

      <div
        className="navbar-profile-wrapper"
        ref={menuRef}
      >

        <div
          className="navbar-avatar"
          onClick={() => setOpen(!open)}
        >
          {usuario ? inicial : "?"}
        </div>

        {open && (

          <div className="navbar-dropdown">

            {usuario ? (

              <>

                <p className="dropdown-label">
                  Bienvenido,
                </p>

                <p className="dropdown-user">
                  {usuario.nombre} ({usuario.rol})
                </p>

                <hr className="dropdown-divider" />

                <Link
                  to="/profile"
                  onClick={() => setOpen(false)}
                  className="dropdown-link"
                >
                  Mi Perfil
                </Link>

                <button
                  className="logout-button"
                  onClick={() => {
                    logout?.();
                    setOpen(false);
                    window.location.href = "/";
                  }}
                >
                  Cerrar Sesión
                </button>

              </>

            ) : (

              <div className="dropdown-auth-links">

                <Link
                  to="/login"
                  onClick={() => setOpen(false)}
                  className="dropdown-link"
                >
                  Iniciar Sesión
                </Link>

                <Link
                  to="/register"
                  onClick={() => setOpen(false)}
                  className="dropdown-link"
                >
                  Registrarse
                </Link>

              </div>

            )}

          </div>

        )}

      </div>

    </nav>

  );

}

export default Navbar;