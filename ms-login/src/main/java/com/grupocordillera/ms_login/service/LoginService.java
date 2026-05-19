package com.grupocordillera.ms_login.service;

import com.grupocordillera.ms_login.dto.AuthResponseDTO;
import com.grupocordillera.ms_login.dto.CreateUserDTO;
import com.grupocordillera.ms_login.dto.LoginResponseDTO;
import com.grupocordillera.ms_login.dto.LoginUpdateDTO;
import com.grupocordillera.ms_login.dto.RegisterDTO;

import java.util.List;

public interface LoginService {

    LoginResponseDTO createClient(RegisterDTO dto);

    AuthResponseDTO login(String email, String password);

    List<LoginResponseDTO> getAllUsers();

    LoginResponseDTO getUserById(String id);

    LoginResponseDTO createUser(CreateUserDTO dto);

    LoginResponseDTO updateUser(String id, LoginUpdateDTO dto);

    void deleteUser(String id);
}