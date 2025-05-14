package com.example.expenseTracker.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(auth -> auth
                        // Allow login page and static resources to be accessed without authentication
                        .requestMatchers("/login.html", "/login.css", "/favicon.ico", "/js/**", "/css/**", "/*.css", "/*.js").permitAll()
                        // All other requests should be authenticated
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login.html")  // Custom login page
                        .loginProcessingUrl("/login")  // URL to process login form submission
                        .defaultSuccessUrl("/index.html", true)  // Redirect after successful login
                        .failureUrl("/login.html?error=true")  // Redirect on failure
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")  // URL to log out
                        .logoutSuccessUrl("/login.html?logout=true")  // Redirect after logout
                )
                .csrf(csrf -> csrf.disable());  // Disable CSRF for simplicity, you can enable it later if needed
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.withUsername("admin")
                .password("admin")  // Plaintext password for testing (use encoder in production)
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(user);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();  // No password encoding for testing (use a proper encoder in production)
    }
}
