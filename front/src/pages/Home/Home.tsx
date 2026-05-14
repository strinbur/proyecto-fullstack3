import "./Home.css";
import { useNavigate } from "react-router-dom";

function Home() {

  const navigate = useNavigate();

  return (

    <div className="home-page">

      <section className="hero-section">

        <div className="hero-content">

          <span className="hero-badge">
            Tecnología Premium 2026
          </span>

          <h1>
            Descubre los mejores productos tecnológicos
          </h1>

          <p>
            Computadores, celulares, accesorios y mucho más
            con ofertas exclusivas para ti.
          </p>

          <div className="hero-buttons">

            <button
              className="primary-btn"
              onClick={() => navigate("/products")}
            >
              Comprar ahora
            </button>

            <button
              className="secondary-btn"
              onClick={() => navigate("/products")}
            >
              Ver catálogo
            </button>

          </div>

        </div>

        <div className="hero-image">

          <img
            src="https://images.unsplash.com/photo-1519389950473-47ba0277781c?q=80&w=1200&auto=format&fit=crop"
            alt="Tecnología"
          />

        </div>

      </section>

      <section className="categories-section">

        <div className="section-title">

          <h2>
            Categorías populares
          </h2>

          <p>
            Explora nuestros productos destacados
          </p>

        </div>

        <div className="categories-grid">

          <div
            className="category-card"
            onClick={() => navigate("/products")}
          >

            <img
              src="https://images.unsplash.com/photo-1496181133206-80ce9b88a853?q=80&w=1200&auto=format&fit=crop"
              alt="Laptops"
            />

            <div className="category-overlay">
              <h3>Laptops</h3>
            </div>

          </div>

          <div
            className="category-card"
            onClick={() => navigate("/products")}
          >

            <img
              src="https://images.unsplash.com/photo-1511707171634-5f897ff02aa9?q=80&w=1200&auto=format&fit=crop"
              alt="Smartphones"
            />

            <div className="category-overlay">
              <h3>Smartphones</h3>
            </div>

          </div>

          <div
            className="category-card"
            onClick={() => navigate("/products")}
          >

            <img
              src="https://images.unsplash.com/photo-1505740420928-5e560c06d30e?q=80&w=1200&auto=format&fit=crop"
              alt="Audífonos"
            />

            <div className="category-overlay">
              <h3>Audífonos</h3>
            </div>

          </div>

        </div>

      </section>

      <section className="offers-section">

        <div className="offers-content">

          <span>
            OFERTA ESPECIAL
          </span>

          <h2>
            Hasta 40% de descuento
          </h2>

          <p>
            Aprovecha nuestras ofertas exclusivas por tiempo limitado.
          </p>

          <button
            className="primary-btn"
            onClick={() => navigate("/products")}
          >
            Ver ofertas
          </button>

        </div>

      </section>

      <section className="features-section">

        <div className="feature-card">

          <h3>
            🚚 Envío Gratis
          </h3>

          <p>
            En compras superiores a $50.000
          </p>

        </div>

        <div className="feature-card">

          <h3>
            🔒 Pago Seguro
          </h3>

          <p>
            Protección total en tus compras
          </p>

        </div>

        <div className="feature-card">

          <h3>
            ⚡ Entrega Rápida
          </h3>

          <p>
            Recibe tus productos rápidamente
          </p>

        </div>

      </section>

    </div>

  );

}

export default Home;