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

    // LOGIN: Cambiado a AuthResponseDTO para recibir token y usuario
    @PostMapping("/auth")
    AuthResponseDTO login(@RequestBody LoginRequestDTO request);

    // REGISTER
    @PostMapping("/register")
    LoginResponseDTO register(@RequestBody LoginRegisterDTO dto);

    // ADMIN CREATE
    @PostMapping("/admin/create")
    LoginResponseDTO createUserWithRole(@RequestBody LoginAdminCreateDTO dto);

    // UPDATE
    @PutMapping("/{id}")
    LoginResponseDTO update(
            @PathVariable("id") String id,
            @RequestBody LoginUpdateDTO dto
    );

    // GET BY ID
    @GetMapping("/{id}")
    LoginResponseDTO getById(@PathVariable("id") String id);

    // GET ALL
    @GetMapping
    List<LoginResponseDTO> getAll();

    // DELETE
    @DeleteMapping("/{id}")
    Void delete(@PathVariable("id") String id);
}