package com.tedameda.blogapp.users.dto;

import lombok.Data;

/**
 * @author TedaMeda
 * @since 12/30/2023
 */
@Data
public class UserResponse {
    private Long id;
    private String username;
    private String email;
    private String bio;
    private String image;
}
