package com.security.jwt.repository;

import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Repository
public class ArticleRepository {

    private List<Article> articles = Arrays.asList(
            new Article(UUID.randomUUID(), "Article 1", "Content 1"),
            new Article(UUID.randomUUID(), "Article 2", "Content 2")
    );

    public List<Article> findAll() {
        return articles;
    }
}

