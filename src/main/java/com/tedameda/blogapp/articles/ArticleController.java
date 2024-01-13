package com.tedameda.blogapp.articles;

import org.springframework.web.bind.annotation.*;

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

    @GetMapping("")
    String getAllArticles(){
        return "get all articles";
    }

    @GetMapping("/{id}")
    String getArticleById(@PathVariable("id") String id){
        return "Get article with id"+id;
    }

    @PostMapping("")
    String createArticle(){
        return "article created";
    }
}
