package com.grupocordillera.ms_login.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.grupocordillera.ms_login.model.Rol;
import lombok.Data;

@Data
@JsonPropertyOrder({
        "name",
        "lastname",
        "email",
        "id",
        "role"
})
public class LoginResponseDTO {

    private String id;
    private String name;
    private String lastname;
    private String email;
    private Rol role;
}