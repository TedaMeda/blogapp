package com.tedameda.blogapp.users;

import com.tedameda.blogapp.users.dto.CreateUserRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * @author TedaMeda
 * @since 12/30/2023
 */
@SpringBootTest
@ActiveProfiles("test")
public class UserServiceTest {
    @Autowired
    UserService userService;
    @Test
    void can_create_user(){
        var user = userService.createUser(new CreateUserRequest(
                "Ronak",
                "pass123",
                "ronak@gmail.com"
        ));

        Assertions.assertNotNull(user);
        Assertions.assertEquals("Ronak", user.getUsername());
//        Assertions.assertEquals("pass123", user.);

    }
}
