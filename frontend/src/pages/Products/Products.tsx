import { useContext, useEffect, useState } from "react";
import { getAllProducts } from "../../features/inventory/inventoryApi";
import { CartContext } from "../../features/cart/CartContext";
import { formatCurrency } from "../../utils/format";
import "./Products.css";

interface Product {
  id: string;
  code: string;
  name: string;
  brand: string;
  price: number;
  quantity: number;
  category: string;
}

export default function Products() {

  const [products, setProducts] = useState<Product[]>([]);
  const [selectedProduct, setSelectedProduct] = useState<Product | null>(null);
  const [quantity, setQuantity] = useState<number>(1);
  const [stockError, setStockError] = useState("");
  const [statusMessage, setStatusMessage] = useState("");
  const { addToCart, items } = useContext(CartContext);

  useEffect(() => {

    const fetchProducts = async () => {

      try {

        const data = await getAllProducts();
        setProducts(data);

      } catch (error) {

        console.error("Error cargando productos", error);

      }

    };

    fetchProducts();

  }, []);

  const getProductImage = (name: string) => {

    const productName = name.toLowerCase();

    if (productName.includes("lenovo")) {

      return "https://images.unsplash.com/photo-1496181133206-80ce9b88a853?q=80&w=1200&auto=format&fit=crop";

    }

    if (productName.includes("samsung")) {

      return "https://images.unsplash.com/photo-1593784991095-a205069470b6?q=80&w=1200&auto=format&fit=crop";

    }

    if (productName.includes("lg")) {

      return "https://images.unsplash.com/photo-1461151304267-38535e780c79?q=80&w=1200&auto=format&fit=crop";

    }

    return "https://placehold.co/600x400";

  };

  const openProductDetail = (product: Product) => {

    setSelectedProduct(product);
    setQuantity(1);
    setStockError("");

  };

  const handleQuantityChange = (value: number) => {

    if (!selectedProduct) return;

    if (value > selectedProduct.quantity) {

      setStockError(`Solo hay ${selectedProduct.quantity} unidades disponibles`);
      return;

    }

    if (value < 1) {

      setQuantity(1);
      return;

    }

    setStockError("");
    setQuantity(value);

  };

  const handleAddToCart = (product: Product, quantityToAdd: number) => {
    if (quantityToAdd < 1) return;

    const currentCartItem = items.find((item) => item.code === product.code);
    const currentCartQuantity = currentCartItem?.quantity ?? 0;
    const allowedQuantity = product.quantity - currentCartQuantity;

    if (allowedQuantity <= 0) {
      setStockError("No hay unidades disponibles para este producto");
      setStatusMessage("");
      return;
    }

    if (quantityToAdd > allowedQuantity) {
      setStockError(`Solo hay ${allowedQuantity} unidades disponibles en stock`);
      setStatusMessage("");
      return;
    }

    addToCart(
      {
        id: product.id,
        code: product.code,
        name: product.name,
        brand: product.brand,
        price: product.price,
        category: product.category,
        imageUrl: getProductImage(product.name),
        stock: product.quantity,
      },
      quantityToAdd
    );

    setStatusMessage(`${quantityToAdd} producto${quantityToAdd > 1 ? "s" : ""} agregado${quantityToAdd > 1 ? "n" : ""} al carrito`);
    setStockError("");
    setSelectedProduct(null);
    setQuantity(1);
  };

  return (

    <div className="products-page">

      <div className="products-container">

        <div className="products-header">

          <div>

            <h1>Productos</h1>

            <p>
              Encuentra los mejores productos disponibles
            </p>

          </div>

          <button className="products-btn">
            Ver ofertas
          </button>

        </div>

        {statusMessage && (
          <div className="status-alert page-alert">
            {statusMessage}
          </div>
        )}

        <div className="products-grid">

          {products.map((product) => (

            <div
              key={product.id}
              className="product-card"
              onClick={() => openProductDetail(product)}
            >

              <div className="product-image">

                <img
                  src={getProductImage(product.name)}
                  alt={product.name}
                />

                <span className="product-category">
                  {product.category}
                </span>

              </div>

              <div className="product-content">

                <h2>{product.name}</h2>

                <p className="product-brand">
                  {product.brand}
                </p>

                <div className="product-info">

                  <p>
                    Código: {product.code}
                  </p>

                  <p>
                    Stock: {product.quantity}
                  </p>

                </div>

                <div className="product-footer">

                  <span className="product-price">
                    {formatCurrency(product.price)}
                  </span>

                  {product.quantity === 0 ? (
                    <button className="buy-btn" disabled>
                      Producto agotado
                    </button>
                  ) : (
                    <button
                      className="buy-btn"
                      onClick={(e) => {
                        e.stopPropagation();
                        handleAddToCart(product, 1);
                      }}
                    >
                      Añadir al carrito
                    </button>
                  )}

                </div>

              </div>

            </div>

          ))}

        </div>

      </div>

      {selectedProduct && (

        <div
          className="modal-overlay"
          onClick={() => setSelectedProduct(null)}
        >

          <div
            className="modal-content"
            onClick={(e) => e.stopPropagation()}
          >

            <button
              className="close-btn"
              onClick={() => setSelectedProduct(null)}
            >
              ✕
            </button>

            <img
              src={getProductImage(selectedProduct.name)}
              alt={selectedProduct.name}
              className="modal-image"
            />

            <div className="modal-info">

              <span className="modal-category">
                {selectedProduct.category}
              </span>

              <h2>{selectedProduct.name}</h2>

              <p>Marca: {selectedProduct.brand}</p>

              <p>Código: {selectedProduct.code}</p>

              <p>Stock disponible: {selectedProduct.quantity}</p>

              <h3>{formatCurrency(selectedProduct.price)}</h3>

              <div className="quantity-container">

                <label>Cantidad</label>

                <input
                  type="number"
                  value={quantity}
                  min={1}
                  onChange={(e) =>
                    handleQuantityChange(Number(e.target.value))
                  }
                  className="quantity-input"
                  disabled={selectedProduct.quantity === 0}
                />

              </div>

              {stockError && (
                <div className="stock-alert">
                  {stockError}
                </div>
              )}

              {statusMessage && (
                <div className="status-alert">
                  {statusMessage}
                </div>
              )}

              <button
                className="modal-buy-btn"
                onClick={() => handleAddToCart(selectedProduct, quantity)}
                disabled={selectedProduct.quantity === 0}
              >
                {selectedProduct.quantity === 0
                  ? "Producto agotado"
                  : `Añadir ${quantity} producto${quantity > 1 ? "s" : ""} al carrito`}
              </button>

            </div>

          </div>

        </div>

      )}

    </div>

  );

}