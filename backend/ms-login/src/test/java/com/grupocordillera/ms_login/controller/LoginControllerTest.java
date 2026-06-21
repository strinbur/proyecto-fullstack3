package com.grupocordillera.ms_login.controller;

import com.grupocordillera.ms_login.dto.*;
import com.grupocordillera.ms_login.service.LoginService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoginControllerTest {

    @Mock
    private LoginService service;

    @InjectMocks
    private LoginController controller;

    private LoginResponseDTO response;

    @BeforeEach
    void setup() {
        response = new LoginResponseDTO();
    }

    @Test
    void register_shouldReturnCreated() {

        RegisterDTO dto = new RegisterDTO();

        when(service.createClient(dto)).thenReturn(response);

        LoginResponseDTO result = controller.createClient(dto).getBody();

        assertNotNull(result);
        verify(service, times(1)).createClient(dto);
    }

    @Test
    void login_shouldReturnAuth() {

        LoginRequestDTO request = new LoginRequestDTO();
        request.setEmail("test@test.com");
        request.setPassword("1234");

        AuthResponseDTO auth = new AuthResponseDTO();

        when(service.login("test@test.com", "1234")).thenReturn(auth);

        AuthResponseDTO result = controller.login(request).getBody();

        assertNotNull(result);
        verify(service, times(1)).login("test@test.com", "1234");
    }

    @Test
    void getAllUsers_shouldReturnList() {

        when(service.getAllUsers()).thenReturn(List.of(response));

        List<LoginResponseDTO> result = controller.getAllUsers().getBody();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(service, times(1)).getAllUsers();
    }

    @Test
    void getUserById_shouldReturnUser() {

        when(service.getUserById("1")).thenReturn(response);

        LoginResponseDTO result = controller.getUserById("1").getBody();

        assertNotNull(result);
        verify(service, times(1)).getUserById("1");
    }

    @Test
    void updateUser_shouldReturnUpdated() {

        LoginUpdateDTO dto = new LoginUpdateDTO();

        when(service.updateUser("1", dto)).thenReturn(response);

        LoginResponseDTO result = controller.updateUser("1", dto).getBody();

        assertNotNull(result);
        verify(service, times(1)).updateUser("1", dto);
    }

    @Test
    void deleteUser_shouldCallService() {

        doNothing().when(service).deleteUser("1");

        controller.deleteUser("1");

        verify(service, times(1)).deleteUser("1");
    }

    @Test
    void createUserAdmin_shouldReturnCreated() {

        CreateUserDTO dto = new CreateUserDTO();

        when(service.createUser(dto)).thenReturn(response);

        LoginResponseDTO result = controller.createUser(dto).getBody();

        assertNotNull(result);
        verify(service, times(1)).createUser(dto);
    }
}