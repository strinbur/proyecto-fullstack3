import { api } from "../../api/api";

/* LOGIN */
export const login = (correo: string, password: string) => {
  return api.post("/login", { correo, password }).then(res => res.data);
};

/* REGISTER */
export const register = (data: unknown) => {
  return api.post("/login/register", data).then(res => res.data);
};

/* UPDATE */
export const updateUser = (id: string, data: unknown) => {
  return api.put(`/login/${id}`, data).then(res => res.data);
};