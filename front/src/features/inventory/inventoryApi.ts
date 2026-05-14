import { api } from "../../api/api";

export const listarProductos = () => {
  return api.get("/inventory").then(res => res.data);
};

export const obtenerProducto = (codigo: string) => {
  return api.get(`/inventory/codigo/${codigo}`)
    .then(res => res.data);
};

export const crearProducto = (data: unknown) => {
  return api.post("/inventory", data)
    .then(res => res.data);
};

export const actualizarProducto = (
  codigo: string,
  data: unknown
) => {
  return api.put(`/inventory/codigo/${codigo}`, data)
    .then(res => res.data);
};

export const eliminarProducto = (codigo: string) => {
  return api.delete(`/inventory/codigo/${codigo}`);
};