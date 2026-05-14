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

    // 1. PÚBLICO: Registro de clientes
    @PostMapping("/register")
    public ResponseEntity<LoginResponseDTO> registrar(
            @Valid @RequestBody RegisterDTO dto
    ) {

        return new ResponseEntity<>(
                service.registrar(dto),
                HttpStatus.CREATED
        );
    }

    // 2. PÚBLICO: LOGIN
    @PostMapping("/auth")
    public ResponseEntity<AuthResponseDTO> login(
            @Valid @RequestBody LoginRequestDTO request
    ) {

        return ResponseEntity.ok(
                service.login(
                        request.getCorreo(),
                        request.getPassword()
                )
        );
    }

    // 3. SOLO ADMIN Y VENTAS: LISTAR
    @PreAuthorize("hasAnyRole('ADMIN','VENTAS')")
    @GetMapping
    public ResponseEntity<List<LoginResponseDTO>> listar() {

        return ResponseEntity.ok(
                service.listar()
        );
    }

    // 4. SOLO ADMIN Y VENTAS: BUSCAR POR ID
    @PreAuthorize("hasAnyRole('ADMIN','VENTAS')")
    @GetMapping("/{id}")
    public ResponseEntity<LoginResponseDTO> obtenerPorId(
            @PathVariable String id
    ) {

        return ResponseEntity.ok(
                service.buscarPorId(id)
        );
    }

    // 5. USUARIO LOGEADO PUEDE ACTUALIZAR
    @PreAuthorize("hasAnyRole('ADMIN','VENTAS','CLIENTE')")
    @PutMapping("/{id}")
    public ResponseEntity<LoginResponseDTO> actualizar(
            @PathVariable String id,
            @Valid @RequestBody LoginUpdateDTO dto
    ) {

        return ResponseEntity.ok(
                service.actualizar(id, dto)
        );
    }

    // 6. SOLO ADMIN: ELIMINAR
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable String id
    ) {

        service.eliminar(id);

        return ResponseEntity.noContent().build();
    }

    // 7. SOLO ADMIN: CREAR USUARIO CON ROL
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin/create")
    public ResponseEntity<LoginResponseDTO> crearUsuario(
            @Valid @RequestBody CreateUserDTO dto
    ) {

        return new ResponseEntity<>(
                service.crearUsuario(dto),
                HttpStatus.CREATED
        );
    }
}