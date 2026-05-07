package com.grupocordillera.ms_login.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.grupocordillera.ms_login.model.Rol;
import lombok.Data;

@Data
@JsonPropertyOrder({
        "nombre",
        "apellido",
        "correo",
        "id",
        "rol"
})
public class LoginResponseDTO {

    private String id;
    private String nombre;
    private String apellido;
    private String correo;
    private Rol rol;
}