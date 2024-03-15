package com.security.jwt.controller.article;

import java.util.UUID;

public class ArticleResponse {
    private UUID id;
    private String title;
    private String content;

    public ArticleResponse(UUID id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    // Getters
    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}

