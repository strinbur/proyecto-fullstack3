package com.grupocordillera.ms_login.dto;

import lombok.Data;

@Data
public class AuthResponseDTO {

    private String token;

    private LoginResponseDTO usuario;
}