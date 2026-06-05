package com.grupocordillera.ms_login.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "users")
public class Login {

    @Id
    private String id;

    private String name;
    private String lastname;
    private String email;
    private String password;
    private Rol role;
}