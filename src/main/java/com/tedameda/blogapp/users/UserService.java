package com.tedameda.blogapp.users;

import com.tedameda.blogapp.users.dto.CreateUserRequest;
import org.springframework.stereotype.Service;

/**
 * @author TedaMeda
 * @since 12/25/2023
 */
@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity createUser(CreateUserRequest u){
        UserEntity user = UserEntity.builder().
                                    username(u.getUsername()).
                                    email(u.getEmail()).
//                                    password(u.getPassword()). //TODO: encrypt password
                                    build();

        return userRepository.save(user);
    }


    public UserEntity getUser(String username){
        return userRepository.findByUsername(username).orElseThrow(()->new UserNotFoundException(username));
    }
    public UserEntity getUser(Long userId){
        return userRepository.findById(userId).orElseThrow(()->new UserNotFoundException(userId));
    }
    public UserEntity loginUser(String username, String password){
        var user = userRepository.findByUsername(username).orElseThrow(()->new UserNotFoundException(username));
        //TODO: match password
        return user;
    }

    static class UserNotFoundException extends IllegalArgumentException{
        public UserNotFoundException(String username){
            super("User "+username+" not found");
        }
        public UserNotFoundException(Long userId){
            super("User with userid: "+userId+" not found");
        }
    }

}