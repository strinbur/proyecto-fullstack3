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
        return loginClient.login(request);
    }

    // REGISTER
    public LoginResponseDTO register(LoginRegisterDTO request) {
        return loginClient.register(request);
    }

    // CREACION CON ROL
    public LoginResponseDTO createUserWithRole(LoginAdminCreateDTO request) {
        return loginClient.createUserWithRole(request);
    }

    // UPDATE
    public LoginResponseDTO update(String id, LoginUpdateDTO request) {
        return loginClient.update(id, request);
    }

    // GET POR ID
    public LoginResponseDTO getById(String id) {
        return loginClient.getById(id);
    }

    // GET ALL
    public List<LoginResponseDTO> getAll() {
        return loginClient.getAll();
    }

    // DELETE
    public void delete(String id) {
        loginClient.delete(id);
    }
}