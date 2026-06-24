package com.grupocordillera.ms_login.controller;

import com.grupocordillera.ms_login.dto.*;
import com.grupocordillera.ms_login.service.LoginService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para manejar las operaciones de inicio de sesión y gestión de usuarios.
 * <p>
 * Expone los endpoints de registro, autenticación, consulta, actualización y eliminación de usuarios.
 * </p>
 */
@RestController
@RequestMapping("/login")
@CrossOrigin
public class LoginController {

    private final LoginService service;

    /**
     * Construye una instancia de {@link LoginController} con el servicio de login inyectado.
     *
     * @param service servicio encargado de la lógica de autenticación y gestión de usuarios
     */
    public LoginController(LoginService service) {
        this.service = service;
    }

    /**
     * Registra un nuevo usuario con el rol {@code CLIENTE}.
     *
     * @param dto datos de registro del usuario
     * @return respuesta con los datos del usuario creado y estado HTTP 201
     */
    @PostMapping("/register")
    public ResponseEntity<LoginResponseDTO> createClient(
            @Valid @RequestBody RegisterDTO dto
    ) {

        return new ResponseEntity<>(
                service.createClient(dto),
                HttpStatus.CREATED
        );
    }

    /**
     * Autentica un usuario y devuelve el token o la información de sesión.
     *
     * @param request datos de autenticación del usuario
     * @return respuesta con el token de autenticación y estado HTTP 200
     */
    @PostMapping("/auth")
    public ResponseEntity<AuthResponseDTO> login(
            @Valid @RequestBody LoginRequestDTO request
    ) {

        return ResponseEntity.ok(
                service.login(
                        request.getEmail(),
                        request.getPassword()
                )
        );
    }

    /**
     * Obtiene todos los usuarios registrados.
     * <p>
     * Este endpoint está pensado para roles administrativos y de ventas.
     * </p>
     *
     * @return lista de usuarios con estado HTTP 200
     */
    //@PreAuthorize("hasAnyRole('ADMIN','VENTAS')")
    @GetMapping
    public ResponseEntity<List<LoginResponseDTO>> getAllUsers() {

        return ResponseEntity.ok(
                service.getAllUsers()
        );
    }

    /**
     * Obtiene un usuario por su identificador.
     *
     * @param id identificador del usuario
     * @return datos del usuario con estado HTTP 200
     */
    //@PreAuthorize("hasAnyRole('ADMIN','VENTAS')")
    @GetMapping("/{id}")
    public ResponseEntity<LoginResponseDTO> getUserById(
            @PathVariable String id
    ) {

        return ResponseEntity.ok(
                service.getUserById(id)
        );
    }

    /**
     * Actualiza los datos de un usuario específico sin modificar su rol.
     *
     * @param id identificador del usuario a actualizar
     * @param dto datos de actualización del usuario
     * @return datos del usuario actualizado con estado HTTP 200
     */
    //@PreAuthorize("hasAnyRole('ADMIN','VENTAS','CLIENTE')")
    @PutMapping("/{id}")
    public ResponseEntity<LoginResponseDTO> updateUser(
            @PathVariable String id,
            @Valid @RequestBody LoginUpdateDTO dto
    ) {

        return ResponseEntity.ok(
                service.updateUser(id, dto)
        );
    }

    /**
     * Elimina un usuario por su identificador.
     *
     * @param id identificador del usuario a eliminar
     * @return respuesta sin contenido con estado HTTP 204
     */
    //@PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(
            @PathVariable String id
    ) {

        service.deleteUser(id);

        return ResponseEntity.noContent().build();
    }

    /**
     * Crea un nuevo usuario con un rol específico.
     *
     * @param dto datos de creación del usuario, incluyendo el rol asignado
     * @return respuesta con los datos del usuario creado y estado HTTP 201
     */
    //@PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin/create")
    public ResponseEntity<LoginResponseDTO> createUser(
            @Valid @RequestBody CreateUserDTO dto
    ) {

        return new ResponseEntity<>(
                service.createUser(dto),
                HttpStatus.CREATED
        );
    }
}