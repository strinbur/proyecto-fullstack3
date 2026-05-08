package com.grupocordillera.ms_bff.login.client;

import com.grupocordillera.ms_bff.login.dto.LoginAdminCreateDTO;
import com.grupocordillera.ms_bff.login.dto.LoginRegisterDTO;
import com.grupocordillera.ms_bff.login.dto.LoginRequestDTO;
import com.grupocordillera.ms_bff.login.dto.LoginResponseDTO;
import com.grupocordillera.ms_bff.login.dto.LoginUpdateDTO;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "ms-login", url = "http://localhost:8081")
public interface LoginClient {

    @PostMapping("/login")
    LoginResponseDTO login(
            @RequestBody LoginRequestDTO request
    );

    @PostMapping("/login/register")
    LoginResponseDTO register(
            @RequestBody LoginRegisterDTO dto
    );

    @PostMapping("/login/admin")
    LoginResponseDTO createUserWithRole(
            @RequestBody LoginAdminCreateDTO dto
    );

    @PutMapping("/login/{id}")
    LoginResponseDTO update(
            @PathVariable("id") String id,
            @RequestBody LoginUpdateDTO dto
    );

    @GetMapping("/login/{id}")
    LoginResponseDTO getById(
            @PathVariable("id") String id
    );

    @GetMapping("/login")
    List<LoginResponseDTO> getAll();

    @DeleteMapping("/login/{id}")
    Void delete(
            @PathVariable("id") String id
    );
}