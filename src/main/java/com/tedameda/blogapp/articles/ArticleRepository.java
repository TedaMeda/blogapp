package com.tedameda.blogapp.articles;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author TedaMeda
 * @since 12/25/2023
 */
@Repository
public interface ArticleRepository extends JpaRepository<ArticleEntity,Long> {
    Optional<ArticleEntity> findBySlug(String slug);
}
