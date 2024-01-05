package com.tedameda.blogapp.security;

import org.springframework.security.web.authentication.AuthenticationFilter;

/**
 * @author TedaMeda
 * @since 1/5/2024
 */
public class JWTAuthenticationFilter extends AuthenticationFilter {
    public JWTAuthenticationFilter() {
        super(new JWTAuthenticationManager(), new JWTAuthenticationConverter());
    }
}
