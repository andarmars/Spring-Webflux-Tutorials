package com.security.jwtAuth.oontroller.article;

package com.security.jwt.controller.article;

import com.codersee.jwtauth.model.Article;
import com.codersee.jwtauth.service.ArticleService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/article")
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping
    public List<ArticleResponse> listAll() {
        return articleService.findAll()
                .stream()
                .map(Article::toResponse)
                .collect(Collectors.toList());
    }

    private static class ArticleResponse {
        private final UUID id;
        private final String title;
        private final String content;

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
}

