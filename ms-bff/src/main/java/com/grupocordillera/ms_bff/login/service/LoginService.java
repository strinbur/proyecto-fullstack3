package com.grupocordillera.ms_bff.login.service;

import com.grupocordillera.ms_bff.login.client.LoginClient;
import com.grupocordillera.ms_bff.login.dto.*;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoginService {

    private final LoginClient loginClient;

    public LoginService(LoginClient loginClient) {
        this.loginClient = loginClient;
    }

    // LOGIN
    public LoginResponseDTO login(LoginRequestDTO request) {
        try {
            return loginClient.login(request);
        } catch (Exception e) {
            throw new RuntimeException("Error en login: " + e.getMessage());
        }
    }

    // REGISTER PUBLICO
    public LoginResponseDTO register(LoginRegisterDTO request) {
        try {
            return loginClient.register(request);
        } catch (Exception e) {
            throw new RuntimeException("Error en registro: " + e.getMessage());
        }
    }

    // CREAR USUARIO CON ROL
    public LoginResponseDTO createUserWithRole(LoginAdminCreateDTO request) {
        try {
            return loginClient.createUserWithRole(request);
        } catch (Exception e) {
            throw new RuntimeException("Error creando usuario: " + e.getMessage());
        }
    }

    // UPDATE
    public LoginResponseDTO update(String id, LoginUpdateDTO request) {
        try {
            return loginClient.update(id, request);
        } catch (Exception e) {
            throw new RuntimeException("Error actualizando usuario: " + e.getMessage());
        }
    }

    // GET POR ID
    public LoginResponseDTO getById(String id) {
        try {
            return loginClient.getById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error obteniendo usuario: " + e.getMessage());
        }
    }

    // GET ALL
    public List<LoginResponseDTO> getAll() {
        try {
            return loginClient.getAll();
        } catch (Exception e) {
            throw new RuntimeException("Error obteniendo usuarios: " + e.getMessage());
        }
    }

    // DELETE
    public void delete(String id) {
        try {
            loginClient.delete(id);
        } catch (Exception e) {
            throw new RuntimeException("Error eliminando usuario: " + e.getMessage());
        }
    }
}