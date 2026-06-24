package com.grupocordillera.ms_bff.login.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginAdminCreateDTO {

    /** Nombre del usuario a crear por admin. */
    @NotBlank(message = "Nombre obligatorio")
    private String name;

    /** Apellido del usuario a crear por admin. */
    @NotBlank(message = "Apellido obligatorio")
    private String lastname;

    /** Correo del usuario a crear por admin. */
    @NotBlank(message = "Correo obligatorio")
    @Email(message = "Correo inválido")
    private String email;

    /** Contraseña inicial (mínimo 6 caracteres). */
    @NotBlank(message = "Password obligatoria")
    @Size(min = 6, message = "La password debe tener al menos 6 caracteres")
    private String password;

    /** Rol que se asignará al usuario (ej. ADMIN, CLIENTE). */
    @NotBlank(message = "Rol obligatorio")
    private String role;
}