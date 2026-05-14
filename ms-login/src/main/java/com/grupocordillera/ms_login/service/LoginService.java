package com.grupocordillera.ms_login.service;

import com.grupocordillera.ms_login.dto.AuthResponseDTO;
import com.grupocordillera.ms_login.dto.CreateUserDTO;
import com.grupocordillera.ms_login.dto.LoginResponseDTO;
import com.grupocordillera.ms_login.dto.LoginUpdateDTO;
import com.grupocordillera.ms_login.dto.RegisterDTO;

import java.util.List;

public interface LoginService {

    LoginResponseDTO registrar(RegisterDTO dto);

    AuthResponseDTO login(String correo, String password);

    List<LoginResponseDTO> listar();

    LoginResponseDTO buscarPorId(String id);

    LoginResponseDTO crearUsuario(CreateUserDTO dto);

    LoginResponseDTO actualizar(String id, LoginUpdateDTO dto);

    void eliminar(String id);
}