package com.tedameda.blogapp.security;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author TedaMeda
 * @since 1/5/2024
 */
public class JWTServiceTests {
    JWTService jwtService = new JWTService();
    @Test
    void canCreateJwtFromUserId(){
        var jwt = jwtService.createJwt(1001L);
        assertNotNull(jwt);
    }
}

