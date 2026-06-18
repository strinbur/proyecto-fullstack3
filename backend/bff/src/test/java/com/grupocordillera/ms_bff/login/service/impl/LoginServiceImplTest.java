package com.grupocordillera.ms_bff.login.service.impl;

import com.grupocordillera.ms_bff.login.client.LoginClient;
import com.grupocordillera.ms_bff.login.dto.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class LoginServiceImplTest {

    private final LoginClient client =
            Mockito.mock(LoginClient.class);

    private final LoginServiceImpl service =
            new LoginServiceImpl(client);

    @Test
    void shouldLogin() {

        LoginRequestDTO request =
                new LoginRequestDTO();

        AuthResponseDTO dto =
                new AuthResponseDTO();

        Mockito.when(client.login(request))
                .thenReturn(dto);

        AuthResponseDTO response =
                service.login(request);

        assertNotNull(response);
    }

    @Test
    void shouldCreateClient() {

        LoginRegisterDTO request =
                new LoginRegisterDTO();

        LoginResponseDTO dto =
                new LoginResponseDTO();

        Mockito.when(client.createClient(request))
                .thenReturn(dto);

        LoginResponseDTO response =
                service.createClient(request);

        assertNotNull(response);
    }

    @Test
    void shouldCreateUser() {

        LoginAdminCreateDTO request =
                new LoginAdminCreateDTO();

        LoginResponseDTO dto =
                new LoginResponseDTO();

        Mockito.when(client.createUser(request))
                .thenReturn(dto);

        LoginResponseDTO response =
                service.createUser(request);

        assertNotNull(response);
    }

    @Test
    void shouldUpdateUser() {

        LoginUpdateDTO request =
                new LoginUpdateDTO();

        LoginResponseDTO dto =
                new LoginResponseDTO();

        Mockito.when(client.updateUser("1", request))
                .thenReturn(dto);

        LoginResponseDTO response =
                service.updateUser("1", request);

        assertNotNull(response);
    }

    @Test
    void shouldGetUserById() {

        LoginResponseDTO dto =
                new LoginResponseDTO();

        Mockito.when(client.getUserById("1"))
                .thenReturn(dto);

        LoginResponseDTO response =
                service.getUserById("1");

        assertNotNull(response);
    }

    @Test
    void shouldGetAllUsers() {

        List<LoginResponseDTO> users =
                List.of(new LoginResponseDTO());

        Mockito.when(client.getAllUsers())
                .thenReturn(users);

        List<LoginResponseDTO> response =
                service.getAllUsers();

        assertNotNull(response);
    }

    @Test
    void shouldDeleteUser() {

        service.deleteUser("1");

        Mockito.verify(client)
                .deleteUser("1");
    }
}