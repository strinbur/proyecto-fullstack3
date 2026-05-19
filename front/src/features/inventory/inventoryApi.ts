import { api } from "../../api/api";

export const getAllProducts = () => {
  return api.get("/inventory").then(res => res.data);
};

export const getProductByCode = (code: string) => {
  return api.get(`/inventory/code/${code}`).then(res => res.data);
};

export const createProduct = (data: unknown) => {
  return api.post("/inventory", data).then(res => res.data);
};

export const updateProduct = (code: string, data: unknown) => {
  return api.put(`/inventory/code/${code}`, data).then(res => res.data);
};

export const deleteProduct = (code: string) => {
  return api.delete(`/inventory/code/${code}`);
};