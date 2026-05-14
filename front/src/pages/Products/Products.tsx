import { useEffect, useState } from "react";
import { listarProductos } from "../../features/inventory/inventoryApi";
import "./Products.css";

interface Product {
  id: string;
  codigo: string;
  nombre: string;
  marca: string;
  precio: number;
  cantidad: number;
  categoria: string;
}

export default function Products() {

  const [products, setProducts] = useState<Product[]>([]);

  useEffect(() => {

    const fetchProductos = async () => {

      try {

        const data = await listarProductos();
        setProducts(data);

      } catch (error) {

        console.error("Error cargando productos", error);

      }

    };

    fetchProductos();

  }, []);

  return (

    <div className="min-h-screen bg-gray-100 p-8">

      <h1 className="text-3xl font-bold mb-6 text-gray-800">
        Productos
      </h1>

      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">

        {products.map((product) => (

          <div
            key={product.id}
            className="bg-white rounded-xl shadow-md p-5"
          >

            <h2 className="text-xl font-semibold text-gray-800">
              {product.nombre}
            </h2>

            <p className="text-gray-500">
              Código: {product.codigo}
            </p>

            <p className="text-gray-500">
              Marca: {product.marca}
            </p>

            <p className="text-blue-600 font-bold text-lg mt-2">
              ${product.precio}
            </p>

            <p className="text-gray-600">
              Stock: {product.cantidad}
            </p>

            <span className="inline-block mt-3 bg-blue-100 text-blue-700 px-3 py-1 rounded-full text-sm">
              {product.categoria}
            </span>

          </div>

        ))}

      </div>

    </div>

  );

}