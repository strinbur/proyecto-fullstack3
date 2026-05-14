package com.grupocordillera.ms_inventory.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    public JwtAuthenticationFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        final String authHeader =
                request.getHeader("Authorization");

        // NO HAY TOKEN
        if (authHeader == null ||
                !authHeader.startsWith("Bearer ")) {

            filterChain.doFilter(request, response);
            return;
        }

        try {

            final String token =
                    authHeader.substring(7);

            // TOKEN INVALIDO
            if (!jwtService.isTokenValid(token)) {

                response.setStatus(
                        HttpServletResponse.SC_UNAUTHORIZED
                );

                response.getWriter()
                        .write("Token invalido");

                return;
            }

            // SI NO ESTA AUTENTICADO
            if (SecurityContextHolder
                    .getContext()
                    .getAuthentication() == null) {

                String correo =
                        jwtService.extractCorreo(token);

                String rol =
                        jwtService.extractRol(token);

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                correo,
                                null,
                                List.of(
                                        new SimpleGrantedAuthority(
                                                "ROLE_" + rol
                                        )
                                )
                        );

                authentication.setDetails(
                        new WebAuthenticationDetailsSource()
                                .buildDetails(request)
                );

                SecurityContextHolder
                        .getContext()
                        .setAuthentication(authentication);
            }

            filterChain.doFilter(request, response);

        } catch (Exception e) {

            response.setStatus(
                    HttpServletResponse.SC_UNAUTHORIZED
            );

            response.getWriter()
                    .write("Token invalido");
        }
    }
}