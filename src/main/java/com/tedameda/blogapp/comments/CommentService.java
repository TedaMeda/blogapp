package com.tedameda.blogapp.comments;

import com.tedameda.blogapp.articles.ArticleEntity;
import com.tedameda.blogapp.articles.ArticleService;
import com.tedameda.blogapp.comments.DTO.CreateCommentRequest;
import com.tedameda.blogapp.users.UserEntity;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

/**
 * @author TedaMeda
 * @since 12/25/2023
 */
@Service
public class CommentService {
    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public List<CommentEntity> getCommentsByArticle(ArticleEntity article){
        var comments = commentRepository.findByarticleEntity(article).orElseThrow(()->new IllegalArgumentException());
        return comments;
    }

    public CommentEntity createComment(CreateCommentRequest commentRequest, ArticleEntity article, UserEntity user){
        CommentEntity comment = CommentEntity.builder()
                .title(commentRequest.getTitle())
                .body(commentRequest.getBody())
                .articleEntity(article)
                .author(user)
                .build();

        return commentRepository.save(comment);
    }

    public void deleteCommentById(Long id,UserEntity user){
        CommentEntity comment = commentRepository.findById(id).orElseThrow(()->new CommentNotFoundException(id));
        if(user.getId()==comment.getAuthor().getId()){
            commentRepository.delete(comment);
        }
        else{
            throw new UnauthorizedUserException();
        }
    }
    public static class CommentNotFoundException extends IllegalArgumentException{
        public CommentNotFoundException(Long id){
            super("Comment with comment-id "+id+" not found");
        }
    }
    public static class UnauthorizedUserException extends IllegalArgumentException{
        public UnauthorizedUserException(){
            super("Unauthorized user");
        }
    }
}
