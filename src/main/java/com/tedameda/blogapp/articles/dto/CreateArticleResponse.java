package com.tedameda.blogapp.articles.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Max;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * @author TedaMeda
 * @since 1/15/2024
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateArticleResponse {
    @NonNull
    private String title;
    @Nullable
    private String subtitle;
    @NonNull
    private String body;
}
