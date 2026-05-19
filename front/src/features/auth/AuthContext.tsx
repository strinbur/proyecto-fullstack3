import { createContext } from "react";

// El tipo de datos que representa a un usuario autenticado
export interface User {
  id: string;
  name: string;
  lastname: string;
  email: string;
  role?: string;
}

// El tipo de datos que se espera recibir al hacer login
export interface LoginData {
  token: string;
  user: User;
}

// El tipo de datos que representa el contexto de autenticación
export interface AuthContextType {
  user: User | null;
  loginUser: (data: LoginData) => void;
  logout: () => void;
}

export const AuthContext = createContext<AuthContextType | null>(null);