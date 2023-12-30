package com.tedameda.blogapp.comments;

import org.springframework.stereotype.Service;

/**
 * @author TedaMeda
 * @since 12/25/2023
 */
@Service
public class CommentService {
    private CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

}
