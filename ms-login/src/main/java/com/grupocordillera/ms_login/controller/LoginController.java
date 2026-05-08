package com.grupocordillera.ms_login.controller;

import com.grupocordillera.ms_login.dto.LoginRequestDTO;
import com.grupocordillera.ms_login.dto.LoginResponseDTO;
import com.grupocordillera.ms_login.dto.LoginUpdateDTO;
import com.grupocordillera.ms_login.model.Login;
import com.grupocordillera.ms_login.service.LoginService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;



@RestController
@RequestMapping("/login")
@CrossOrigin
public class LoginController {

    private final LoginService service;

    public LoginController(LoginService service) {
        this.service = service;
    }


    @PostMapping("/register")
    public ResponseEntity<?> registrar(@Valid @RequestBody Login login) {
        try {
            return ResponseEntity.ok(service.registrar(login));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PostMapping
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDTO request) {

        Optional<LoginResponseDTO> usuario = service.login(
                request.getCorreo(),
                request.getPassword()
        );

        return usuario
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(401).body("Credenciales incorrectas"));
    }


    @GetMapping
    public ResponseEntity<?> listar() {
        return ResponseEntity.ok(service.listar());
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable String id) {

        Optional<LoginResponseDTO> usuario = service.buscarPorId(id);

        return usuario
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(
            @PathVariable String id,
            @Valid @RequestBody LoginUpdateDTO dto
    ) {
        try {
            return ResponseEntity.ok(service.actualizar(id, dto));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable String id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/admin")
    public ResponseEntity<?> crearUsuario(
            @Valid @RequestBody Login login
    ) {

        try {

            return ResponseEntity.ok(
                    service.crearUsuario(login)
            );

        } catch (RuntimeException e) {

            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());

        }

    }

  

}