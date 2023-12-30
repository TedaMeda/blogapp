package com.tedameda.blogapp.articles.dto;

import lombok.Data;
import lombok.NonNull;
import org.springframework.lang.Nullable;

/**
 * @author TedaMeda
 * @since 12/28/2023
 */
@Data
public class UpdateArticleRequest {
    @Nullable
    private String title;
    @Nullable
    private String subTitle;
    @Nullable
    private String body;
}
