package com.tedameda.blogapp.articles.dto;

import lombok.Data;
import lombok.NonNull;
import org.springframework.lang.Nullable;

/**
 * @author TedaMeda
 * @since 12/27/2023
 */
@Data
public class CreateArticleRequest {
    @NonNull
    private String title;
    @Nullable
    private String subTitle;
    @NonNull
    private String body;
}
