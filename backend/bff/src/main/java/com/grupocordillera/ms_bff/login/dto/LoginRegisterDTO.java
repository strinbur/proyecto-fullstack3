package com.grupocordillera.ms_bff.login.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginRegisterDTO {

    /** Nombre del nuevo usuario. */
    @NotBlank(message = "Nombre obligatorio")
    private String name;

    /** Apellido del nuevo usuario. */
    @NotBlank(message = "Apellido obligatorio")
    private String lastname;

    /** Correo del nuevo usuario. */
    @NotBlank(message = "Correo obligatorio")
    @Email(message = "Correo inválido")
    private String email;

    /** Contraseña (mínimo 6 caracteres). */
    @NotBlank(message = "Password obligatoria")
    @Size(min = 6, message = "La password debe tener al menos 6 caracteres")
    private String password;
}