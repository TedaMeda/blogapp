package com.tedameda.blogapp.articles;

import com.tedameda.blogapp.articles.dto.CreateArticleRequest;
import com.tedameda.blogapp.articles.dto.CreateArticleResponse;
import com.tedameda.blogapp.articles.dto.UpdateArticleRequest;
import com.tedameda.blogapp.common.dto.ErrorResponseDTO;
import com.tedameda.blogapp.users.UserEntity;
import com.tedameda.blogapp.users.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;


/**
 * @author TedaMeda
 * @since 12/30/2023
 */
@RestController
@RequestMapping("/articles")
public class ArticleController {
    ArticleService articleService;
    private final ModelMapper modelMapper;
    public ArticleController(ArticleService articleService, ModelMapper modelMapper) {
        this.articleService = articleService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("")
    public ResponseEntity<List<CreateArticleResponse>> getAllArticles(){
        Iterable<ArticleEntity> article = articleService.getAllArticles();
        List<CreateArticleResponse> articleResponse = new ArrayList<>();
        article.forEach(article1 -> {
            articleResponse.add(modelMapper.map(article1, CreateArticleResponse.class));
        });
        return ResponseEntity.ok(articleResponse);
    }

    @PatchMapping("/{article-slug}")
    public ResponseEntity<CreateArticleResponse> updateArticleBySlug(
            @PathVariable("article-slug") String slug
            , @RequestBody UpdateArticleRequest articleRequest
            , @AuthenticationPrincipal UserEntity user) throws Exception{

            Long editRequestedByUserId=user.getId();
            var article=articleService.getArticleBySlug(slug);

            if(article.getAuthor().getId()==editRequestedByUserId){
                ArticleEntity updatedArticle = articleService.updateArticle(article.getId(),articleRequest);
                CreateArticleResponse articleResponse = modelMapper.map(updatedArticle, CreateArticleResponse.class);
                return ResponseEntity.ok(articleResponse);
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
    @PostMapping("")
    public ResponseEntity<CreateArticleResponse> createArticle(@RequestBody CreateArticleRequest articleRequest, @AuthenticationPrincipal UserEntity user){
        ArticleEntity article = articleService.createArticle(articleRequest, user.getId());
        CreateArticleResponse articleResponse=modelMapper.map(article, CreateArticleResponse.class);
       return ResponseEntity.ok(articleResponse);
    }

    @ExceptionHandler({
            ArticleService.ArticleNotFoundException.class
    })
    ResponseEntity<ErrorResponseDTO> handleExeption(Exception ex){
        String message;
        HttpStatus status;
        if (ex instanceof ArticleService.ArticleNotFoundException){
            message = ex.getMessage();
            status = HttpStatus.NOT_FOUND;
        }
        else {
            message = "Internal Server Error";
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        ErrorResponseDTO response = ErrorResponseDTO.builder().message(message).build();
        return ResponseEntity.status(status).body(response);
    }
}
