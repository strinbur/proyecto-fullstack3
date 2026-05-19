package com.grupocordillera.ms_login.repository;

import com.grupocordillera.ms_login.model.Login;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface LoginRepository extends MongoRepository<Login, String> {

    Optional<Login> findByEmail(String email);

}