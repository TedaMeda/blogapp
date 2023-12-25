package com.tedameda.blogapp.users;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * @author TedaMeda
 * @since 12/25/2023
 */
@DataJpaTest
@ActiveProfiles("test")
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    @Order(1)
    void can_create_users(){
        var user = UserEntity.builder().name("ronak").email("ronak@gmail.com").build();
        userRepository.save(user);
    }

    @Test
    @Order(2)
    void can_find_users(){
        var user = UserEntity.builder().name("ronak").email("ronak@gmail.com").build();
        userRepository.save(user);

        var users = userRepository.findAll();
        assert users.size()==1;
    }
}
