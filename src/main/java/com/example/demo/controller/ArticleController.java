package com.example.demo.controller;


import com.example.demo.entity.Article;
import com.example.demo.service.ArticleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ArticleController {

    private ArticleService articleService;

    @GetMapping("/article")
    public ResponseEntity<List<Map<String, Object>>> getArticles(){
        return ResponseEntity.ok(articleService.getArticles());
    }

    @GetMapping("/article/{id}")
    public ResponseEntity<Map<String, Object>> getArticle(@PathVariable Long id){
        return ResponseEntity.ok(articleService.getArticle(id));
    }

    @PostMapping("/article")
    public ResponseEntity<Article> createArticle(@RequestBody Map<String,Object> body){
        Long memberId = Long.valueOf(body.get("memberId").toString());
        Long boardId = Long.valueOf(body.get("boardId").toString());
        String title = body.get("title").toString();
        String content = body.get("content").toString();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(articleService.createArticle(memberId, boardId, title, content));
    }

    @PutMapping("/article/{id}")
    public ResponseEntity<Article> updateArticle(@PathVariable Long id, @RequestBody Map<String,Object> body) {
        String title = body.get("title").toString();
        String content = body.get("content").toString();
        return ResponseEntity.ok(articleService.updateArticle(id,title,content));
    }

    @DeleteMapping("/article/{id}")
    public ResponseEntity<?> deleteArticle(@PathVariable Long id){
        articleService.deleteArticle(id);
        return ResponseEntity.noContent().build();
    }
}
