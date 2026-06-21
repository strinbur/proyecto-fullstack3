import { api } from "../../api/api";

export const createOrder = (userEmail: string) => {
  return api.post("/orders", null, {
    headers: { userEmail }
  }).then(res => res.data);
};

export const getOrdersByUser = (email: string) => {
  return api.get(`/orders/user/${email}`).then(res => res.data);
};

export const getOrderById = (id: string) => {
  return api.get(`/orders/${id}`).then(res => res.data);
};

export const getAllOrders = () => {
  return api.get("/orders").then(res => res.data);
};

export const getOrdersByStatus = (status: string) => {
  return api.get(`/orders/status/${status}`).then(res => res.data);
};