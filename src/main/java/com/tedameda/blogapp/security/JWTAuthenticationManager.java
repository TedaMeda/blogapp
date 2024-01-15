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
    private final JWTService jwtService;
    private final UserService userService;

    public JWTAuthenticationManager(JWTService jwtService, UserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if(authentication instanceof JWTAuthentication jwtAuthentication){
            // down casting
            var jwt = jwtAuthentication.getCredentials();
            var userId = jwtService.retrieveUserId(jwt);

            jwtAuthentication.userEntity = userService.getUser(userId);
            jwtAuthentication.setAuthenticated(true);

            return jwtAuthentication;
        }
        throw new IllegalAccessError("Cannot authenticate this non-JWT authentication");
    }
}
