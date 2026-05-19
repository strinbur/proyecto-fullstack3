package com.grupocordillera.ms_bff.login.service.impl;

import com.grupocordillera.ms_bff.login.client.LoginClient;
import com.grupocordillera.ms_bff.login.dto.*;
import com.grupocordillera.ms_bff.login.service.LoginService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoginServiceImpl implements LoginService {

    private final LoginClient loginClient;

    public LoginServiceImpl(LoginClient loginClient) {
        this.loginClient = loginClient;
    }

    // Login
    @Override
    public AuthResponseDTO login(LoginRequestDTO request) {
        return loginClient.login(request);
    }

    // Crear cliente (registro publico)
    @Override
    public LoginResponseDTO createClient(LoginRegisterDTO request) {
        return loginClient.createClient(request);
    }

    // Creacion de usuario con rol (solo admin)
    @Override
    public LoginResponseDTO createUser(LoginAdminCreateDTO request) {
        return loginClient.createUser(request);
    }

    // Update usuario
    @Override
    public LoginResponseDTO updateUser(String id, LoginUpdateDTO request) {
        return loginClient.updateUser(id, request);
    }

    // Get usuarios por id
    @Override
    public LoginResponseDTO getUserById(String id) {
        return loginClient.getUserById(id);
    }

    // Get all usuarios
    @Override
    public List<LoginResponseDTO> getAllUsers() {
        return loginClient.getAllUsers();
    }

    // Delete usuario
    @Override
    public void deleteUser(String id) {
        loginClient.deleteUser(id);
    }
}