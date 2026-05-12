import { createContext } from "react";

/* ==========================================================
   TIPADO DE USUARIO
   ========================================================== */
export interface Usuario {
  id: string;
  nombre: string;
  apellido: string;
  correo: string;
  rol?: string; // El rol es opcional por si el microservicio no lo envía en algún flujo
}

/* ==========================================================
   TIPADO DE RESPUESTA DE LOGIN (JWT + USER)
   ========================================================== */
export interface LoginData {
  token: string;
  usuario: Usuario;
}

/* ==========================================================
   DEFINICIÓN DEL CONTEXTO
   ========================================================== */
export interface AuthContextType {
  usuario: Usuario | null;
  // loginUser ahora recibe el objeto completo que viene del BFF
  loginUser: (data: LoginData) => void; 
  logout: () => void;
}

export const AuthContext = createContext<AuthContextType | null>(null);