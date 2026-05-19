package com.example.demo.controller;


import com.example.demo.repository.ArticleRepository;
import com.example.demo.service.ArticleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PostController {
    private ArticleService articleService;

    public PostController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/posts")
    public String getPosts(Model model){
        articleService.setPostsModel(model);
        return "posts";
    }
}
