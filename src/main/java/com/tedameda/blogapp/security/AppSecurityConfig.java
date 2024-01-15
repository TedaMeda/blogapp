package com.tedameda.blogapp.security;

import com.tedameda.blogapp.users.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    public AppSecurityConfig(JWTAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.authorizeHttpRequests((authorize) -> authorize
                .requestMatchers(HttpMethod.POST,"/users","/users/login").permitAll()
                .requestMatchers(HttpMethod.GET, "/articles", "/articles/*").permitAll()
                        .requestMatchers("/h2-console", "/h2-console/**").permitAll()
                .anyRequest().authenticated()
        ).csrf(AbstractHttpConfigurer::disable).httpBasic(Customizer.withDefaults())
                .addFilterBefore(jwtAuthenticationFilter, AnonymousAuthenticationFilter.class);
        return http.build();
    }


}
