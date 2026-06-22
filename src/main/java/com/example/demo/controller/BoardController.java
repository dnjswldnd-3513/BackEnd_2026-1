package com.example.demo.controller;

import com.example.demo.dto.request.BoardCreateRequest;
import com.example.demo.dto.request.BoardUpdateRequest;
import com.example.demo.dto.response.BoardResponse;
import com.example.demo.service.BoardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BoardController {

    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping("/boards")
    public ResponseEntity<List<BoardResponse>> getBoards() {
        return ResponseEntity.ok(boardService.getBoards());
    }

    @GetMapping("/boards/{id}")
    public ResponseEntity<BoardResponse> getBoard(@PathVariable Long id) {
        return ResponseEntity.ok(boardService.getBoard(id));
    }

    @PostMapping("/boards")
    public ResponseEntity<BoardResponse> create(@RequestBody BoardCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(boardService.createBoard(request));
    }

    @PutMapping("/boards/{id}")
    public ResponseEntity<BoardResponse> update(@PathVariable Long id, @RequestBody BoardUpdateRequest request) {
        return ResponseEntity.ok(boardService.updateBoard(id, request));
    }

    @DeleteMapping("/boards/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boardService.deleteBoard(id);
        return ResponseEntity.noContent().build();
    }
}
