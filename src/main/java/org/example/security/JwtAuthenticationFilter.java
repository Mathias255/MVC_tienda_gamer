package org.example.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        // 1. Si no hay cabecera de autenticación, continúa el flujo normal
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        jwt = authHeader.substring(7);

        try {
            // 2. Extraemos el email usando tu JwtService (retorna String)
            userEmail = jwtService.extractUsername(jwt);

            // 3. Si el email es válido y no hay autenticación previa activa
            if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                // Validamos el token pasando los parámetros exactos de tu firma: (String, String)
                if (jwtService.isTokenValid(jwt, userEmail)) {

                    // Creamos una autenticación rápida y segura basada en el email del token
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userEmail,
                            null,
                            Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
                    );

                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        } catch (Exception e) {
            // Entorno de desarrollo: si el token es simulado o inválido,
            // evitamos el clásico Error 500 y permitimos que llegue al controlador.
            System.out.println("[JWT Filter] Token de desarrollo o inválido ignorado: " + e.getMessage());
        }

        filterChain.doFilter(request, response);
    }
}