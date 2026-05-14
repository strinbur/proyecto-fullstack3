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

    // LOGIN: Corregido para manejar el DTO que contiene el Token
    @Override
    public AuthResponseDTO login(LoginRequestDTO request) {
        return loginClient.login(request);
    }

    // REGISTER
    @Override
    public LoginResponseDTO register(LoginRegisterDTO request) {
        return loginClient.register(request);
    }

    // CREACION CON ROL
    @Override
    public LoginResponseDTO createUserWithRole(LoginAdminCreateDTO request) {
        return loginClient.createUserWithRole(request);
    }

    // UPDATE
    @Override
    public LoginResponseDTO update(String id, LoginUpdateDTO request) {
        return loginClient.update(id, request);
    }

    // GET POR ID
    @Override
    public LoginResponseDTO getById(String id) {
        return loginClient.getById(id);
    }

    // GET ALL
    @Override
    public List<LoginResponseDTO> getAll() {
        return loginClient.getAll();
    }

    // DELETE
    @Override
    public void delete(String id) {
        loginClient.delete(id);
    }
}