package com.tedameda.blogapp.users.dto;

import lombok.Data;
import lombok.NonNull;

/**
 * @author TedaMeda
 * @since 12/27/2023
 */
@Data
public class CreateUserRequest {
    @NonNull
    private String username;
    @NonNull
    private String password;
    @NonNull
    private String email;
}
