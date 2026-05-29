package com.example.demo.controller;


import com.example.demo.entity.Article;
import com.example.demo.service.ArticleService;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @PostMapping("/articles")
    public ResponseEntity<Article> createArticle(@RequestBody Map<String,Object> body){
        Long memberId = Long.valueOf(body.get("memberId").toString());
        Long boardId = Long.valueOf(body.get("boardId").toString());
        String title = body.get("title").toString();
        String content = body.get("content").toString();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(articleService.createArticle(memberId, boardId, title, content));
    }

    @PutMapping("/articles/{id}")
    public ResponseEntity<Article> updateArticle(@PathVariable Long id, @RequestBody Map<String,Object> body) {
        String title = body.get("title").toString();
        String content = body.get("content").toString();
        return ResponseEntity.ok(articleService.updateArticle(id,title,content));
    }

    @DeleteMapping("/articles/{id}")
    public ResponseEntity<?> deleteArticle(@PathVariable Long id){
        articleService.deleteArticle(id);
        return ResponseEntity.noContent().build();
    }

}
