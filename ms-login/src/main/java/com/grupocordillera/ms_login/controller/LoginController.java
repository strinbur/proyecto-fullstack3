package com.grupocordillera.ms_login.controller;

import com.grupocordillera.ms_login.dto.*;
import com.grupocordillera.ms_login.service.LoginService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/login")
@CrossOrigin
public class LoginController {

    private final LoginService service;

    public LoginController(LoginService service) {
        this.service = service;
    }

    // Registro publico crea un usuario con rol CLIENTE
    @PostMapping("/register")
    public ResponseEntity<LoginResponseDTO> createClient(
            @Valid @RequestBody RegisterDTO dto
    ) {

        return new ResponseEntity<>(
                service.createClient(dto),
                HttpStatus.CREATED
        );
    }

    // Login
    @PostMapping("/auth")
    public ResponseEntity<AuthResponseDTO> login(
            @Valid @RequestBody LoginRequestDTO request
    ) {

        return ResponseEntity.ok(
                service.login(
                        request.getEmail(),
                        request.getPassword()
                )
        );
    }

    // Solo ADMIN y VENTAS: Listar todos los usuarios
    @PreAuthorize("hasAnyRole('ADMIN','VENTAS')")
    @GetMapping
    public ResponseEntity<List<LoginResponseDTO>> getAllUsers() {

        return ResponseEntity.ok(
                service.getAllUsers()
        );
    }

    // Solo ADMIN y VENTAS: Obtener usuario por ID
    @PreAuthorize("hasAnyRole('ADMIN','VENTAS')")
    @GetMapping("/{id}")
    public ResponseEntity<LoginResponseDTO> getUserById(
            @PathVariable String id
    ) {

        return ResponseEntity.ok(
                service.getUserById(id)
        );
    }

    // Usuario propio, ADMIN y VENTAS: Actualizar usuario sin cambiar rol
    @PreAuthorize("hasAnyRole('ADMIN','VENTAS','CLIENTE')")
    @PutMapping("/{id}")
    public ResponseEntity<LoginResponseDTO> updateUser(
            @PathVariable String id,
            @Valid @RequestBody LoginUpdateDTO dto
    ) {

        return ResponseEntity.ok(
                service.updateUser(id, dto)
        );
    }

    // Solo ADMIN: Eliminar usuario por ID
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(
            @PathVariable String id
    ) {

        service.deleteUser(id);

        return ResponseEntity.noContent().build();
    }

    // Solo ADMIN puede crear un usuario con rol especifico
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin/create")
    public ResponseEntity<LoginResponseDTO> createUser(
            @Valid @RequestBody CreateUserDTO dto
    ) {

        return new ResponseEntity<>(
                service.createUser(dto),
                HttpStatus.CREATED
        );
    }
}