package com.example.demo.repository;

import com.example.demo.entity.Board;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class BoardRepository {

    private final Map<Long, Board> boards = new HashMap<>();
    private Long nextId = 0L;

    public Board save(Board board){
        board.crateId(++nextId);
        boards.put(board.getId(),board);
        return board;
    }

    public Board findById(Long id){
        Board board = boards.get(id);
        if (board == null) throw new IllegalArgumentException("존재하지 않는 board id: " + id);
        return board;
    }

    public List<Board> findAll() {
        return new ArrayList<>(boards.values());
    }

    public void deleteById(Long id) {
        boards.remove(id);
    }
}
