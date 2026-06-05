package com.grupocordillera.ms_bff.login.service;

import com.grupocordillera.ms_bff.login.dto.*;

import java.util.List;

public interface LoginService {


    AuthResponseDTO login(LoginRequestDTO request);

    LoginResponseDTO createClient(LoginRegisterDTO request);

    LoginResponseDTO createUser(LoginAdminCreateDTO request);

    LoginResponseDTO updateUser(String id, LoginUpdateDTO request);

    LoginResponseDTO getUserById(String id);

    List<LoginResponseDTO> getAllUsers();

    void deleteUser(String id);
}