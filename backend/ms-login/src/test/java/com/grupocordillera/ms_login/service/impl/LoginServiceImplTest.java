package com.grupocordillera.ms_login.service.impl;

import com.grupocordillera.ms_login.config.security.JwtService;
import com.grupocordillera.ms_login.dto.AuthResponseDTO;
import com.grupocordillera.ms_login.dto.CreateUserDTO;
import com.grupocordillera.ms_login.dto.LoginResponseDTO;
import com.grupocordillera.ms_login.dto.LoginUpdateDTO;
import com.grupocordillera.ms_login.dto.RegisterDTO;
import com.grupocordillera.ms_login.exception.LoginException;
import com.grupocordillera.ms_login.model.Login;
import com.grupocordillera.ms_login.model.Rol;
import com.grupocordillera.ms_login.repository.LoginRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoginServiceImplTest {

    @Mock
    private LoginRepository repository;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private LoginServiceImpl service;

    @Test
    void testCreateClient() {

        RegisterDTO dto = new RegisterDTO();
        dto.setName("Patricio");
        dto.setLastname("Perez");
        dto.setEmail("pato@test.cl");
        dto.setPassword("123456");

        Login savedUser = new Login();
        savedUser.setId("1");
        savedUser.setName(dto.getName());
        savedUser.setLastname(dto.getLastname());
        savedUser.setEmail(dto.getEmail());
        savedUser.setPassword(dto.getPassword());
        savedUser.setRole(Rol.CLIENTE);

        when(repository.findByEmail(dto.getEmail())).thenReturn(Optional.empty());
        when(repository.save(any(Login.class))).thenReturn(savedUser);

        LoginResponseDTO result = service.createClient(dto);

        assertNotNull(result);
        assertEquals("1", result.getId());
        assertEquals("Patricio", result.getName());
        assertEquals("Perez", result.getLastname());
        assertEquals("pato@test.cl", result.getEmail());
        assertEquals(Rol.CLIENTE, result.getRole());

        verify(repository).findByEmail(dto.getEmail());
        verify(repository).save(any(Login.class));
    }

    @Test
    void testCreateClientWithExistingEmail() {

        RegisterDTO dto = new RegisterDTO();
        dto.setName("Patricio");
        dto.setLastname("Perez");
        dto.setEmail("pato@test.cl");
        dto.setPassword("123456");

        Login existingUser = new Login();
        existingUser.setId("1");
        existingUser.setEmail(dto.getEmail());

        when(repository.findByEmail(dto.getEmail()))
                .thenReturn(Optional.of(existingUser));

        LoginException exception = assertThrows(
                LoginException.class,
                () -> service.createClient(dto)
        );

        assertEquals(
                "El correo ya esta registrado",
                exception.getMessage()
        );

        verify(repository).findByEmail(dto.getEmail());
        verify(repository, never()).save(any(Login.class));
    }

    @Test
    void testLogin() {
        String email = "pato@test.cl";
        String password = "123456";
        String token = "jwt-token-test";

        Login user = new Login();
        user.setId("1");
        user.setName("Patricio");
        user.setLastname("Perez");
        user.setEmail(email);
        user.setPassword(password);
        user.setRole(Rol.CLIENTE);

        when(repository.findByEmail(email)).thenReturn(Optional.of(user));
        when(jwtService.generateToken(user)).thenReturn(token);

        AuthResponseDTO response = service.login(email, password);

        assertNotNull(response);
        assertNotNull(response.getUser());
        assertEquals(token, response.getToken());
        assertEquals("1", response.getUser().getId());
        assertEquals("Patricio", response.getUser().getName());
        assertEquals("Perez", response.getUser().getLastname());
        assertEquals(email, response.getUser().getEmail());
        assertEquals(Rol.CLIENTE, response.getUser().getRole());

       verify(repository).findByEmail(email);
        verify(jwtService).generateToken(user);
    }

    @Test
    void testLoginUserNotFound() {

    String email = "pato@test.cl";
    String password = "123456";

    when(repository.findByEmail(email)).thenReturn(Optional.empty());

    LoginException exception = assertThrows(
            LoginException.class,
            () -> service.login(email, password)
    );

    assertEquals("Usuario no encontrado", exception.getMessage());

    verify(repository).findByEmail(email);
    verifyNoInteractions(jwtService);
    }

    @Test
    void testLoginInvalidPassword() {

        String email = "pato@test.cl";
        String password = "123456";

        Login user = new Login();
        user.setId("1");
        user.setName("Patricio");
        user.setLastname("Perez");
        user.setEmail(email);
        user.setPassword("otraPassword");
        user.setRole(Rol.CLIENTE);

        when(repository.findByEmail(email)).thenReturn(Optional.of(user));

        LoginException exception = assertThrows(
                LoginException.class,
                () -> service.login(email, password)
        );

        assertEquals("Contraseña incorrecta", exception.getMessage());

        verify(repository).findByEmail(email);
        verify(jwtService, never()).generateToken(any(Login.class));
    }

    @Test
    void testGetAllUsers() {
    
        Login user1 = new Login();
        user1.setId("1");
        user1.setName("Patricio");
        user1.setLastname("Perez");
        user1.setEmail("pato@test.cl");
        user1.setRole(Rol.CLIENTE);

        Login user2 = new Login();
        user2.setId("2");
        user2.setName("Juan");
        user2.setLastname("Gonzalez");
        user2.setEmail("juan@test.cl");
        user2.setRole(Rol.VENTAS);

        when(repository.findAll()).thenReturn(java.util.List.of(user1, user2));

        java.util.List<LoginResponseDTO> result = service.getAllUsers();

        assertNotNull(result);
        assertEquals(2, result.size());

        assertEquals("1", result.get(0).getId());
        assertEquals("Patricio", result.get(0).getName());
        assertEquals(Rol.CLIENTE, result.get(0).getRole());

        assertEquals("2", result.get(1).getId());
        assertEquals("Juan", result.get(1).getName());
        assertEquals(Rol.VENTAS, result.get(1).getRole());

        verify(repository).findAll();
    }

    @Test
    void testGetUserById() {

        String id = "1";

        Login user = new Login();
        user.setId(id);
        user.setName("Patricio");
        user.setLastname("Perez");
        user.setEmail("pato@test.cl");
        user.setRole(Rol.CLIENTE);

        when(repository.findById(id)).thenReturn(Optional.of(user));

        LoginResponseDTO result = service.getUserById(id);

        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals("Patricio", result.getName());
        assertEquals("Perez", result.getLastname());
        assertEquals("pato@test.cl", result.getEmail());
        assertEquals(Rol.CLIENTE, result.getRole());

        verify(repository).findById(id);
    }

    @Test
    void testGetUserByIdNotFound() {

        String id = "999";

        when(repository.findById(id)).thenReturn(Optional.empty());

        LoginException exception = assertThrows(
                LoginException.class,
                () -> service.getUserById(id)
        );

        assertEquals("Usuario no encontrado", exception.getMessage());

        verify(repository).findById(id);
    }
    
    @Test
    void testCreateUser() {

        CreateUserDTO dto = new CreateUserDTO();
        dto.setName("Patricio");
        dto.setLastname("Perez");
        dto.setEmail("pato@test.cl");
        dto.setPassword("123456");
        dto.setRole(Rol.VENTAS);

        Login savedUser = new Login();
        savedUser.setId("1");
        savedUser.setName(dto.getName());
        savedUser.setLastname(dto.getLastname());
        savedUser.setEmail(dto.getEmail());
        savedUser.setPassword(dto.getPassword());
        savedUser.setRole(dto.getRole());

        when(repository.findByEmail(dto.getEmail())).thenReturn(Optional.empty());
        when(repository.save(any(Login.class))).thenReturn(savedUser);

        LoginResponseDTO result = service.createUser(dto);

        assertNotNull(result);
        assertEquals("1", result.getId());
        assertEquals("Patricio", result.getName());
        assertEquals("Perez", result.getLastname());
        assertEquals("pato@test.cl", result.getEmail());
        assertEquals(Rol.VENTAS, result.getRole());

        verify(repository).findByEmail(dto.getEmail());
        verify(repository).save(any(Login.class));
    }
    
    @Test
    void testCreateUserEmailAlreadyExists() {

        CreateUserDTO dto = new CreateUserDTO();
        dto.setName("Patricio");
        dto.setLastname("Perez");
        dto.setEmail("pato@test.cl");
        dto.setPassword("123456");
        dto.setRole(Rol.VENTAS);

        Login existingUser = new Login();
        existingUser.setId("1");
        existingUser.setEmail(dto.getEmail());

        when(repository.findByEmail(dto.getEmail())).thenReturn(Optional.of(existingUser));


        LoginException exception = assertThrows(
                LoginException.class,
                () -> service.createUser(dto)
        );

        assertEquals("El correo ya esta registrado", exception.getMessage());


        verify(repository).findByEmail(dto.getEmail());
        verify(repository, never()).save(any(Login.class));
    }    

@Test
void testUpdateUser() {

    String id = "1";

    Login existing = new Login();
    existing.setId(id);
    existing.setName("Old");
    existing.setLastname("User");
    existing.setEmail("old@test.com");
    existing.setRole(Rol.CLIENTE);

    LoginUpdateDTO dto = new LoginUpdateDTO();
    dto.setName("New");
    dto.setLastname("User");
    dto.setEmail("new@test.com");

    Login updated = new Login();
    updated.setId(id);
    updated.setName(dto.getName());
    updated.setLastname(dto.getLastname());
    updated.setEmail(dto.getEmail());
    updated.setRole(Rol.CLIENTE);

    when(repository.findById(id)).thenReturn(Optional.of(existing));
    when(repository.findByEmail(dto.getEmail())).thenReturn(Optional.empty());
    when(repository.save(any(Login.class))).thenReturn(updated);

    LoginResponseDTO result = service.updateUser(id, dto);

    assertNotNull(result);
    assertEquals("New", result.getName());
    assertEquals("new@test.com", result.getEmail());

    verify(repository).findById(id);
    verify(repository).save(any(Login.class));
}

@Test
void testUpdateUserNotFound() {

    String id = "999";

    LoginUpdateDTO dto = new LoginUpdateDTO();
    dto.setName("New");
    dto.setLastname("User");
    dto.setEmail("new@test.com");

    when(repository.findById(id)).thenReturn(Optional.empty());

    LoginException ex = assertThrows(
            LoginException.class,
            () -> service.updateUser(id, dto)
    );

    assertEquals("Usuario no encontrado", ex.getMessage());

    verify(repository).findById(id);
    verify(repository, never()).save(any(Login.class));
}

@Test
void testUpdateUserEmptyFields() {

    String id = "1";

    Login existing = new Login();
    existing.setId(id);

    LoginUpdateDTO dto = new LoginUpdateDTO();
    dto.setName(" ");
    dto.setLastname(" ");
    dto.setEmail(" ");

    when(repository.findById(id)).thenReturn(Optional.of(existing));

    LoginException ex = assertThrows(
            LoginException.class,
            () -> service.updateUser(id, dto)
    );

    assertEquals("Campos obligatorios vacíos", ex.getMessage());

    verify(repository).findById(id);
}

@Test
void testUpdateUserEmailAlreadyUsed() {

    String id = "1";

    Login existing = new Login();
    existing.setId(id);

    Login otherUser = new Login();
    otherUser.setId("2");
    otherUser.setEmail("used@test.com");

    LoginUpdateDTO dto = new LoginUpdateDTO();
    dto.setName("Test");
    dto.setLastname("User");
    dto.setEmail("used@test.com");

    when(repository.findById(id)).thenReturn(Optional.of(existing));
    when(repository.findByEmail(dto.getEmail())).thenReturn(Optional.of(otherUser));

    LoginException ex = assertThrows(
            LoginException.class,
            () -> service.updateUser(id, dto)
    );

    assertEquals("El correo ya esta en uso", ex.getMessage());

    verify(repository).findById(id);
}

@Test
void testDeleteUser() {

    String id = "1";

    Login user = new Login();
    user.setId(id);
    user.setRole(Rol.CLIENTE);

    when(repository.findById(id)).thenReturn(Optional.of(user));

    service.deleteUser(id);

    verify(repository).findById(id);
    verify(repository).deleteById(id);
}

@Test
void testDeleteUserNotFound() {

    String id = "999";

    when(repository.findById(id)).thenReturn(Optional.empty());

    LoginException ex = assertThrows(
            LoginException.class,
            () -> service.deleteUser(id)
    );

    assertEquals("Usuario no encontrado", ex.getMessage());

    verify(repository).findById(id);
    verify(repository, never()).deleteById(anyString());
}


}