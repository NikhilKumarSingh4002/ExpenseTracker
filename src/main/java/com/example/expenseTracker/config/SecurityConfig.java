package com.example.expenseTracker.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
public class SecurityConfig {

    @Bean
    public UserDetailsService userDetailsService() {
        // Creating an admin user for in-memory authentication
        UserDetails adminUser = User.withUsername("admin")
                .password("{noop}admin") // {noop} disables password encoding (for testing only)
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(adminUser);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())  // Disable CSRF protection (optional for testing)
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/").authenticated()  // Secure root path with authentication
                .requestMatchers("/index.html").permitAll()  // Allow index.html without authentication
                .anyRequest().permitAll()  // Allow other resources
            )
            .formLogin(form -> form
                .permitAll()  // Use Spring Security's default login page
            )
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)  // Allow session creation if needed
            );

        return http.build();
    }
}
