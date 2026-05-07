package com.grupocordillera.ms_bff.login.dto;

import lombok.Data;

@Data
public class LoginRegisterDTO {

    private String nombre;
    private String apellido;
    private String correo;
    private String password;
}