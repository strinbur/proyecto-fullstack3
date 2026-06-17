package com.grupocordillera.ms_bff.login.controller;

import com.grupocordillera.ms_bff.login.dto.*;
import com.grupocordillera.ms_bff.login.service.LoginService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class LoginControllerTest {

    private final LoginService service =
            Mockito.mock(LoginService.class);

    private final LoginController controller =
            new LoginController(service);

    @Test
    void shouldLogin() {

        LoginRequestDTO request =
                new LoginRequestDTO();

        request.setEmail("test@test.cl");
        request.setPassword("123456");

        AuthResponseDTO responseDto =
                new AuthResponseDTO();

        Mockito.when(service.login(request))
                .thenReturn(responseDto);

        AuthResponseDTO response =
                controller.login(request);

        assertNotNull(response);
    }

    @Test
    void shouldCreateClient() {

        LoginRegisterDTO request =
                new LoginRegisterDTO();

        request.setEmail("test@test.cl");

        LoginResponseDTO responseDto =
                new LoginResponseDTO();

        Mockito.when(service.createClient(request))
                .thenReturn(responseDto);

        LoginResponseDTO response =
                controller.createClient(request);

        assertNotNull(response);
    }

    @Test
    void shouldCreateUser() {

        LoginAdminCreateDTO request =
                new LoginAdminCreateDTO();

        request.setEmail("admin@test.cl");
        request.setRole("ADMIN");

        LoginResponseDTO responseDto =
                new LoginResponseDTO();

        Mockito.when(service.createUser(request))
                .thenReturn(responseDto);

        LoginResponseDTO response =
                controller.createUser(request);

        assertNotNull(response);
    }

    @Test
    void shouldUpdateUser() {

        LoginUpdateDTO request =
                new LoginUpdateDTO();

        LoginResponseDTO responseDto =
                new LoginResponseDTO();

        Mockito.when(
                service.updateUser(
                        "1",
                        request
                )
        ).thenReturn(responseDto);

        LoginResponseDTO response =
                controller.updateUser(
                        "1",
                        request
                );

        assertNotNull(response);
    }

    @Test
    void shouldGetUserById() {

        LoginResponseDTO dto =
                new LoginResponseDTO();

        Mockito.when(service.getUserById("1"))
                .thenReturn(dto);

        LoginResponseDTO response =
                controller.getUserById("1");

        assertNotNull(response);
    }

    @Test
    void shouldGetAllUsers() {

        List<LoginResponseDTO> users =
                List.of(new LoginResponseDTO());

        Mockito.when(service.getAllUsers())
                .thenReturn(users);

        List<LoginResponseDTO> response =
                controller.getAllUsers();

        assertNotNull(response);
    }

    @Test
    void shouldDeleteUser() {

        controller.deleteUser("1");

        Mockito.verify(service)
                .deleteUser("1");
    }
}