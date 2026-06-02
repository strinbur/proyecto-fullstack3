package com.grupocordillera.ms_bff.login.dto;

import lombok.Data;

@Data
public class LoginResponseDTO {

    private String id;
    private String name;
    private String lastname;
    private String email;
    private String role;
}