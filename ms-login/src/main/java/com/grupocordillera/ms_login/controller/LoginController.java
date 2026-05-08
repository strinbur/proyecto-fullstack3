package com.grupocordillera.ms_login.controller;

import com.grupocordillera.ms_login.dto.LoginRequestDTO;
import com.grupocordillera.ms_login.dto.LoginResponseDTO;
import com.grupocordillera.ms_login.dto.LoginUpdateDTO;
import com.grupocordillera.ms_login.model.Login;
import com.grupocordillera.ms_login.service.LoginService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
@CrossOrigin
public class LoginController {

    private final LoginService service;

    public LoginController(LoginService service) {
        this.service = service;
    }

    // REGISTRAR SIN ROL 
    @PostMapping("/register")
    public ResponseEntity<LoginResponseDTO> registrar(@Valid @RequestBody Login login) {
        return ResponseEntity.ok(service.registrar(login));
    }

    // LOGIN
    @PostMapping
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO request) {

        LoginResponseDTO usuario = service.login(
                request.getCorreo(),
                request.getPassword()
        );

        return ResponseEntity.ok(usuario);
    }

    // LISTAR
    @GetMapping
    public ResponseEntity<?> listar() {
        return ResponseEntity.ok(service.listar());
    }

    // GET POR ID
    @GetMapping("/{id}")
    public ResponseEntity<LoginResponseDTO> obtenerPorId(@PathVariable String id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<LoginResponseDTO> actualizar(
            @PathVariable String id,
            @Valid @RequestBody LoginUpdateDTO dto
    ) {
        return ResponseEntity.ok(service.actualizar(id, dto));
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable String id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    // CREAR USUARIO CON ROL
    @PostMapping("/admin")
    public ResponseEntity<LoginResponseDTO> crearUsuario(@Valid @RequestBody Login login) {
        return ResponseEntity.ok(service.crearUsuario(login));
    }
}