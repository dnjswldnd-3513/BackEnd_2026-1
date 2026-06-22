package com.example.demo.dao;

import com.example.demo.entity.Board;
import com.example.demo.exception.EntityNotFoundException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BoardDao {

    private final JdbcTemplate jdbcTemplate;

    public BoardDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Board save(Board board) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(c -> {
            var ps = c.prepareStatement(
                    "INSERT INTO board (name) VALUES (?)",
                    new String[]{"id"});
            ps.setString(1, board.getName());
            return ps;
        }, keyHolder);
        board.setId(keyHolder.getKey().longValue());
        return board;
    }

    public Board findById(Long id) {
        List<Board> results = jdbcTemplate.query(
                "SELECT * FROM board WHERE id = ?", boardRowMapper(), id);
        if (results.isEmpty())
            throw new EntityNotFoundException("존재하지 않는 board id: " + id);
        return results.get(0);
    }

    public List<Board> findAll() {
        return jdbcTemplate.query("SELECT * FROM board", boardRowMapper());
    }

    public void update(Board board) {
        jdbcTemplate.update("UPDATE board SET name = ? WHERE id = ?",
                board.getName(), board.getId());
    }

    public void deleteById(Long id) {
        jdbcTemplate.update("DELETE FROM board WHERE id = ?", id);
    }

    public boolean existsById(Long id) {
        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM board WHERE id = ?", Integer.class, id);
        return count != null && count > 0;
    }

    private RowMapper<Board> boardRowMapper() {
        return (rs, rowNum) -> new Board(
                rs.getLong("id"),
                rs.getString("name"));
    }
}
