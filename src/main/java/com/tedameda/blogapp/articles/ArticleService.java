package com.tedameda.blogapp.articles;

import com.tedameda.blogapp.articles.dto.CreateArticleRequest;
import com.tedameda.blogapp.articles.dto.UpdateArticleRequest;
import com.tedameda.blogapp.users.UserEntity;
import com.tedameda.blogapp.users.UserRepository;
import org.springframework.stereotype.Service;

/**
 * @author TedaMeda
 * @since 12/25/2023
 */
@Service
public class ArticleService {
    private ArticleRepository articleRepository;
    private UserRepository userRepository;

    public ArticleService(ArticleRepository articleRepository, UserRepository userRepository) {
        this.articleRepository = articleRepository;
        this.userRepository = userRepository;
    }

    public Iterable<ArticleEntity> getAllArticles(){
        return articleRepository.findAll();
    }

    public ArticleEntity getArticleBySlug(String slug){
        var article = articleRepository.findBySlug(slug).orElseThrow(()->new ArticleNotFoundException(slug));
        return article;
    }

    public ArticleEntity createArticle(CreateArticleRequest a, Long authorId){
        var author = userRepository.findById(authorId).orElseThrow(()->new ArticleNotFoundException(authorId));
        return articleRepository.save(ArticleEntity.builder()
                .title(a.getTitle())
                //TODO: add slugification function
                .slug(a.getTitle().toLowerCase().replaceAll("\\s+","-"))
                .body(a.getBody())
                .author(author)
                .subtitle(a.getSubTitle())
                .build()
        );
    }

    public ArticleEntity updateArticle(Long articleId, UpdateArticleRequest a){
        var article = articleRepository.findById(articleId).orElseThrow(()->new ArticleNotFoundException(articleId));
        if(a.getTitle()!=null){
            article.setTitle(a.getTitle());
            article.setSlug(a.getTitle().toLowerCase().replaceAll("\\s+","-"));
        }
        if(a.getSubTitle()!=null){
            article.setSubtitle(a.getSubTitle());
        }
        if(a.getBody()!=null){
            article.setBody(a.getBody());
        }
        return articleRepository.save(article);
    }

    class ArticleNotFoundException extends IllegalArgumentException{
        public ArticleNotFoundException(String slug){
            super("Article-slug "+slug+" not found");
        }

        public ArticleNotFoundException(Long articleId){
            super("Article with articleId: "+articleId+" not found");
        }
    }
}
