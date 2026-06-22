package com.example.demo.service;


import com.example.demo.dao.ArticleDao;
import com.example.demo.dao.BoardDao;
import com.example.demo.dto.request.BoardCreateRequest;
import com.example.demo.dto.request.BoardUpdateRequest;
import com.example.demo.dto.response.BoardResponse;
import com.example.demo.entity.Board;
import com.example.demo.exception.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BoardService {

    private final BoardDao boardDao;
    private final ArticleDao articleDao;

    public BoardService(BoardDao boardDao, ArticleDao articleDao) {
        this.boardDao = boardDao;
        this.articleDao = articleDao;
    }

    @Transactional(readOnly = true)
    public List<BoardResponse> getBoards() {
        return boardDao.findAll().stream()
                .map(m -> new BoardResponse(m.getId(), m.getName()))
                .toList();
    }

    @Transactional(readOnly = true)
    public BoardResponse getBoard(Long id) {
        Board board = boardDao.findById(id);
        return new BoardResponse(board.getId(), board.getName());
    }

    @Transactional
    public BoardResponse createBoard(BoardCreateRequest request) {
        if (request.name() == null) throw new BadRequestException("name은 필수입니다.");
        Board board = boardDao.save(new Board(request.name()));
        return new BoardResponse(board.getId(), board.getName());
    }

    @Transactional
    public BoardResponse updateBoard(Long id, BoardUpdateRequest request) {
        Board board = boardDao.findById(id);
        board.update(request.name());
        boardDao.update(board);
        return new BoardResponse(board.getId(), board.getName());
    }

    @Transactional
    public void deleteBoard(Long id) {
        boardDao.findById(id);
        if (articleDao.existsByBoardId(id)) throw new BadRequestException("작성된 게시물이 있어 삭제할 수 없습니다.");
        boardDao.deleteById(id);
    }
}
