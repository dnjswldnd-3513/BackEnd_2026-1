package com.example.demo.controller;


import com.example.demo.entity.Article;
import com.example.demo.service.ArticleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/articles")
    public ResponseEntity<List<Map<String, Object>>> getArticles(
            @RequestParam(required = false) Long boardId) {
        if (boardId != null) {
            return ResponseEntity.ok(articleService.getArticlesByBoard(boardId));
        }
        return ResponseEntity.ok(articleService.getArticles());
    }

    @GetMapping("/articles/{id}")
    public ResponseEntity<Map<String, Object>> getArticle(@PathVariable Long id){
        return ResponseEntity.ok(articleService.getArticle(id));
    }

    private Long toLong(Object obj) {
        return obj != null ? Long.valueOf(obj.toString()) : null;
    }

    @PostMapping("/articles")
    public ResponseEntity<Article> createArticle(@RequestBody Map<String,Object> body){
        String title = (String) body.get("title");
        String content = (String) body.get("content");

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(articleService.createArticle(toLong(body.get("memberId")), toLong(body.get("boardId")), title, content));
    }

    @PutMapping("/articles/{id}")
    public ResponseEntity<Article> updateArticle(@PathVariable Long id, @RequestBody Map<String, Object> body) {
        String title = (String) body.get("title");
        String content = (String) body.get("content");
        return ResponseEntity.ok(articleService.updateArticle(id, toLong(body.get("memberId")), toLong(body.get("boardId")), title, content));
    }

    @DeleteMapping("/articles/{id}")
    public ResponseEntity<?> deleteArticle(@PathVariable Long id){
        articleService.deleteArticle(id);
        return ResponseEntity.noContent().build();
    }

}
