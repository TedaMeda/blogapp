package com.tedameda.blogapp.security;

import com.tedameda.blogapp.users.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;

/**
 * @author TedaMeda
 * @since 12/31/2023
 */
@Configuration
@EnableWebSecurity
public class AppSecurityConfig{
    private final JWTAuthenticationFilter jwtAuthenticationFilter;
    private final JWTService jwtService;
    private final UserService userService;

    public AppSecurityConfig(JWTAuthenticationFilter jwtAuthenticationFilter, JWTService jwtService, UserService userService) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.jwtService = jwtService;
        this.userService = userService;
    }
    @Bean
    public JWTAuthenticationFilter jwtAuthenticationFilter() throws Exception{
        return new JWTAuthenticationFilter(
                new JWTAuthenticationManager(jwtService,userService)
        );
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.csrf().disable();//TODO: find alternative way to replace this
        http.authorizeHttpRequests((authorize) -> authorize
                .requestMatchers(HttpMethod.POST,"/users","/users/login").permitAll()
                .requestMatchers(HttpMethod.GET, "/articles", "/articles/*").permitAll()
                .anyRequest().authenticated()
        ).httpBasic(Customizer.withDefaults());
//                .formLogin(Customizer.withDefaults());
        http.addFilterBefore(jwtAuthenticationFilter, AnonymousAuthenticationFilter.class);
        return http.build();
    }


}
