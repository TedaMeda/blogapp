package com.tedameda.blogapp.security;

import com.tedameda.blogapp.users.UserEntity;
import com.tedameda.blogapp.users.UserRepository;
import com.tedameda.blogapp.users.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;

/**
 * @author TedaMeda
 * @since 12/31/2023
 */
@Configuration
@EnableWebSecurity
public class AppSecurityConfig{
    private final JWTAuthenticationFilter jwtAuthenticationFilter = new JWTAuthenticationFilter();

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.csrf().disable();//TODO: find alternative way to replace this
        http.authorizeHttpRequests((authorize) -> authorize
                .requestMatchers(HttpMethod.POST,"/users","/users/login").permitAll()
                .requestMatchers("/h2-console/*").permitAll()
                .anyRequest().authenticated()
        ).formLogin(Customizer.withDefaults());
        http.addFilterBefore(jwtAuthenticationFilter, AnonymousAuthenticationFilter.class);
        return http.build();
    }

}
