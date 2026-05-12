import { Link } from "react-router-dom";
import "./Navbar.css";

function Navbar() {

  return (

    <nav className="navbar">

      {/* LOGO */}

      <div className="navbar-logo">

        <Link to="/">
          CordilleraShop
        </Link>

      </div>

      {/* LINKS */}

      <ul className="navbar-links">

        <li>

          <Link to="/">
            Inicio
          </Link>

        </li>

        <li>

          <Link to="/products">
            Productos
          </Link>

        </li>


        {/* ADMIN SOLO EN PC */}

        {window.innerWidth > 768 && (

          <li>

            <Link to="/dashboard">
              Administrador
            </Link>

          </li>

        )}

      </ul>

    </nav>

  );

}

export default Navbar;