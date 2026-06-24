package com.grupocordillera.ms_login.config.security;

import com.grupocordillera.ms_login.model.Login;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${jwt.secret.key}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long expirationTime;

    private SecretKey getSigningKey() {

        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public String generateToken(Login usuario) {

        return Jwts.builder()
                .setSubject(usuario.getEmail())
                .claim("role", usuario.getRole().name())
                .claim("name", usuario.getName())
                .claim("lastname", usuario.getLastname())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(
                        new Date(System.currentTimeMillis() + expirationTime)
                )
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

    public String extractEmail(String token) {

        return extractClaims(token).getSubject();
    }

    public <T> T extractClaim(
            String token,
            Function<Claims, T> claimsResolver
    ) {

        final Claims claims = extractClaims(token);

        return claimsResolver.apply(claims);
    }

    public boolean isTokenValid(String token) {

        try {

            return !isTokenExpired(token);

        } catch (Exception e) {

            return false;
        }
    }

    private boolean isTokenExpired(String token) {

        return extractClaims(token)
                .getExpiration()
                .before(new Date());
    }
}