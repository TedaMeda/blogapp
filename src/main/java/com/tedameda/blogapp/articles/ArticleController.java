package com.tedameda.blogapp.articles;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author TedaMeda
 * @since 12/30/2023
 */
@RestController
@RequestMapping("/articles")
public class ArticleController {
    ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

}
