package com.grupocordillera.ms_bff.login.dto;

import lombok.Data;

@Data
public class LoginResponseDTO {
    private String id;
    private String nombre;
    private String apellido;
    private String correo;
    private String rol;
}