package com.example.jwt_restapi.config;

import com.example.jwt_restapi.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;

@Configuration
@EnableWebSecurity
public class AppConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request -> request
                        .requestMatchers(HttpMethod.POST, "/registrar").permitAll()
                        .requestMatchers(HttpMethod.GET, "/logar").permitAll()
                        .requestMatchers(HttpMethod.GET, "/nivel/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/logar.html").permitAll()
                        .requestMatchers(HttpMethod.GET, "/registrar.html").permitAll()
                        .requestMatchers(HttpMethod.GET, "/home.html").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/usuario/**").hasRole("ADMIN") // Apenas ADMIN pode deletar usuários
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
