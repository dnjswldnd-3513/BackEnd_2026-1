package com.example.demo.service;


import com.example.demo.entity.Board;
import com.example.demo.repository.BoardRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService {
    private final BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    public List<Board> getBoards() {
        return boardRepository.findAll();
    }

    public Board getBoard(Long id) {
        return boardRepository.findById(id);
    }

    public Board createBoard(String name) {
        return boardRepository.save(new Board(name));
    }

    public Board updateBoard(Long id, String name) {
        Board board = boardRepository.findById(id);
        board.update(name);
        return board;
    }

    public void deleteBoard(Long id) {
        boardRepository.deleteById(id);
    }
}
