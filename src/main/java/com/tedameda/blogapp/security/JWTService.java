package com.tedameda.blogapp.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Date;

/**
 * @author TedaMeda
 * @since 1/5/2024
 */
@Service
public class JWTService {
    //TODO Move the key to properties file
    private static final String JWT_KEY = "jh537bkv323ug98dby0agcen29g847ty9@5";
    private Algorithm algorithm = Algorithm.HMAC256(JWT_KEY);

    public String createJwt(Long userId){
        return JWT.create()
                .withSubject(userId.toString())
                .withIssuedAt(new Date())
//                .withExpiresAt() //TODO: add JWT expiry date
                .sign(algorithm);
    }

    public Long retrieveUserId(String jwtString){
        var decodedJWT = JWT.decode(jwtString);
        var userId = Long.valueOf(decodedJWT.getSubject());
        return userId;
    }

}
