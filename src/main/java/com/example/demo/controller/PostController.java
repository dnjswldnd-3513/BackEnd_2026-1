package com.example.demo.controller;

import com.example.demo.entity.Article;
import com.example.demo.entity.Board;
import com.example.demo.exception.EntityNotFoundException;
import com.example.demo.repository.BoardRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class PostController {

    private final BoardRepository boardRepository;

    public PostController(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @GetMapping("/posts")
    public String getPosts(@RequestParam Long boardId, Model model) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 board id: "+ boardId));
        model.addAttribute("boardName", board.getName());
        model.addAttribute("articles", board.getArticles());
        return "posts";
    }
}
