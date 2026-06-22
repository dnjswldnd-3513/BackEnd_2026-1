package com.example.demo.controller;

import com.example.demo.dao.ArticleDao;
import com.example.demo.dao.BoardDao;
import com.example.demo.entity.Article;
import com.example.demo.entity.Board;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class PostController {

    private final ArticleDao articleDao;
    private final BoardDao boardDao;

    public PostController(ArticleDao articleDao, BoardDao boardDao) {
        this.articleDao = articleDao;
        this.boardDao = boardDao;
    }

    @GetMapping("/posts")
    public String getPosts(@RequestParam Long boardId, Model model) {
        Board board = boardDao.findById(boardId);
        List<Article> articles = articleDao.findByBoardId(boardId);
        model.addAttribute("boardName", board.getName());
        model.addAttribute("articles", articles);
        return "posts";
    }
}
