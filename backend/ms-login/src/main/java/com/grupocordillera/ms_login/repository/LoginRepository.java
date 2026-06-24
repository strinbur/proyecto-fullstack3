package com.grupocordillera.ms_login.repository;

import com.grupocordillera.ms_login.model.Login;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

/**
 * Repositorio para operaciones CRUD sobre la entidad {@link Login} en MongoDB.
 */
public interface LoginRepository extends MongoRepository<Login, String> {

    /**
     * Busca un usuario por correo electrónico.
     *
     * @param email correo electrónico del usuario
     * @return usuario envuelto en {@link Optional} si existe
     */
    Optional<Login> findByEmail(String email);

}