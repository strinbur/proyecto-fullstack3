package com.grupocordillera.ms_bff.login.service;

import com.grupocordillera.ms_bff.login.dto.*;

import java.util.List;

public interface LoginService {

    // LOGIN: Retorna Token + Usuario
    AuthResponseDTO login(LoginRequestDTO request);

    LoginResponseDTO register(LoginRegisterDTO request);

    LoginResponseDTO createUserWithRole(LoginAdminCreateDTO request);

    LoginResponseDTO update(String id, LoginUpdateDTO request);

    LoginResponseDTO getById(String id);

    // GET ALL: Corregido (solo una lista)
    List<LoginResponseDTO> getAll();

    void delete(String id);
}