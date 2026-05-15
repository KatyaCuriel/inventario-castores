package com.castores.inventario.config;

import com.castores.inventario.service.CustomUserDetailsService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return org.springframework.security.crypto.password.NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        http
                .userDetailsService(customUserDetailsService)

                .authorizeHttpRequests(auth -> auth

                        // ADMIN
                        .requestMatchers(
                                "/productos/nuevo",
                                "/productos/guardar",
                                "/productos/entrada/**",
                                "/movimientos/**"
                        ).hasRole("ADMIN")

                        // ALMACENISTA
                        .requestMatchers(
                                "/productos/salida/**"
                        ).hasRole("ALMACENISTA")

                        // AMBOS
                        .requestMatchers(
                                "/productos",
                                "/productos/"
                        ).hasAnyRole("ADMIN", "ALMACENISTA")

                        .anyRequest()
                        .authenticated()
                )

                .formLogin(form -> form
                        .loginPage("/login")
                        .usernameParameter("correo")
                        .defaultSuccessUrl("/productos", true)
                        .permitAll()
                )

                .logout(logout -> logout
                        .logoutSuccessUrl("/login")
                        .permitAll()
                );

        return http.build();
    }
}