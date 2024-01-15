package com.tedameda.blogapp.users;

import com.tedameda.blogapp.users.dto.CreateUserRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author TedaMeda
 * @since 12/25/2023
 */
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserEntity createUser(CreateUserRequest u) {
        UserEntity user = UserEntity.builder()
                            .username(u.getUsername())
                            .email(u.getEmail())
                            .password(passwordEncoder.encode(u.getPassword()))
                            .build();
        return userRepository.save(user);
    }

    public UserEntity getUser(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
    }

    public UserEntity getUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
    }

    public UserEntity loginUser(String username, String password) {
        var user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
        boolean passMatch = passwordEncoder.matches(password, user.getPassword());
        if(!passMatch){
            throw new InvalidCredentialsException();
        }
        return user;
    }

    static class UserNotFoundException extends IllegalArgumentException {
        public UserNotFoundException(String username) {
            super("User with username: " + username + " not found");
        }
        public UserNotFoundException(Long userId) {
            super("User with userid: " + userId + " not found");
        }
    }

    static  class  InvalidCredentialsException extends IllegalArgumentException{
        public InvalidCredentialsException() {
            super("Invalid username or password");
        }
    }
}