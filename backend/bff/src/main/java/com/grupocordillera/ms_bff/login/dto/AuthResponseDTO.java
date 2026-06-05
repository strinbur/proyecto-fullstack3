package com.grupocordillera.ms_bff.login.dto;

import lombok.Data;

@Data
public class AuthResponseDTO {

    private String token;
    private LoginResponseDTO user;
}