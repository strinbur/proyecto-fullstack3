package com.grupocordillera.ms_bff.login.dto;

import lombok.Data;

@Data
public class AuthResponseDTO {
    /** Token JWT devuelto al autenticarse. */
    private String token;

    /** Información del usuario autenticado. */
    private LoginResponseDTO user;
}