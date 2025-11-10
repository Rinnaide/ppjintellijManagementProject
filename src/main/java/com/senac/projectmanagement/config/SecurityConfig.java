package com.senac.projectmanagement.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.senac.projectmanagement.security.JwtAuthenticationFilter;
import com.senac.projectmanagement.security.UserDetailsServiceImpl;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final UserDetailsServiceImpl userDetailsService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private Environment env;

    public SecurityConfig(UserDetailsServiceImpl userDetailsService,
                          JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.userDetailsService = userDetailsService;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    // Public endpoints (no authentication required)
    private String[] getPublicEndpoints() {
        java.util.List<String> endpoints = new java.util.ArrayList<>(java.util.Arrays.asList(
            "/api/auth/login",
            "/api/users/login",
            "/api/users"  // POST for registration
        ));

        // Add Swagger endpoints only in development environment
        String[] activeProfiles = env.getActiveProfiles();
        boolean isDevelopment = java.util.Arrays.asList(activeProfiles).contains("dev") ||
                                java.util.Arrays.asList(activeProfiles).contains("development") ||
                                activeProfiles.length == 0; // default to dev if no profile set

        if (isDevelopment) {
            endpoints.addAll(java.util.Arrays.asList(
                "/swagger-ui.html",
                "/swagger-ui/**",
                "/v3/api-docs/**",
                "/swagger-resources/**",
                "/webjars/**"
            ));
        }

        return endpoints.toArray(new String[0]);
    }



    // Endpoints requiring authentication
    public static final String[] ENDPOINTS_WITH_AUTHENTICATION_REQUIRED = {
            "/api/categories/**",
            "/api/transactions/**",
            "/api/user-balances/**",
            "/api/notifications/**",
            "/api/user-roles/**"
    };

    // Admin role only endpoints
    public static final String[] ENDPOINTS_ADMIN = {
            "/api/users/**",  // Excluding POST which is public
            "/api/roles/**",
            "/api/audit-logs/**",
            "/api/password-reset-tokens/**",
            "/api/refresh-tokens/**",
            "/api/sync-statuses/**"
    };

    @Bean
    public DaoAuthenticationProvider authenticationProvider(PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(DaoAuthenticationProvider authenticationProvider) {
        return authenticationProvider::authenticate;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // Determine public endpoints based on environment
        String[] publicEndpoints = getPublicEndpoints();

        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(publicEndpoints).permitAll()
                        .requestMatchers(ENDPOINTS_ADMIN).hasRole("ADMINISTRATOR")
                        .requestMatchers(ENDPOINTS_WITH_AUTHENTICATION_REQUIRED).authenticated()
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