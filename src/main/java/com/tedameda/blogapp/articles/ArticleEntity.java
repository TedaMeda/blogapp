package com.tedameda.blogapp.articles;

import com.tedameda.blogapp.users.UserEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.Date;

/**
 * @author TedaMeda
 * @since 12/25/2023
 */
@Entity(name = "articles")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ArticleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(nullable = false)
    private Long id;
    @NonNull
    @Max(100)
    private String title;
    @NonNull
    @Column(unique = true)
    private String slug;
    @Nullable
    private String subtitle;
    @NonNull
    private String body;
    @CreatedDate
    private Date createdAt;
    //TODO: add taglist
    //private List<String> tags;
    @ManyToOne
    @JoinColumn(name = "authorId", nullable = false)
    private UserEntity author;
}
