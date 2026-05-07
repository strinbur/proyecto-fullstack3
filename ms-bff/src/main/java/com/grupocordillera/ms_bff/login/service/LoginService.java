package com.grupocordillera.ms_bff.login.service;

import com.grupocordillera.ms_bff.login.client.LoginClient;
import com.grupocordillera.ms_bff.login.dto.LoginRequestDTO;
import com.grupocordillera.ms_bff.login.dto.LoginResponseDTO;
import com.grupocordillera.ms_bff.login.dto.LoginUpdateDTO;
import com.grupocordillera.ms_bff.login.dto.LoginRegisterDTO;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoginService {

    private final LoginClient loginClient;

    public LoginService(LoginClient loginClient) {
        this.loginClient = loginClient;
    }

    public LoginResponseDTO login(LoginRequestDTO request) {
        return loginClient.login(request);
    }

    public LoginResponseDTO register(LoginRegisterDTO request) {
        return loginClient.register(request);
    }

    public LoginResponseDTO update(String id, LoginUpdateDTO request) {
        return loginClient.update(id, request);
    }

    public LoginResponseDTO getById(String id) {
        return loginClient.getById(id);
    }

    public List<LoginResponseDTO> getAll() {
        return loginClient.getAll();
    }

    public void delete(String id) {
        loginClient.delete(id);
    }
}