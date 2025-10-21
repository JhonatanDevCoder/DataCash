package com.datacash.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .cors(withDefaults()) // Habilita la configuración de CORS definida abajo
            .csrf(csrf -> csrf.disable()) // Deshabilitar CSRF para APIs REST
            .authorizeHttpRequests(auth -> auth
                // Permite acceso público a estas rutas
                .requestMatchers("/api/auth/**", "/api/usuarios/**").permitAll() 
                .requestMatchers("/", "/index.html", "/static/**").permitAll()
                // Cualquier otra solicitud debe ser autenticada (puedes ajustar esto más tarde)
                .anyRequest().permitAll() // Por ahora, permitimos todo para que sea fácil de probar
            );
        return http.build();
    }
}