package com.grupocordillera.ms_bff.login.controller;

import com.grupocordillera.ms_bff.login.dto.*;
import com.grupocordillera.ms_bff.login.service.LoginService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * BFF controller responsable de exponer endpoints de autenticación y
 * gestión de usuarios al frontend. Delegates en `LoginService` para la
 * comunicación con el microservicio de login.
 */
@RestController
@RequestMapping("/bff/login")
@CrossOrigin
public class LoginController {

    private static final Logger log =
            LoggerFactory.getLogger(LoginController.class);

    private final LoginService service;

    public LoginController(LoginService service) {
        this.service = service;
    }

    // Login
    @PostMapping
    public AuthResponseDTO login(@Valid @RequestBody LoginRequestDTO request) {
        log.info("BFF LOGIN RECIBIDO: {}", request.getEmail());

        // Realiza la autenticación y devuelve token + usuario
        return service.login(request);
    }

    // Crear cliente (registro publico)
    @PostMapping("/register")
    public LoginResponseDTO createClient(@Valid @RequestBody LoginRegisterDTO request) {
        log.info("BFF REGISTER: {}", request.getEmail());

        // Registra un cliente con rol por defecto (cliente)
        return service.createClient(request);
    }

    // Crear usuario con rol (solo admin)
    @PostMapping("/admin")
    public LoginResponseDTO createUser(
            @Valid @RequestBody LoginAdminCreateDTO request) {

        log.info(
                "BFF CREATE USER ROLE: {} - {}",
                request.getEmail(),
                request.getRole()
        );

        // Crea un usuario con rol específico (requiere autorización en backend)
        return service.createUser(request);
    }

    // Put usuario
    @PutMapping("/{id}")
    public LoginResponseDTO updateUser(
            @PathVariable String id,
            @Valid @RequestBody LoginUpdateDTO request) {
        log.info("BFF UPDATE PERFIL ID: {}", id);

        // Actualiza los datos del usuario identificado por id
        return service.updateUser(id, request);
    }

    // Get usuario por id
    @GetMapping("/{id}")
    public LoginResponseDTO getUserById(@PathVariable String id) {
        log.info("BFF GET USER ID: {}", id);

        return service.getUserById(id);
    }

    // Get todos los usuarios
    @GetMapping
    public List<LoginResponseDTO> getAllUsers() {
        log.info("BFF GET ALL USERS");

        return service.getAllUsers();
    }

    // Delete usuario
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable String id) {
        log.info("BFF DELETE USER ID: {}", id);

        // Elimina el usuario identificado por id delegando al servicio
        service.deleteUser(id);
    }
}