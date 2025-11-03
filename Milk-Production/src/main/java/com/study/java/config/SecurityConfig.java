package com.study.java.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.Customizer;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // disable for REST APIs (use with caution)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/**").permitAll() // allow unauthenticated access to your API endpoints
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults()); // enable Basic auth for other endpoints

        return http.build();
    }
}
