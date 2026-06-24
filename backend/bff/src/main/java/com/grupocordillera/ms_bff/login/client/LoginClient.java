package com.grupocordillera.ms_bff.login.client;

import com.grupocordillera.ms_bff.login.dto.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(
    name = "ms-login",
    path = "/login",
    configuration = com.grupocordillera.ms_bff.config.FeignConfig.class
)
public interface LoginClient {

    // Login
    @PostMapping("/auth")
    AuthResponseDTO login(@RequestBody LoginRequestDTO request);

    // Registro public crea un usuario con rol cliente
    @PostMapping("/register")
    LoginResponseDTO createClient(@RequestBody LoginRegisterDTO request);

    // Creacion de usuario con rol (solo admin)
    @PostMapping("/admin/create")
    LoginResponseDTO createUser(@RequestBody LoginAdminCreateDTO request);

    // Update de usuario
    @PutMapping("/{id}")
    LoginResponseDTO updateUser(
            @PathVariable("id") String id,
            @RequestBody LoginUpdateDTO request
    );

    // Get by ID
    @GetMapping("/{id}")
    LoginResponseDTO getUserById(@PathVariable("id") String id);

    // Get todos los usuarios
    @GetMapping
    List<LoginResponseDTO> getAllUsers();

    // Delete usuario
    @DeleteMapping("/{id}")
    Void deleteUser(@PathVariable("id") String id);
}