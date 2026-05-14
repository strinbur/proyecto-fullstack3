package com.grupocordillera.ms_bff.login.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequestDTO {
    
    @NotBlank(message = "Correo obligatorio")
    @Email(message = "Correo invalido")
    private String correo;

    @NotBlank(message = "Password obligatoria")
    private String password;
}