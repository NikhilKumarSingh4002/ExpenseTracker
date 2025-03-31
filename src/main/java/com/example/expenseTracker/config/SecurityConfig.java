package com.example.expenseTracker.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

@Configuration
public class SecurityConfig {

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails adminUser = User.withUsername("admin")
                .password("{noop}admin") // {noop} disables password encoding (for testing only)
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(adminUser);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable) // Correct way to disable CSRF in Spring Boot 3.x
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/").authenticated() // Secure "/" (main page)
                .anyRequest().permitAll() // Allow everything else (CSS, JS, etc.)
            )
            .httpBasic(httpBasic -> {}) // Enables HTTP Basic Authentication
            .sessionManagement(session -> session.sessionCreationPolicy(
                org.springframework.security.config.http.SessionCreationPolicy.STATELESS
            ));

        return http.build();
    }
}

