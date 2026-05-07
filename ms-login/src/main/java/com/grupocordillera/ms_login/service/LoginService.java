package com.grupocordillera.ms_login.service;

import com.grupocordillera.ms_login.dto.LoginResponseDTO;
import com.grupocordillera.ms_login.dto.LoginUpdateDTO;
import com.grupocordillera.ms_login.model.Login;
import com.grupocordillera.ms_login.model.Rol;
import com.grupocordillera.ms_login.repository.LoginRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LoginService {

    private final LoginRepository repository;

    public LoginService(LoginRepository repository) {
        this.repository = repository;
    }

    // MAPEO DEL DTO
    private LoginResponseDTO toDTO(Login login) {
        LoginResponseDTO dto = new LoginResponseDTO();
        dto.setId(login.getId());
        dto.setNombre(login.getNombre());
        dto.setApellido(login.getApellido());
        dto.setCorreo(login.getCorreo());
        dto.setRol(login.getRol());
        return dto;
    }

    // REGISTRAR USUARIO ROL CLIENTE
    public LoginResponseDTO registrar(Login login) {

        if (repository.findByCorreo(login.getCorreo()).isPresent()) {
            throw new RuntimeException("El correo ya esta registrado");
        }

        login.setRol(Rol.CLIENTE);

        Login guardado = repository.save(login);

        return toDTO(guardado);
    }

    // LOGIN
    public Optional<LoginResponseDTO> login(String correo, String password) {

        Optional<Login> usuario = repository.findByCorreo(correo);

        if (usuario.isPresent()) {
            if (usuario.get().getPassword().equals(password)) {
                return Optional.of(toDTO(usuario.get()));
            }
        }

        return Optional.empty();
    }

    // GET TODOS
    public List<LoginResponseDTO> listar() {
        return repository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // GET POR ID
    public Optional<LoginResponseDTO> buscarPorId(String id) {
        return repository.findById(id)
                .map(this::toDTO);
    }

    // CREAR USUARIO CON ROL
    public LoginResponseDTO crearUsuario(Login login) {

        if (repository.findByCorreo(login.getCorreo()).isPresent()) {
            throw new RuntimeException("El correo ya esta registrado");
        }

        Login guardado = repository.save(login);
        return toDTO(guardado);
    }

    // PUT 
    public LoginResponseDTO actualizar(String id, LoginUpdateDTO dto) {

        Login existente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // validación extra (defensa backend)
        if (dto.getNombre().trim().isEmpty()
                || dto.getApellido().trim().isEmpty()
                || dto.getCorreo().trim().isEmpty()) {
            throw new RuntimeException("Campos obligatorios vacíos");
        }

        Optional<Login> usuarioConMismoCorreo = repository.findByCorreo(dto.getCorreo());

        if (usuarioConMismoCorreo.isPresent() &&
                !usuarioConMismoCorreo.get().getId().equals(id)) {
            throw new RuntimeException("El correo ya esta en uso");
        }

        existente.setNombre(dto.getNombre());
        existente.setApellido(dto.getApellido());
        existente.setCorreo(dto.getCorreo());

        Login actualizado = repository.save(existente);

        return toDTO(actualizado);
    }

    // 🔹 DELETE
    public void eliminar(String id) {
        repository.deleteById(id);
    }
}