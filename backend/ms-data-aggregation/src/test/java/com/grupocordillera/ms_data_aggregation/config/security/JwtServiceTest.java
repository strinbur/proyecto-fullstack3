package com.grupocordillera.ms_data_aggregation.config.security;

import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.Test;
import com.grupocordillera.ms_data_aggregation.config.security.JwtService;

import static org.junit.jupiter.api.Assertions.*;


class JwtServiceTest {


    private final JwtService jwtService = new JwtService();


    @Test
    void isTokenValid_shouldReturnFalseForInvalidToken() {


        boolean result = jwtService.isTokenValid("bad-token");


        assertFalse(result);
    }
}