package com.tedameda.blogapp.comments;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author TedaMeda
 * @since 12/25/2023
 */
@Repository
public interface CommentRepository extends JpaRepository<CommentEntity,Long> {
}
