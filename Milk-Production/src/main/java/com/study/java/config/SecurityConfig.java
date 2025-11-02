package com.study.java.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // disable CSRF for simplicity in APIs
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/public/**").permitAll() // allow public endpoints
                        .anyRequest().authenticated() // everything else requires login
                );
            //    .httpBasic(); // enables basic auth (username/password in header)

        return http.build();
    }

}
