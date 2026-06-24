package com.grupocordillera.ms_login.service;

import com.grupocordillera.ms_login.dto.AuthResponseDTO;
import com.grupocordillera.ms_login.dto.CreateUserDTO;
import com.grupocordillera.ms_login.dto.LoginResponseDTO;
import com.grupocordillera.ms_login.dto.LoginUpdateDTO;
import com.grupocordillera.ms_login.dto.RegisterDTO;

import java.util.List;

/**
 * Servicio para la lógica de negocio de autenticación y administración de usuarios.
 */
public interface LoginService {

    /**
     * Registra un nuevo usuario con rol {@code CLIENTE}.
     *
     * @param dto datos de registro del usuario
     * @return usuario creado
     */
    LoginResponseDTO createClient(RegisterDTO dto);

    /**
     * Autentica a un usuario a partir de su correo y contraseña.
     *
     * @param email    correo electrónico del usuario
     * @param password contraseña del usuario
     * @return token de autenticación y datos de usuario
     */
    AuthResponseDTO login(String email, String password);

    /**
     * Recupera todos los usuarios registrados.
     *
     * @return lista de usuarios
     */
    List<LoginResponseDTO> getAllUsers();

    /**
     * Recupera un usuario por su identificador.
     *
     * @param id identificador del usuario
     * @return usuario encontrado
     */
    LoginResponseDTO getUserById(String id);

    /**
     * Crea un usuario con un rol explícito.
     *
     * @param dto datos de creación del usuario
     * @return usuario creado
     */
    LoginResponseDTO createUser(CreateUserDTO dto);

    /**
     * Actualiza los datos de un usuario existente.
     *
     * @param id  identificador del usuario
     * @param dto datos de actualización
     * @return usuario actualizado
     */
    LoginResponseDTO updateUser(String id, LoginUpdateDTO dto);

    /**
     * Elimina un usuario por su identificador.
     *
     * @param id identificador del usuario
     */
    void deleteUser(String id);
}