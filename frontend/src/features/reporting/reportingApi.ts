import { api } from "../../api/api";

export const getSalesSummary = () => {
  return api.get("/reports/sales-summary").then(res => res.data);
};

export const getTopProducts = (topN: number = 5) => {
  return api.get("/reports/top-products", { params: { topN } }).then(res => res.data);
};

export const getCriticalStock = () => {
  return api.get("/reports/critical-stock").then(res => res.data);
};

export const getHistory = () => {
  const token = localStorage.getItem("token");
  return api.get("/reports/history", {
    headers: { Authorization: `Bearer ${token}` }
  }).then(res => res.data);
};