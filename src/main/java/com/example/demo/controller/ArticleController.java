package com.example.demo.controller;

import com.example.demo.dto.request.ArticleCreateRequest;
import com.example.demo.dto.request.ArticleUpdateRequest;
import com.example.demo.dto.response.ArticleResponse;
import com.example.demo.service.ArticleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/articles")
    public ResponseEntity<List<ArticleResponse>> getArticles(
            @RequestParam(required = false) Long boardId) {
        if (boardId != null) {
            return ResponseEntity.ok(articleService.getArticlesByBoard(boardId));
        }
        return ResponseEntity.ok(articleService.getArticles());
    }

    @GetMapping("/articles/{id}")
    public ResponseEntity<ArticleResponse> getArticle(@PathVariable Long id) {
        return ResponseEntity.ok(articleService.getArticle(id));
    }

    @PostMapping("/articles")
    public ResponseEntity<ArticleResponse> createArticle(@RequestBody ArticleCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(articleService.createArticle(request));
    }

    @PutMapping("/articles/{id}")
    public ResponseEntity<ArticleResponse> updateArticle(@PathVariable Long id, @RequestBody ArticleUpdateRequest request) {
        return ResponseEntity.ok(articleService.updateArticle(id, request));
    }

    @DeleteMapping("/articles/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable Long id) {
        articleService.deleteArticle(id);
        return ResponseEntity.noContent().build();
    }
}
