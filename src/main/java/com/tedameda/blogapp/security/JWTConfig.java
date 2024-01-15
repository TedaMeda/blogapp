package com.tedameda.blogapp.security;

import com.tedameda.blogapp.users.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author TedaMeda
 * @since 1/13/2024
 */

@Configuration
public class JWTConfig {

    private final JWTService jwtService;
    private final UserService userService;

    public JWTConfig(JWTService jwtService, UserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @Bean
    public JWTAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        return new JWTAuthenticationFilter(
                new JWTAuthenticationManager(jwtService, userService));
    }
}