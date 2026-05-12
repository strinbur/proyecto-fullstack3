package com.grupocordillera.ms_login.service.impl;

import com.grupocordillera.ms_login.dto.*;
import com.grupocordillera.ms_login.exception.LoginException;
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

    public LoginServiceImpl(LoginRepository repository, JwtService jwtService) {
        this.repository = repository;
        this.jwtService = jwtService;
    }

    // 🔥 MAPEO DTO (CORRECTO)
    private LoginResponseDTO toDTO(Login login) {

        LoginResponseDTO dto = new LoginResponseDTO();

        dto.setId(login.getId());
        dto.setNombre(login.getNombre());
        dto.setApellido(login.getApellido());
        dto.setCorreo(login.getCorreo());

        // ✅ CORRECTO: enum directo (NO .name())
        dto.setRol(login.getRol());

        return dto;
    }

    // REGISTRO (CLIENTE POR DEFECTO)
    @Override
    public LoginResponseDTO registrar(RegisterDTO dto) {

        if (repository.findByCorreo(dto.getCorreo()).isPresent()) {
            throw new LoginException("El correo ya esta registrado");
        }

        Login login = new Login();

        login.setNombre(dto.getNombre());
        login.setApellido(dto.getApellido());
        login.setCorreo(dto.getCorreo());
        login.setPassword(dto.getPassword());
        login.setRol(Rol.CLIENTE);

        return toDTO(repository.save(login));
    }

    // LOGIN
    @Override
    public AuthResponseDTO login(String correo, String password) {

        Login usuario = repository.findByCorreo(correo)
                .orElseThrow(() -> new LoginException("Usuario no encontrado"));

        if (!usuario.getPassword().equals(password)) {
            throw new LoginException("Contraseña incorrecta");
        }

        String token = jwtService.generateToken(usuario);

        AuthResponseDTO response = new AuthResponseDTO();

        response.setToken(token);
        response.setUsuario(toDTO(usuario));

        return response;
    }

    // LISTAR
    @Override
    public List<LoginResponseDTO> listar() {
        return repository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // POR ID
    @Override
    public LoginResponseDTO buscarPorId(String id) {
        return repository.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new LoginException("Usuario no encontrado"));
    }

    // CREAR USUARIO CON ROL (ADMIN)
    @Override
    public LoginResponseDTO crearUsuario(CreateUserDTO dto) {

        if (repository.findByCorreo(dto.getCorreo()).isPresent()) {
            throw new LoginException("El correo ya esta registrado");
        }

        Login login = new Login();

        login.setNombre(dto.getNombre());
        login.setApellido(dto.getApellido());
        login.setCorreo(dto.getCorreo());
        login.setPassword(dto.getPassword());

        // ✅ IMPORTANTE: aquí debe ser ENUM
        login.setRol(dto.getRol());

        return toDTO(repository.save(login));
    }

    // ACTUALIZAR
    @Override
    public LoginResponseDTO actualizar(String id, LoginUpdateDTO dto) {

        Login existente = repository.findById(id)
                .orElseThrow(() -> new LoginException("Usuario no encontrado"));

        if (dto.getNombre().trim().isEmpty() ||
            dto.getApellido().trim().isEmpty() ||
            dto.getCorreo().trim().isEmpty()) {

            throw new LoginException("Campos obligatorios vacíos");
        }

        repository.findByCorreo(dto.getCorreo()).ifPresent(usuario -> {
            if (!usuario.getId().equals(id)) {
                throw new LoginException("El correo ya esta en uso");
            }
        });

        existente.setNombre(dto.getNombre());
        existente.setApellido(dto.getApellido());
        existente.setCorreo(dto.getCorreo());

        return toDTO(repository.save(existente));
    }

    // DELETE
    @Override
    public void eliminar(String id) {

        if (!repository.existsById(id)) {
            throw new LoginException("Usuario no encontrado");
        }

        repository.deleteById(id);
    }
}