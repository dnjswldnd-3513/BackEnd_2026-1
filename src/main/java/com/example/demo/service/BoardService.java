package com.example.demo.service;


import com.example.demo.entity.Board;
import com.example.demo.exception.BadRequestException;
import com.example.demo.repository.ArticleRepository;
import com.example.demo.repository.BoardRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService {
    private final BoardRepository boardRepository;
    private final ArticleRepository articleRepository;

    public BoardService(BoardRepository boardRepository, ArticleRepository articleRepository) {
        this.boardRepository = boardRepository;
        this.articleRepository = articleRepository;
    }

    public List<Board> getBoards() {
        return boardRepository.findAll();
    }

    public Board getBoard(Long id) {
        return boardRepository.findById(id);
    }

    public Board createBoard(String name) {
        if (name ==null) throw new BadRequestException("name은 필수입니다.");
        return boardRepository.save(new Board(name));
    }

    public Board updateBoard(Long id, String name) {
        Board board = boardRepository.findById(id);
        board.update(name);
        return board;
    }

    public void deleteBoard(Long id) {
        boardRepository.findById(id);
        if (articleRepository.checkByBoardId(id)) throw new BadRequestException("작성된 게시물이 있어 게시판을 삭제할수 없습니다.");
        boardRepository.deleteById(id);
    }
}
