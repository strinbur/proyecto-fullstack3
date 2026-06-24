package com.grupocordillera.ms_inventory.config.security;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtFilter;

    public SecurityConfig(
            JwtAuthenticationFilter jwtFilter
    ) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http
    ) throws Exception {

        http
            // DESACTIVAR CSRF
            .csrf(csrf -> csrf.disable())

            // SIN SESIONES
            .sessionManagement(session ->
                    session.sessionCreationPolicy(
                            SessionCreationPolicy.STATELESS
                    )
            )

            .exceptionHandling(ex -> ex

                    .authenticationEntryPoint((request, response, authException) -> {

                        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

                        response.getWriter().write("""
                            {
                                "status": 401,
                                "errores": [
                                    "Token no proporcionado o invalido"
                                ]
                            }
                        """);
                    })

                    .accessDeniedHandler((request, response, accessDeniedException) -> {

                        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

                        response.getWriter().write("""
                            {
                                "status": 403,
                                "errores": [
                                    "No tienes permisos para acceder"
                                ]
                            }
                        """);
                    })
            )

            // AUTHORIZATION
            .authorizeHttpRequests(auth -> auth

                    // SWAGGER
                    .requestMatchers(
                            "/swagger-ui/**",
                            "/v3/api-docs/**",
                            "/actuator/**"
                    ).permitAll()

                    // PUBLIC GET
                    .requestMatchers(
                            HttpMethod.GET,
                            "/inventory/**"
                    ).permitAll()

                    // TODO LO DEMAS REQUIERE JWT
                    .anyRequest().authenticated()
            )

            // JWT FILTER
            .addFilterBefore(
                    jwtFilter,
                    UsernamePasswordAuthenticationFilter.class
            );

        return http.build();
    }
}