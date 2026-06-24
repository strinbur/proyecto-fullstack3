package com.grupocordillera.ms_login.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Entidad de usuario almacenada en MongoDB para el módulo de login.
 */
@Data
@Document(collection = "users")
public class Login {

    /**
     * Identificador único generado por MongoDB.
     */
    @Id
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
     * Contraseña cifrada del usuario.
     */
    private String password;

    /**
     * Rol asignado al usuario.
     */
    private Rol role;
}