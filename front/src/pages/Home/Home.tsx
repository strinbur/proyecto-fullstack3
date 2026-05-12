import { useEffect, useState } from "react";

import { listarProductos } from "../../features/inventory/inventoryApi";

import "./Home.css";

/* =========================
   TIPADO
========================= */

type Producto = {
  _id: string;
  nombre: string;
  marca: string;
  precio: number;
  categoria: string;
};

function Home() {

  const [productos, setProductos] = useState<Producto[]>([]);

  /* =========================
     CARGAR PRODUCTOS
  ========================== */

  useEffect(() => {

    const fetchProductos = async () => {

      try {

        const data = await listarProductos();

        setProductos(data);

      } catch (error) {

        console.error(error);

      }

    };

    fetchProductos();

  }, []);

  return (

    <div className="home-page">

      {/* HERO */}

      <section className="hero">

        <div className="hero-content">

          <span>
            Tecnología • Smart TV • Gaming
          </span>

          <h1>
            Compra tecnología
            al mejor precio
          </h1>

          <p>
            Encuentra notebooks, televisores,
            accesorios y mucho más.
          </p>

          <button
            onClick={() =>
              window.location.href = "/products"
            }
          >
            Ver Productos
          </button>

        </div>

      </section>

      {/* PRODUCTOS */}

      <section className="home-products">

        <div className="section-title">

          <h2>
            Productos Destacados
          </h2>

          <p>
            Productos disponibles en tienda
          </p>

        </div>

        {/* GRID */}

        <div className="products-grid">

          {productos.map((product) => (

            <div
              className="product-card"
              key={product._id}
            >

              {/* IMAGEN */}

              <div className="product-image">

                <img
  src={
    product.nombre.toLowerCase().includes("lenovo")
      ? "https://encrypted-tbn2.gstatic.com/shopping?q=tbn:ANd9GcRf2zkLw7dKI0k6l_j2QMB90gC_-e3ufzbXBNLnW4ME485Q-Mo0N7gIyar_Z1N5UImnn-v3b22YGn3INjed5miTVBRkLVoOLuQAKoGKLZvHJZtGeb5dk1en6g"

      : product.nombre.toLowerCase().includes("samsung")
      ? "https://images.unsplash.com/photo-1593784991095-a205069470b6?q=80&w=1200&auto=format&fit=crop"

      : product.nombre.toLowerCase().includes("lg")
      ? "https://images.unsplash.com/photo-1461151304267-38535e780c79?q=80&w=1200&auto=format&fit=crop"

      : "https://images.unsplash.com/photo-1498049794561-7780e7231661?q=80&w=1200&auto=format&fit=crop"
  }
  alt={product.nombre}
/>

              </div>

              {/* INFO */}

              <div className="product-info">

                <span className="product-category">
                  {product.categoria}
                </span>

                <h3>
                  {product.nombre}
                </h3>

                <p>
                  {product.marca}
                </p>

                <div className="product-bottom">

                  <span className="product-price">

                    $
                    {product.precio.toLocaleString("es-CL")}

                  </span>

                  <button>
                    Ver
                  </button>

                </div>

              </div>

            </div>

          ))}

        </div>

      </section>

    </div>

  );

}

export default Home;