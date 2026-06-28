package com.example.demo.service;


import com.example.demo.dto.request.BoardCreateRequest;
import com.example.demo.dto.request.BoardUpdateRequest;
import com.example.demo.dto.response.BoardResponse;
import com.example.demo.entity.Board;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.EntityNotFoundException;
import com.example.demo.repository.BoardRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BoardService {

    private final BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    private Board findBoardById(Long id){
        return boardRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 board id입니다. id : "+id));
    }

    @Transactional(readOnly = true)
    public List<BoardResponse> getBoards() {
        return boardRepository.findAll().stream()
                .map(m -> new BoardResponse(m.getId(), m.getName()))
                .toList();
    }

    @Transactional(readOnly = true)
    public BoardResponse getBoard(Long id) {
        Board board = findBoardById(id);
        return new BoardResponse(board.getId(), board.getName());
    }

    @Transactional
    public BoardResponse createBoard(BoardCreateRequest request) {
        if (request.name() == null) throw new BadRequestException("name은 필수입니다.");
        Board board = boardRepository.save(new Board(request.name()));
        return new BoardResponse(board.getId(), board.getName());
    }

    @Transactional
    public BoardResponse updateBoard(Long id, BoardUpdateRequest request) {
        Board board = findBoardById(id);
        board.update(request.name());
        return new BoardResponse(board.getId(), board.getName());
    }

    @Transactional
    public void deleteBoard(Long id) {
        Board board = findBoardById(id);
        boardRepository.deleteById(id);
    }//원래는 게시물이 있으면 400을 던졌는데 이제 cascade가 all 이니까 삭제하면 게시물도 같이 날라가게
}
