import axios from "axios";

export const api = axios.create({
  baseURL: "http://localhost:8080/bff"
});

//Agregamos un interceptor para incluir el token JWT en cada solicitud
api.interceptors.request.use((config) => {

  const token = localStorage.getItem("token");

  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }

  return config;
});

//Manejo de errores globales, como token inválido o expirado
api.interceptors.response.use(

  (response) => response,

  (error) => {

    // TOKEN INVALIDO O EXPIRADO
    if (error.response?.status === 401) {

      localStorage.removeItem("token");
      localStorage.removeItem("user");

      window.location.href = "/login";
    }

    return Promise.reject(error);
  }
);