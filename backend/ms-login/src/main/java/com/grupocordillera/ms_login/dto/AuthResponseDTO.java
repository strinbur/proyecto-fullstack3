package com.grupocordillera.ms_login.dto;

import lombok.Data;

/**
 * DTO de respuesta de autenticación que incluye el token y los datos del usuario.
 */
@Data
public class AuthResponseDTO {

    /**
     * Token JWT generado tras la autenticación.
     */
    private String token;

    /**
     * Datos del usuario autenticado.
     */
    private LoginResponseDTO user;
}