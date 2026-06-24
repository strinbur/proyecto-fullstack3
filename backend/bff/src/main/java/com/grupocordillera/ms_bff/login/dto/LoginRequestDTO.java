package com.grupocordillera.ms_bff.login.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequestDTO {
    /** Correo electrónico del usuario. */
    @NotBlank(message = "Correo obligatorio")
    @Email(message = "Correo inválido")
    private String email;

    /** Contraseña en texto plano enviada para autenticación. */
    @NotBlank(message = "Password obligatoria")
    private String password;
}