package com.tedameda.blogapp.comments.DTO;

import com.tedameda.blogapp.users.UserEntity;
import lombok.Builder;
import lombok.Data;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.Date;

/**
 * @author TedaMeda
 * @since 1/17/2024
 */
@Data
@Builder
public class CreateCommentResponse {
    @Nullable
    private String title;
    @NonNull
    private String body;
    @NonNull
    private Date createdAt;
    @NonNull
    private String username;
}
