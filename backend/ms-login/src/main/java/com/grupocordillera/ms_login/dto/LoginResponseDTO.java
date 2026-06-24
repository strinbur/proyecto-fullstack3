package com.grupocordillera.ms_login.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.grupocordillera.ms_login.model.Rol;
import lombok.Data;

/**
 * DTO para la respuesta de información de usuario.
 */
@Data
@JsonPropertyOrder({
        "name",
        "lastname",
        "email",
        "id",
        "role"
})
public class LoginResponseDTO {

    /**
     * Identificador único del usuario.
     */
    private String id;

    /**
     * Nombre del usuario.
     */
    private String name;

    /**
     * Apellido del usuario.
     */
    private String lastname;

    /**
     * Correo electrónico del usuario.
     */
    private String email;

    /**
     * Rol asignado al usuario.
     */
    private Rol role;
} 