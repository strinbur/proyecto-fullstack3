import axios from "axios";

export const api = axios.create({
  baseURL: "http://localhost:8080/bff"
});

/* ==========================================================
   AGREGAR TOKEN JWT AUTOMATICAMENTE
   ========================================================== */
api.interceptors.request.use((config) => {

  const token = localStorage.getItem("token");

  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }

  return config;
});

/* ==========================================================
   MANEJO GLOBAL DE ERRORES JWT
   ========================================================== */
api.interceptors.response.use(

  (response) => response,

  (error) => {

    // TOKEN INVALIDO O EXPIRADO
    if (error.response?.status === 401) {

      localStorage.removeItem("token");
      localStorage.removeItem("usuario");

      window.location.href = "/login";
    }

    return Promise.reject(error);
  }
);