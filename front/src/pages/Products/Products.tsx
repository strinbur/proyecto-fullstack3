import { useState } from 'react'
import './Products.css'

function Products() {

  const [selectedProduct, setSelectedProduct] = useState<any>(null)

  const products = [
    {
      id: 1,
      nombre: 'Notebook Lenovo IdeaPad',
      marca: 'Lenovo',
      precio: 799990,
      categoria: 'Tecnología',
      descripcion:
        'Notebook ideal para gaming y productividad con excelente rendimiento.',
      detalles: [
        'Intel Core i7',
        '16GB RAM',
        'RTX 4060',
        'SSD 1TB'
      ],
      imagen:
        'https://images.unsplash.com/photo-1593642702821-c8da6771f0c6'
    },

    {
      id: 2,
      nombre: 'Smart TV Samsung',
      marca: 'Samsung',
      precio: 599990,
      categoria: 'Televisión',
      descripcion:
        'Smart TV 4K con colores vibrantes y sistema operativo inteligente.',
      detalles: [
        '55 pulgadas',
        '4K UHD',
        'HDR',
        'Netflix integrado'
      ],
      imagen:
        'https://images.unsplash.com/photo-1593784991095-a205069470b6'
    },

    {
      id: 3,
      nombre: 'TV LG',
      marca: 'LG',
      precio: 449990,
      categoria: 'Televisión',
      descripcion:
        'Televisor LG con excelente calidad de imagen y sonido envolvente.',
      detalles: [
        '50 pulgadas',
        'WebOS',
        'Full HD',
        'Bluetooth'
      ],
      imagen:
        'https://images.unsplash.com/photo-1461151304267-38535e780c79'
    }
  ]

  return (
    <div className="products-page">

      <div className="products-header">

        <div>
          <h1>Todos los productos</h1>

          <p>
            Descubre tecnología, televisores y más.
          </p>
        </div>

      </div>

      <div className="products-grid">

        {products.map((product) => (

          <div
            className="product-card"
            key={product.id}
            onClick={() => setSelectedProduct(product)}
          >

            <img
              src={product.imagen}
              alt={product.nombre}
            />

            <div className="product-info">

              <span className="product-category">
                {product.categoria}
              </span>

              <h3>{product.nombre}</h3>

              <p>{product.marca}</p>

              <div className="price-row">

                <span className="price">
                  ${product.precio.toLocaleString('es-CL')}
                </span>

                <button className="add-cart">
                  Agregar
                </button>

              </div>

            </div>

          </div>

        ))}

      </div>

      {/* MODAL */}

      {selectedProduct && (

        <div
          className="modal-overlay"
          onClick={() => setSelectedProduct(null)}
        >

          <div
            className="product-modal"
            onClick={(e) => e.stopPropagation()}
          >

            <button
              className="close-modal"
              onClick={() => setSelectedProduct(null)}
            >
              ✕
            </button>

            <img
              src={selectedProduct.imagen}
              alt={selectedProduct.nombre}
            />

            <div className="modal-info">

              <span className="product-category">
                {selectedProduct.categoria}
              </span>

              <h2>{selectedProduct.nombre}</h2>

              <h3>
                ${selectedProduct.precio.toLocaleString('es-CL')}
              </h3>

              <p>
                {selectedProduct.descripcion}
              </p>

              <ul>

                {selectedProduct.detalles.map(
                  (detalle: string, index: number) => (

                    <li key={index}>
                      {detalle}
                    </li>

                  )
                )}

              </ul>

              <button className="buy-btn">
                Agregar al carrito
              </button>

            </div>

          </div>

        </div>

      )}

    </div>
  )
}

export default Products