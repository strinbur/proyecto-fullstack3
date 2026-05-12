import axios from "axios";

export const api = axios.create({
  baseURL: "http://localhost:8080/bff"
});

// Agregar el token a todas las peticiones automáticamente
api.interceptors.request.use((config) => {
  const token = localStorage.getItem("token");
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});