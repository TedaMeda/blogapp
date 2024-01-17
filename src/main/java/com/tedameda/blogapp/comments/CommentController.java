package com.tedameda.blogapp.comments;

import com.tedameda.blogapp.articles.ArticleEntity;
import com.tedameda.blogapp.articles.ArticleService;
import com.tedameda.blogapp.comments.DTO.CreateCommentRequest;
import com.tedameda.blogapp.comments.DTO.CreateCommentResponse;
import com.tedameda.blogapp.common.dto.ErrorResponseDTO;
import com.tedameda.blogapp.users.UserEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author TedaMeda
 * @since 12/30/2023
 */
@RestController
@RequestMapping("/articles/{article-slug}/comments")
public class CommentController {
    private final CommentService commentService;
    private final ArticleService articleService;
    private final ModelMapper modelMapper;

    public CommentController(CommentService commentService, ArticleService articleService, ModelMapper modelMapper) {
        this.commentService = commentService;
        this.articleService = articleService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("")
    public ResponseEntity<List<CreateCommentResponse>> getComments(@PathVariable("article-slug") String slug){
        ArticleEntity article = articleService.getArticleBySlug(slug);
        List<CommentEntity> comments = commentService.getCommentsByArticle(article);
        List<CreateCommentResponse> commentResponses = new ArrayList<>();
        for (CommentEntity comment : comments) {
            commentResponses.add(
//                    modelMapper.map(comment, CreateCommentResponse.class)
                    CreateCommentResponse.builder()
                            .title(comment.getTitle())
                            .body(comment.getBody())
                            .createdAt(comment.getCreatedAt())
                            .username(comment.getAuthor().getUsername())
                            .build()
            );
        }
        return ResponseEntity.ok(commentResponses);
    }

    @PostMapping("")
    public ResponseEntity<CreateCommentResponse> createComment(@RequestBody CreateCommentRequest commentRequest
            , @PathVariable("article-slug") String slug, @AuthenticationPrincipal UserEntity user){
        ArticleEntity article = articleService.getArticleBySlug(slug);
        CommentEntity comment = commentService.createComment(commentRequest,article,user);
        CreateCommentResponse commentResponse = CreateCommentResponse.builder()
                .title(comment.getTitle())
                .body(comment.getBody())
                .createdAt(comment.getCreatedAt())
                .username(comment.getAuthor().getUsername())
                .build();
        return ResponseEntity.ok(commentResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable("id") Long id, @AuthenticationPrincipal UserEntity user){
        commentService.deleteCommentById(id,user);
        return ResponseEntity.ok("Comment Deleted");
    }

    @ExceptionHandler({
            ArticleService.ArticleNotFoundException.class,
            CommentService.CommentNotFoundException.class,
            CommentService.UnauthorizedUserException.class
    })
    private ResponseEntity<ErrorResponseDTO> handleExeption(Exception ex){
        String message;
        HttpStatus status;
        if (ex instanceof ArticleService.ArticleNotFoundException){
            message = ex.getMessage();
            status = HttpStatus.NOT_FOUND;
        }
        else if (ex instanceof CommentService.CommentNotFoundException){
            message = ex.getMessage();
            status = HttpStatus.NOT_FOUND;
        }
        else if(ex instanceof CommentService.UnauthorizedUserException){
            message = ex.getMessage();
            status =HttpStatus.UNAUTHORIZED;
        }
        else {
            message = "Internal Server Error";
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        ErrorResponseDTO response = ErrorResponseDTO.builder().message(message).build();
        return ResponseEntity.status(status).body(response);
    }
}
