package com.grupocordillera.ms_bff.login.controller;

import com.grupocordillera.ms_bff.login.dto.LoginAdminCreateDTO;
import com.grupocordillera.ms_bff.login.dto.LoginRequestDTO;
import com.grupocordillera.ms_bff.login.dto.LoginResponseDTO;
import com.grupocordillera.ms_bff.login.dto.LoginRegisterDTO;
import com.grupocordillera.ms_bff.login.dto.LoginUpdateDTO;
import com.grupocordillera.ms_bff.login.service.LoginService;

import jakarta.validation.Valid;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

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

    // LOGIN
    @PostMapping
    public LoginResponseDTO login(
            @RequestBody LoginRequestDTO request
    ) {

        log.info(
                " BFF LOGIN RECIBIDO: {}",
                request.getCorreo()
        );

        return service.login(request);

    }

    // REGISTER PUBLICO
    @PostMapping("/register")
    public LoginResponseDTO register(
            @RequestBody LoginRegisterDTO request
    ) {

        log.info(
                " BFF REGISTER: {}",
                request.getCorreo()
        );

        return service.register(request);

    }

    // CREAR USUARIO CON ROL
    @PostMapping("/admin")
    public LoginResponseDTO createUserWithRole(
            @Valid @RequestBody LoginAdminCreateDTO request
    ) {

        log.info(
                " BFF CREATE USER ROLE: {} - {}",
                request.getCorreo(),
                request.getRol()
        );

        return service.createUserWithRole(request);

    }

    // UPDATE
    @PutMapping("/{id}")
    public LoginResponseDTO update(
            @PathVariable String id,
            @RequestBody LoginUpdateDTO request
    ) {

        log.info(
                " BFF UPDATE PERFIL ID: {}",
                id
        );

        return service.update(id, request);

    }

    // GET POR ID
    @GetMapping("/{id}")
    public LoginResponseDTO getById(
            @PathVariable String id
    ) {

        log.info(
                " BFF GET USER ID: {}",
                id
        );

        return service.getById(id);

    }

    @GetMapping
    public List<LoginResponseDTO> getAll() {

        log.info(" BFF GET ALL USERS");

        return service.getAll();

    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {

        log.info("BFF DELETE USER ID: {}", id);

        service.delete(id);

    }

}