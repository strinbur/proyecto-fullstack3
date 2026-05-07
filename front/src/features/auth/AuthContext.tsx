import { createContext } from "react";

export interface Usuario {
  id: string;
  nombre: string;
  apellido: string;
  correo: string;
  rol?: string;
}

export interface AuthContextType {
  usuario: Usuario | null;
  loginUser: (user: Usuario) => void;
  logout: () => void;
}

export const AuthContext = createContext<AuthContextType | null>(null);