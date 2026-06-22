package com.example.demo.dao;

import com.example.demo.entity.Article;
import com.example.demo.exception.EntityNotFoundException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ArticleDao {

    private final JdbcTemplate jdbcTemplate;

    public ArticleDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Article save(Article article) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(c -> {
            var ps = c.prepareStatement(
                    "INSERT INTO article (author_id, board_id, title, content) VALUES (?, ?, ?, ?)",
                    new String[]{"id"});
            ps.setLong(1, article.getAuthorId());
            ps.setLong(2, article.getBoardId());
            ps.setString(3, article.getTitle());
            ps.setString(4, article.getContent());
            return ps;
        }, keyHolder);
        article.setId(keyHolder.getKey().longValue());
        return article;
    }

    public Article findById(Long id) {
        List<Article> results = jdbcTemplate.query(
                "SELECT * FROM article WHERE id = ?", articleRowMapper(), id);
        if (results.isEmpty())
            throw new EntityNotFoundException("존재하지 않는 article id: " + id);
        return results.get(0);
    }

    public List<Article> findAll() {
        return jdbcTemplate.query("SELECT * FROM article", articleRowMapper());
    }

    public List<Article> findByBoardId(Long boardId) {
        return jdbcTemplate.query(
                "SELECT * FROM article WHERE board_id = ?", articleRowMapper(), boardId);
    }

    public void update(Article article) {
        jdbcTemplate.update(
                "UPDATE article SET author_id = ?, board_id = ?, title = ?, content = ? WHERE id = ?",
                article.getAuthorId(), article.getBoardId(),
                article.getTitle(), article.getContent(), article.getId());
    }

    public void deleteById(Long id) {
        jdbcTemplate.update("DELETE FROM article WHERE id = ?", id);
    }

    public boolean existsByAuthorId(Long authorId) {
        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM article WHERE author_id = ?", Integer.class, authorId);
        return count != null && count > 0;
    }

    public boolean existsByBoardId(Long boardId) {
        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM article WHERE board_id = ?", Integer.class, boardId);
        return count != null && count > 0;
    }

    private RowMapper<Article> articleRowMapper() {
        return (rs, rowNum) -> new Article(
                rs.getLong("id"),
                rs.getLong("author_id"),
                rs.getLong("board_id"),
                rs.getString("title"),
                rs.getString("content"),
                rs.getTimestamp("created_date").toLocalDateTime(),
                rs.getTimestamp("modified_date").toLocalDateTime());
    }
}
