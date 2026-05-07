import { useState } from "react";
import type { ReactNode } from "react";
import { AuthContext } from "./AuthContext";
import type { Usuario } from "./AuthContext";


interface Props {
  children: ReactNode;
}

export function AuthProvider({ children }: Props) {
  const [usuario, setUsuario] = useState<Usuario | null>(() => {
    const data = localStorage.getItem("usuario");
    return data ? JSON.parse(data) : null;
  });

  // LOGIN
  const loginUser = (user: Usuario) => {
    localStorage.setItem("usuario", JSON.stringify(user));
    setUsuario(user);
  };

  // LOGOUT
  const logout = () => {
    localStorage.removeItem("usuario");
    setUsuario(null);
  };

  return (
    <AuthContext.Provider value={{ usuario, loginUser, logout }}>
      {children}
    </AuthContext.Provider>
  );
}