package com.tedameda.blogapp.comments;

import com.tedameda.blogapp.articles.ArticleEntity;
import com.tedameda.blogapp.users.UserEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.Date;
import java.util.List;

/**
 * @author TedaMeda
 * @since 12/25/2023
 */
@Entity(name = "comments")
@Getter
@Setter
@Builder
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(nullable = false)
    private Long id;
    @Nullable
    private String title;
    @NonNull
    private String body;
    @CreationTimestamp
    private Date createdAt;

    @ManyToOne
    @JoinColumn(name = "articleId", nullable = false)
    private ArticleEntity articleEntity;
    @ManyToOne
    @JoinColumn(name = "authorId", nullable = false)
    private UserEntity  author;
}
