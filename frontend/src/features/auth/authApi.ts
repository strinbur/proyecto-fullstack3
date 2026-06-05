import { api } from "../../api/api";

// Login
export const login = (email: string, password: string) => {
  return api.post("/login", {
    email,
    password
  }).then(res => res.data);
};

// Register Cliente
export const createClient = (data: unknown) => {
  return api.post("/login/register", data)
    .then(res => res.data);
};

// Register Admin
export const createUser = (data: unknown) => {
  return api.post("/login/admin", data)
    .then(res => res.data);
};

// Update User
export const updateUser = (id: string, data: unknown) => {
  return api.put(`/login/${id}`, data)
    .then(res => res.data);
};

// Get All Users
export const getAllUsers = () => {
  return api.get("/login").then(res => res.data);
};

// Delete User
export const deleteUser = (id: string) => {
  return api.delete(`/login/${id}`)
    .then(res => res.data);
};