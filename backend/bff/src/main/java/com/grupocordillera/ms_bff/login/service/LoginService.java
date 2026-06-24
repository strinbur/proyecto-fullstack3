package com.grupocordillera.ms_bff.login.service;

import com.grupocordillera.ms_bff.login.dto.*;

import java.util.List;

/**
 * Servicio del BFF responsable de operaciones de autenticación y gestión
 * de usuarios. Implementaciones delegan en un cliente Feign hacia `ms-login`.
 */
public interface LoginService {

    /**
     * Realiza la autenticación con las credenciales proporcionadas.
     */
    AuthResponseDTO login(LoginRequestDTO request);

    /**
     * Registra un cliente (rol por defecto) en el sistema.
     */
    LoginResponseDTO createClient(LoginRegisterDTO request);

    /**
     * Crea un usuario con rol específico (acción de administrador).
     */
    LoginResponseDTO createUser(LoginAdminCreateDTO request);

    /**
     * Actualiza los datos del usuario identificado por `id`.
     */
    LoginResponseDTO updateUser(String id, LoginUpdateDTO request);

    /**
     * Recupera la información de un usuario por su `id`.
     */
    LoginResponseDTO getUserById(String id);

    /**
     * Recupera todos los usuarios del sistema.
     */
    List<LoginResponseDTO> getAllUsers();

    /**
     * Elimina el usuario identificado por `id`.
     */
    void deleteUser(String id);
}