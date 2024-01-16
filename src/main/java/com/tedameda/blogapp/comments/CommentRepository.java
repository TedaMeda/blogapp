package com.tedameda.blogapp.comments;

import com.tedameda.blogapp.articles.ArticleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author TedaMeda
 * @since 12/25/2023
 */
@Repository
public interface CommentRepository extends JpaRepository<CommentEntity,Long> {
    Optional<List<CommentEntity>> findByarticleEntity(ArticleEntity articleEntity);
}
