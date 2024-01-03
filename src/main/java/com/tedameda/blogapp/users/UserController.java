package com.tedameda.blogapp.users;

import com.tedameda.blogapp.common.dto.ErrorResponseDTO;
import com.tedameda.blogapp.users.dto.CreateUserRequest;
import com.tedameda.blogapp.users.dto.UserResponse;
import com.tedameda.blogapp.users.dto.UserLoginRequest;
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
//    private final AuthenticationManager authenticationManager;
//    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, ModelMapper modelMapper){//, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.modelMapper = modelMapper;
//        this.authenticationManager = authenticationManager;
//        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("")
    public ResponseEntity<UserResponse> signupUser(@RequestBody CreateUserRequest request){
//        Authentication authenticationRequest =
//                UsernamePasswordAuthenticationToken.unauthenticated(request.getUsername(), request.getPassword());
//        Authentication authenticationResponse =
//                this.authenticationManager.authenticate(authenticationRequest);

        var savedUser = userService.createUser(request);
        URI savedUserURI = URI.create("/users/"+savedUser.getId());
        return ResponseEntity.created(savedUserURI).body(modelMapper.map(savedUser, UserResponse.class));
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponse> loginUser(@RequestBody UserLoginRequest request){
        var user = userService.loginUser(request.getUsername(), request.getPassword());
        return ResponseEntity.ok(modelMapper.map(user, UserResponse.class));
    }

    public record LoginRequest(String username, String password) {
    }

    @ExceptionHandler({UserService.UserNotFoundException.class})
    ResponseEntity<ErrorResponseDTO> handleException(Exception ex){
        String message;
        HttpStatus status;
        if (ex instanceof UserService.UserNotFoundException){
            message = ex.getMessage();
            status = HttpStatus.NOT_FOUND;
        }
        else {
            message = "Internal Server Error";
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        ErrorResponseDTO response = ErrorResponseDTO.builder().message(message).build();
        return ResponseEntity.status(status).body(response);
    }
}
