package com.tedameda.blogapp.comments;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author TedaMeda
 * @since 12/30/2023
 */
@RestController
@RequestMapping("/articles/{article-slug}/comments")
public class CommentController {
    CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

}
