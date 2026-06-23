import { useContext, useEffect, useState } from "react";
import { getAllProducts, updateProduct } from "../../features/inventory/inventoryApi";
import { CartContext } from "../../features/cart/CartContext";
import { formatCurrency } from "../../utils/format";
import "./Products.css";
import { AuthContext } from "../../features/auth/AuthContext";

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
  const auth = useContext(AuthContext);
  const [adminQuantity, setAdminQuantity] = useState<number | null>(null);
  const [selectedCategory, setSelectedCategory] = useState<string | null>(null);
  const [expandedProductCode, setExpandedProductCode] = useState<string | null>(null);
  const categoryFilters = ["tecnologia", "television", "celulares", "audio"];

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

  const productImageLinks: Record<string, string> = {
    "lenovo ideapad": "https://p1-ofp.static.pub//fes/cms/2025/01/31/42vzg5361kb0ao7n2m8c86tgsd822y027827.jpg",
    "smart tv samsung 55": "https://media.falabella.com/falabellaCL/80144424_1/w=1200,h=1200,fit=pad",
    "tv lg 50": "https://www.lg.com/content/dam/channel/wcms/cl/ms-2026/npi-2026/QNED/QNED70BSA/50qned70bsa-awh/GALLERY/2010X1334/50QNED70_1.jpg/jcr:content/renditions/thum-1600x1062.jpeg?w=800",
    "televisor lg 50": "https://www.lg.com/cl/televisores",
    "dell inspiron 14": "https://media.falabella.com/falabellaCL/145245395_01/w=1500,h=1500,fit=cover",
    "lenovo legion 5": "https://lckmqxkcmqtobflmrcqc.supabase.co/storage/v1/object/public/product-files/products/4a2565d3-dc87-41dc-98a6-faaa6baafd5e/main-1780674603363.avif",
    "macbook air m2": "https://res.cloudinary.com/djx6viedj/image/upload/t_trimmed_square_2048/5j1aadrsi49x1hxrcs756l2qs6t8?_a=BACCd2Ev",
    "iphone 15": "https://media.falabella.com/falabellaCL/16907118_1/w=1500,h=1500,fit=cover",
    "samsung galaxy s24": "https://encrypted-tbn2.gstatic.com/shopping?q=tbn:ANd9GcSNFioJKt4FW4wt4kEPV-VpmTueMkLmZVzpy-pnGBOB0pQ66xy6HZxl9RrixOr61pXUur_kbWJOUAAux_9lKYEYaa5SjdQtIAb5jUa81VD-s8Qm_aANGqIP0g",
    "xiaomi 13 pro": "https://http2.mlstatic.com/D_Q_NP_600660-MLU78185430205_082024-F.webp",
    "google pixel 8": "https://media.solotodo.com/media/products/1854074_picture_1702613624.jpg",
    "sony wh-1000xm5": "https://http2.mlstatic.com/D_Q_NP_867545-CBT110551065071_042026-F.webp",
    "bose quietcomfort ultra": "https://media.falabella.com/falabellaCL/138928337_01/w=1500,h=1500,fit=cover",
    "apple airpods max": "https://d1aqw5mz0wngqe.cloudfront.net/images/spree/images/2471707/attachments/large/Apple_AirPods_Max_2024_Basalt_Black_01.jpg?1727278622",
  };

  const getProductImage = (name: string) => {
    const productName = name.toLowerCase();

    for (const keyword of Object.keys(productImageLinks)) {
      if (productName.includes(keyword)) {
        return productImageLinks[keyword];
      }
    }

    return "https://placehold.co/600x400";
  };

  const getCategoryLabel = (category: string, name: string) => {
    const normalized = category.toLowerCase();

    if (normalized === "tecnologia") {
      return "Notebook";
    }

    if (normalized === "television") {
      return "Televisión";
    }

    if (normalized === "celulares") {
      return "Celulares";
    }

    if (normalized === "audio") {
      return "Audio";
    }

    return category.charAt(0).toUpperCase() + category.slice(1);
  };

  const filteredProducts = selectedCategory
    ? products.filter(
        (product) => product.category.toLowerCase() === selectedCategory
      )
    : products;

  const openProductDetail = (product: Product) => {

    setSelectedProduct(product);
    setExpandedProductCode(product.code);
    setQuantity(1);
    setStockError("");
    setAdminQuantity(product.quantity);

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

  const handleAdminUpdate = async () => {
    if (!selectedProduct || adminQuantity == null) return;

    try {
      const payload = {
        name: selectedProduct.name,
        brand: selectedProduct.brand,
        price: selectedProduct.price,
        quantity: adminQuantity,
        category: selectedProduct.category,
        specs: {}
      };

      await updateProduct(selectedProduct.code, payload);

      setStatusMessage("Stock actualizado correctamente");
      setSelectedProduct(null);
      // refresh products
      const data = await getAllProducts();
      setProducts(data);
    } catch (err) {
      console.error("Error actualizando stock", err);
      setStatusMessage("Error actualizando stock");
    }
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

        </div>

        <div className="category-filter-bar">
          <span className="category-filter-label">Categoría:</span>
          <button
            key="all"
            className={`category-filter-btn ${selectedCategory === null ? "active" : ""}`}
            onClick={() => setSelectedCategory(null)}
          >
            Todos los productos
          </button>
          {categoryFilters.map((category) => (
            <button
              key={category}
              className={`category-filter-btn ${
                selectedCategory === category ? "active" : ""
              }`}
              onClick={() =>
                setSelectedCategory(
                  selectedCategory === category ? null : category
                )
              }
            >
              {getCategoryLabel(category, "")}
            </button>
          ))}
        </div>

        {statusMessage && (
          <div className="status-alert page-alert">
            {statusMessage}
          </div>
        )}

        <div className="products-grid">

          {filteredProducts.map((product) => (

            <div
              key={product.id}
              className={`product-card ${
                expandedProductCode === product.code ? "expanded" : ""
              }`}
              onClick={() => openProductDetail(product)}
            >

              <div className="product-image">

                <img
                  src={getProductImage(product.name)}
                  alt={product.name}
                />

                <span className="product-category">
                  {getCategoryLabel(product.category, product.name)}
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
                {getCategoryLabel(selectedProduct.category, selectedProduct.name)}
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

              {/* Keep buy button available for all users (including ADMIN) */}
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