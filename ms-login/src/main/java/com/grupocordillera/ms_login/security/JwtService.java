package com.grupocordillera.ms_login.security;

import com.grupocordillera.ms_login.model.Login;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey; // Cambiado de Key a SecretKey
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {

    // La clave debe ser de al menos 256 bits para HS256
    private static final String SECRET_KEY = "miclavesupersecretaparajwtfullstack3miclavesupersecretaparajwtfullstack3";

    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 24; // 1 día

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    public String generateToken(Login usuario) {
        return Jwts.builder()
                .setSubject(usuario.getCorreo())
                .claim("rol", usuario.getRol().name())
                .claim("nombre", usuario.getNombre())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractCorreo(String token) {
        return extractClaims(token).getSubject();
    }

    // Método útil para extraer cualquier claim de forma genérica
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractClaims(token);
        return claimsResolver.apply(claims);
    }

    public boolean isTokenValid(String token) {
        try {
            // Verificamos que el token se pueda parsear y que no haya expirado
            return !isTokenExpired(token);
        } catch (Exception e) {
            return false;
        }
    }

    private boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }
}