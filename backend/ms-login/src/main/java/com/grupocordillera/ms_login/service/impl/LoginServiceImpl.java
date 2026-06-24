package com.grupocordillera.ms_login.service.impl;

import com.grupocordillera.ms_login.config.security.JwtService;
import com.grupocordillera.ms_login.dto.*;
import com.grupocordillera.ms_login.exception.LoginException;
import com.grupocordillera.ms_login.model.Login;
import com.grupocordillera.ms_login.model.Rol;
import com.grupocordillera.ms_login.repository.LoginRepository;
import com.grupocordillera.ms_login.service.LoginService;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementación del servicio de login que maneja autenticación y administración de usuarios.
 */
@Service
public class LoginServiceImpl implements LoginService {

    private final LoginRepository repository;
    private final JwtService jwtService;

    public LoginServiceImpl(
            LoginRepository repository,
            JwtService jwtService
    ) {
        this.repository = repository;
        this.jwtService = jwtService;
    }

    /**
     * Convierte una entidad {@link Login} en un {@link LoginResponseDTO}.
     *
     * @param login entidad de usuario
     * @return DTO con los datos del usuario
     */
    private LoginResponseDTO toDTO(Login login) {

        LoginResponseDTO dto =
                new LoginResponseDTO();

        dto.setId(login.getId());
        dto.setName(login.getName());
        dto.setLastname(login.getLastname());
        dto.setEmail(login.getEmail());
        dto.setRole(login.getRole());

        return dto;
    }

    /**
     * Registra un cliente nuevo con rol {@link Rol#CLIENTE}.
     *
     * @param dto información de registro del cliente
     * @return datos del cliente creado
     */
    @Override
    public LoginResponseDTO createClient(
            RegisterDTO dto
    ) {

        if (
                repository.findByEmail(dto.getEmail())
                        .isPresent()
        ) {

            throw new LoginException(
                    "El correo ya esta registrado"
            );
        }

        Login login = new Login();

        login.setName(dto.getName());
        login.setLastname(dto.getLastname());
        login.setEmail(dto.getEmail());
        login.setPassword(dto.getPassword());
        login.setRole(Rol.CLIENTE);

        return toDTO(
                repository.save(login)
        );
    }

/**
     * Autentica a un usuario y genera un token JWT.
     *
     * @param email    correo electrónico del usuario
     * @param password contraseña del usuario
     * @return respuesta de autenticación con token y datos del usuario
     */
    @Override
    public AuthResponseDTO login(
            String email,
            String password
    ) {

        Login user =
                repository.findByEmail(email)
                        .orElseThrow(() ->
                                new LoginException(
                                        "Usuario no encontrado"
                                )
                        );

        if (
                !user.getPassword()
                        .equals(password)
        ) {

            throw new LoginException(
                    "Contraseña incorrecta"
            );
        }

        String token =
                jwtService.generateToken(user);

        AuthResponseDTO response =
                new AuthResponseDTO();

        response.setToken(token);
        response.setUser(toDTO(user));

        return response;
    }

/**
     * Recupera todos los usuarios registrados.
     *
     * @return lista de usuarios
     */
    @Override
    public List<LoginResponseDTO> getAllUsers() {

        return repository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Recupera un usuario por su identificador.
     *
     * @param id identificador del usuario
     * @return usuario encontrado
     */
    @Override
    public LoginResponseDTO getUserById(
            String id
    ) {

        return repository.findById(id)
                .map(this::toDTO)
                .orElseThrow(() ->
                        new LoginException(
                                "Usuario no encontrado"
                        )
                );
    }

/**
     * Crea un usuario con el rol especificado en el DTO.
     *
     * @param dto información de creación del usuario
     * @return usuario creado
     */
    @Override
    public LoginResponseDTO createUser(
            CreateUserDTO dto
    ) {

        if (
                repository.findByEmail(dto.getEmail())
                        .isPresent()
        ) {

            throw new LoginException(
                    "El correo ya esta registrado"
            );
        }

        Login login = new Login();

        login.setName(dto.getName());
        login.setLastname(dto.getLastname());
        login.setEmail(dto.getEmail());
        login.setPassword(dto.getPassword());
        login.setRole(dto.getRole());

        return toDTO(
                repository.save(login)
        );
    }

/**
     * Actualiza los datos de un usuario existente sin cambiar su rol.
     *
     * @param id  identificador del usuario
     * @param dto datos nuevos del usuario
     * @return usuario actualizado
     */
    @Override
    public LoginResponseDTO updateUser(
            String id,
            LoginUpdateDTO dto
    ) {

        Login existing =
                repository.findById(id)
                        .orElseThrow(() ->
                                new LoginException(
                                        "Usuario no encontrado"
                                )
                        );

        if (
                dto.getName().trim().isEmpty()
                        ||
                dto.getLastname().trim().isEmpty()
                        ||
                dto.getEmail().trim().isEmpty()
        ) {

            throw new LoginException(
                    "Campos obligatorios vacíos"
            );
        }

        repository.findByEmail(dto.getEmail())
                .ifPresent(user -> {

                    if (
                            !user.getId().equals(id)
                    ) {

                        throw new LoginException(
                                "El correo ya esta en uso"
                        );
                    }
                });

        existing.setName(dto.getName());
        existing.setLastname(dto.getLastname());
        existing.setEmail(dto.getEmail());

        return toDTO(
                repository.save(existing)
        );
    }

/**
     * Elimina un usuario por su identificador, excepto si es administrador.
     *
     * @param id identificador del usuario
     */
    @Override
    public void deleteUser(
            String id
    ) {

        Login user =
                repository.findById(id)
                        .orElseThrow(() ->
                                new LoginException(
                                        "Usuario no encontrado"
                                )
                        );
        if (
                user.getRole() == Rol.ADMIN
        ) {

            throw new LoginException(
                    "No se puede eliminar un administrador"
            );
        }

        repository.deleteById(id);
    }
}