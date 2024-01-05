package com.tedameda.blogapp.users;

import com.tedameda.blogapp.common.dto.ErrorResponseDTO;
import com.tedameda.blogapp.security.JWTService;
import com.tedameda.blogapp.users.dto.CreateUserRequest;
import com.tedameda.blogapp.users.dto.UserResponse;
import com.tedameda.blogapp.users.dto.UserLoginRequest;
import org.apache.catalina.User;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

/**
 * @author TedaMeda
 * @since 12/30/2023
 */
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final JWTService jwtService;

    public UserController(UserService userService, ModelMapper modelMapper, JWTService jwtService){//, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.jwtService = jwtService;
    }

    @PostMapping("")
    public ResponseEntity<UserResponse> signupUser(@RequestBody CreateUserRequest request){
        var savedUser = userService.createUser(request);
        URI savedUserURI = URI.create("/users/"+savedUser.getId());
        UserResponse userResponse = modelMapper.map(savedUser, UserResponse.class);
        userResponse.setToken(
                jwtService.createJwt(userResponse.getId())
        );
        return ResponseEntity.created(savedUserURI).body(userResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponse> loginUser(@RequestBody UserLoginRequest request){
        var user = userService.loginUser(request.getUsername(), request.getPassword());
        UserResponse userResponse = modelMapper.map(user, UserResponse.class);
        userResponse.setToken(
                jwtService.createJwt(userResponse.getId())
        );
        return ResponseEntity.ok(userResponse);
    }

    public record LoginRequest(String username, String password) {
    }

    @ExceptionHandler({
            UserService.UserNotFoundException.class,
            UserService.InvalidCredentialsException.class
    })
    ResponseEntity<ErrorResponseDTO> handleException(Exception ex){
        String message;
        HttpStatus status;
        if (ex instanceof UserService.UserNotFoundException){
            message = ex.getMessage();
            status = HttpStatus.NOT_FOUND;
        }
        else if(ex instanceof UserService.InvalidCredentialsException){
            message = ex.getMessage();
            status = HttpStatus.UNAUTHORIZED;
        }
        else {
            message = "Internal Server Error";
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        ErrorResponseDTO response = ErrorResponseDTO.builder().message(message).build();
        return ResponseEntity.status(status).body(response);
    }
}
