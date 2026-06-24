package com.grupocordillera.ms_reporting.config.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import javax.crypto.SecretKey;


@Service
public class JwtService {


    @Value("${jwt.secret.key}")
    private String secretKey;


    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }


    public Claims extractClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigningKey())
    
                /**
                 * Servicio de utilidad para operaciones con JWT: extracción de claims,
                 * validación y lectura de campos como email y role.
                 */
                .build()
                .parseClaimsJws(token)
                .getBody();
    }


    public String extractEmail(String token) {
        return extractClaims(token).getSubject();
                /**
                 * Parsea y devuelve los claims contenidos en el token JWT.
                 *
                 * @param token token JWT
                 * @return claims del token
                 */
    }


    public String extractRole(String token) {
        return extractClaims(token).get("role", String.class);
    }


    public boolean isTokenValid(String token) {
                /**
                 * Extrae el subject (email) del token.
                 *
                 * @param token token JWT
                 * @return email contenido en el subject
                 */
        try {
            extractClaims(token);
            return true;
        } catch (Exception e) {
                /**
                 * Extrae el rol contenido en los claims del token.
                 *
                 * @param token token JWT
                 * @return rol como cadena
                 */
            e.printStackTrace();
            return false;
        }
    }
                /**
                 * Valida que el token sea parseable y esté firmado con la clave correcta.
                 *
                 * @param token token JWT
                 * @return {@code true} si el token es válido, {@code false} en caso contrario
                 */
}
