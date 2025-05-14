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
        UserDetails adminUser = User.withUsername("admin")
                .password("{noop}admin") // {noop} for testing
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(adminUser);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())  // Disable CSRF for simplicity (enable in production)
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/").authenticated()  // Secure root path
                .requestMatchers("/login", "/login.html", "/css/**", "/js/**").permitAll()  // Allow login page and static resources
                .anyRequest().authenticated()  // Secure all other paths
            )
            .formLogin(form -> form
                .loginPage("/login.html")  // Custom login page
                .loginProcessingUrl("/login")  // Form submits to this URL
                .defaultSuccessUrl("/", true)  // Redirect to root after login
                .failureUrl("/login.html?error=true")  // Redirect back on failure
                .permitAll()
            )
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
            );

        return http.build();
    }
}