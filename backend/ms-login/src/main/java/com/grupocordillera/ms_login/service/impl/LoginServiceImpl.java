package com.grupocordillera.ms_login.service.impl;

import com.grupocordillera.ms_login.common.exception.LoginException;
import com.grupocordillera.ms_login.dto.*;
import com.grupocordillera.ms_login.model.Login;
import com.grupocordillera.ms_login.model.Rol;
import com.grupocordillera.ms_login.repository.LoginRepository;
import com.grupocordillera.ms_login.security.JwtService;
import com.grupocordillera.ms_login.service.LoginService;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

//Mapeo de entidad a DTO
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

//Crea un nuevo usuario, pero no se puede crear un ADMIN
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

//Login de usuario, genera un token JWT si las credenciales son correctas
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

//Get todos los usuarios
    @Override
    public List<LoginResponseDTO> getAllUsers() {

        return repository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

//Get un usuario por su ID
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

//Crea un nuevo usuario con rol de CLIENTE
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

//Actualiza un usuario por su ID
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

//Elimina un usuario por su ID, pero no se puede eliminar un ADMIN
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