package com.tedameda.blogapp;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

/**
 * @author TedaMeda
 * @since 12/25/2023
 */
@Configuration
public class JpaTestConfig {
    @Bean
    @Profile("Test")
    public DataSource dataSource(){
        var dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl("jdbc:j2:mem:test;DB_CLOST_DELAY=-1");
        return dataSource;
    }
}
