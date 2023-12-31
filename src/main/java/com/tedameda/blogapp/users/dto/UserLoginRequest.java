package com.tedameda.blogapp.users.dto;

import lombok.Data;
import lombok.NonNull;

/**
 * @author TedaMeda
 * @since 12/30/2023
 */
@Data
public class UserLoginRequest {
    @NonNull
    private String username;
    @NonNull
    private String password;
}
