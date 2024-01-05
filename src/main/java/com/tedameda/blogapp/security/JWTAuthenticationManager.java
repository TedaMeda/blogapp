package com.tedameda.blogapp.security;

import com.tedameda.blogapp.users.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 * @author TedaMeda
 * @since 1/5/2024
 */
public class JWTAuthenticationManager implements AuthenticationManager{
    private JWTService jwtService;
    private UserService userService;

    public JWTAuthenticationManager(JWTService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if(authentication instanceof JWTAuthentication){
            var jwtAuthentication = (JWTAuthentication)authentication; // down casting
            var jwt = jwtAuthentication.getCredentials();
            var userId = jwtService.retrieveUserId(jwt);
            var userEntity = userService.getUser(userId);
            jwtAuthentication.userEntity = userEntity;
            jwtAuthentication.setAuthenticated(true);
            return jwtAuthentication;
        }
    }
}
