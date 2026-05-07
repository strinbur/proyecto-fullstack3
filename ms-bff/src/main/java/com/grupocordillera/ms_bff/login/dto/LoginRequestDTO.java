package com.grupocordillera.ms_bff.login.dto;

import lombok.Data;

@Data
public class LoginRequestDTO {
    private String correo;
    private String password;
}