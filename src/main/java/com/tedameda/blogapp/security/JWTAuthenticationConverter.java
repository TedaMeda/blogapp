package com.tedameda.blogapp.security;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationConverter;

/**
 * @author TedaMeda
 * @since 1/5/2024
 */
public class JWTAuthenticationConverter implements AuthenticationConverter {

    @Override
    public Authentication convert(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if(authHeader==null || !authHeader.startsWith("Bearer ")){
            return null;
        }

        var jwt = authHeader.substring(7);
        return new JWTAuthentication(jwt);
    }
}
