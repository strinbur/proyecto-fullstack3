package com.grupocordillera.ms_bff.login.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginUpdateDTO {

    /** Nombre actualizado del usuario. */
    @NotBlank(message = "Nombre obligatorio")
    private String name;

    /** Apellido actualizado del usuario. */
    @NotBlank(message = "Apellido obligatorio")
    private String lastname;

    /** Correo actualizado del usuario. */
    @NotBlank(message = "Correo obligatorio")
    @Email(message = "Correo inválido")
    private String email;
}