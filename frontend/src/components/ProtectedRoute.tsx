import { Navigate } from "react-router-dom";
import { useContext } from "react";
import type { ReactNode } from "react";
import { AuthContext } from "../features/auth/AuthContext";

interface Props {
  children: ReactNode;
}

function ProtectedRoute({ children }: Props) {
  const auth = useContext(AuthContext);
  const usuario = auth?.user;

  if (!usuario) {
    return <Navigate to="/login" replace />;
  }

  return <>{children}</>;
}

export default ProtectedRoute;