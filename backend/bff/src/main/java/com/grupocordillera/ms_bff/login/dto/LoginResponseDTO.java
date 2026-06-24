package com.grupocordillera.ms_bff.login.dto;

import lombok.Data;

@Data
public class LoginResponseDTO {
    /** Identificador único del usuario. */
    private String id;

    /** Nombre del usuario. */
    private String name;

    /** Apellido del usuario. */
    private String lastname;

    /** Correo electrónico del usuario. */
    private String email;

    /** Rol asignado al usuario (ej. ADMIN, CLIENTE). */
    private String role;
}