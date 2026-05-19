package com.grupocordillera.ms_bff.login.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginRegisterDTO {

    @NotBlank(message = "Nombre obligatorio")
    private String name;

    @NotBlank(message = "Apellido obligatorio")
    private String lastname;

    @NotBlank(message = "Correo obligatorio")
    @Email(message = "Correo inválido")
    private String email;

    @NotBlank(message = "Password obligatoria")
    @Size(min = 6, message = "La password debe tener al menos 6 caracteres")
    private String password;
}