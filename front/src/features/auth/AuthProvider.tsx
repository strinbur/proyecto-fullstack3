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

  // LOGIN: Ahora recibe el objeto completo (token + usuario)
  const loginUser = (data: { token: string; usuario: Usuario }) => {
    localStorage.setItem("token", data.token); // Guardamos la llave
    localStorage.setItem("usuario", JSON.stringify(data.usuario)); // Guardamos los datos
    setUsuario(data.usuario);
  };

  const logout = () => {
    localStorage.removeItem("token");
    localStorage.removeItem("usuario");
    setUsuario(null);
  };

  return (
    <AuthContext.Provider value={{ usuario, loginUser, logout }}>
      {children}
    </AuthContext.Provider>
  );
}