package com.grupocordillera.ms_reporting.config.security;

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


        final String authHeader = request.getHeader("Authorization");


        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }


        try {


            final String token = authHeader.substring(7);


            if (!jwtService.isTokenValid(token)) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");
                response.getWriter().write("Token invalido (isTokenValid=false)");
                return;
            }


            if (SecurityContextHolder.getContext().getAuthentication() == null) {


                String email = jwtService.extractEmail(token);
                String role = jwtService.extractRole(token);


                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                email,
                                null,
                                List.of(new SimpleGrantedAuthority("ROLE_" + role))
                        );


                authentication.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );


                SecurityContextHolder.getContext().setAuthentication(authentication);
            }


            filterChain.doFilter(request, response);


        } catch (Exception e) {


            e.printStackTrace();


            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");


            response.getWriter().write(
                    "Token invalido: " + e.getMessage()
            );


            return;
        }
    }
}
