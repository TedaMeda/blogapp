package com.tedameda.blogapp.comments.DTO;

import com.tedameda.blogapp.articles.ArticleEntity;
import com.tedameda.blogapp.users.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.Date;

/**
 * @author TedaMeda
 * @since 1/16/2024
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCommentRequest {
    @Nullable
    private String title;
    @NonNull
    private String body;
}
