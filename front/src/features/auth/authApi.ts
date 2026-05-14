import { api } from "../../api/api";

/* LOGIN */
export const login = (
  correo: string,
  password: string
) => {

  return api.post("/login", {
    correo,
    password
  }).then(res => res.data);

};

/* REGISTER */
export const register = (
  data: unknown
) => {

  return api.post("/login/register", data)
    .then(res => res.data);

};

/* CREAR USUARIO CON  ROL */
export const createUserWithRole = (
  data: unknown
) => {

  return api.post("/login/admin", data)
    .then(res => res.data);

};

/* UPDATE */
export const updateUser = (
  id: string,
  data: unknown
) => {

  return api.put(`/login/${id}`, data)
    .then(res => res.data);

};

/* GET ALL */
export const getUsers = () => {
  return api.get("/login").then(res => res.data);
};

/* DELETE */
export const deleteUser = (id: string) => {
  return api.delete(`/login/${id}`)
    .then(res => res.data);
};