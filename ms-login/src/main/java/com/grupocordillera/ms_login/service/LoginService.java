package com.grupocordillera.ms_login.service;

import com.grupocordillera.ms_login.dto.LoginResponseDTO;
import com.grupocordillera.ms_login.dto.LoginUpdateDTO;
import com.grupocordillera.ms_login.exception.LoginException;
import com.grupocordillera.ms_login.model.Login;
import com.grupocordillera.ms_login.model.Rol;
import com.grupocordillera.ms_login.repository.LoginRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoginService {

    private final LoginRepository repository;

    public LoginService(LoginRepository repository) {
        this.repository = repository;
    }

    // MAPEO DTO
    private LoginResponseDTO toDTO(Login login) {
        LoginResponseDTO dto = new LoginResponseDTO();
        dto.setId(login.getId());
        dto.setNombre(login.getNombre());
        dto.setApellido(login.getApellido());
        dto.setCorreo(login.getCorreo());
        dto.setRol(login.getRol());
        return dto;
    }

    // REGISTRAR
    public LoginResponseDTO registrar(Login login) {

        if (repository.findByCorreo(login.getCorreo()).isPresent()) {
            throw new LoginException("El correo ya esta registrado");
        }

        login.setRol(Rol.CLIENTE);

        Login guardado = repository.save(login);

        return toDTO(guardado);
    }

    // LOGIN
    public LoginResponseDTO login(String correo, String password) {

        Login usuario = repository.findByCorreo(correo)
                .orElseThrow(() -> new LoginException("Usuario no encontrado"));

        if (!usuario.getPassword().equals(password)) {
            throw new LoginException("Contraseña incorrecta");
        }

        return toDTO(usuario);
    }

    // LISTAR
    public List<LoginResponseDTO> listar() {
        return repository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // BUSCAR POR ID
    public LoginResponseDTO buscarPorId(String id) {
        return repository.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new LoginException("Usuario no encontrado"));
    }

    // CREAR USUARIO CON ROL
    public LoginResponseDTO crearUsuario(Login login) {

        if (repository.findByCorreo(login.getCorreo()).isPresent()) {
            throw new LoginException("El correo ya esta registrado");
        }

        Login guardado = repository.save(login);

        return toDTO(guardado);
    }

    // ACTUALIZAR
    public LoginResponseDTO actualizar(String id, LoginUpdateDTO dto) {

        Login existente = repository.findById(id)
                .orElseThrow(() -> new LoginException("Usuario no encontrado"));

        if (dto.getNombre() == null || dto.getApellido() == null || dto.getCorreo() == null ||
            dto.getNombre().trim().isEmpty() ||
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

        Login actualizado = repository.save(existente);

        return toDTO(actualizado);
    }

    // DELETE
    public void eliminar(String id) {
        if (!repository.existsById(id)) {
            throw new LoginException("Usuario no encontrado");
        }
        repository.deleteById(id);
    }
}