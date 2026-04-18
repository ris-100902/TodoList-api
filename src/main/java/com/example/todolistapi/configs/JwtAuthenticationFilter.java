package com.example.todolistapi.configs;

import java.io.IOException;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.todolistapi.entity.Users;
import com.example.todolistapi.repository.UsersRepository;
import com.example.todolistapi.services.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{
    private final JwtService jwtService;
    private final UsersRepository usersRepository;

    public JwtAuthenticationFilter(JwtService jwtService, UsersRepository usersRepository) {
        this.jwtService = jwtService;
        this.usersRepository = usersRepository;
    }

    @Override
    @SuppressWarnings("UseSpecificCatch")
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            final String jwt = authHeader.substring(7);
            final String userEmail = jwtService.extractUserName(jwt);
            IO.println("token: "+jwt);

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (userEmail!=null && auth == null) {
                Users user = this.usersRepository.findByEmail(userEmail).orElseThrow(() -> new RuntimeException("No such user"));
                if (jwtService.isTokenValid(jwt, user)) {
                    UsernamePasswordAuthenticationToken newAuth = new UsernamePasswordAuthenticationToken(user, null, List.of());
                    SecurityContextHolder.getContext().setAuthentication(newAuth);
                }
            }
            filterChain.doFilter(request, response);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }
}