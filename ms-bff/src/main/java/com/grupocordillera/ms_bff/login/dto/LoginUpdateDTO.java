package com.grupocordillera.ms_bff.login.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginUpdateDTO {

    @NotBlank(message = "Nombre obligatorio")
    private String nombre;

    @NotBlank(message = "Apellido obligatorio")
    private String apellido;

    @NotBlank(message = "Correo obligatorio")
    @Email(message = "Correo inválido")
    private String correo;
}