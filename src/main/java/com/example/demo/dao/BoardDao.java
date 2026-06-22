package com.example.demo.dao;

import com.example.demo.entity.Board;
import com.example.demo.exception.EntityNotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BoardDao {

    @PersistenceContext
    private EntityManager entityManager;

    public Board save(Board board) {
        entityManager.persist(board);
        return board;
    }

    public Board findById(Long id) {
        Board board = entityManager.find(Board.class, id);
        if (board == null) throw new EntityNotFoundException("존재하지 않는 board id: " + id);
        return board;
    }

    public List<Board> findAll() {
        return entityManager.createQuery("SELECT b FROM Board b", Board.class)
                .getResultList();
    }

    public void deleteById(Long id) {
        Board board = findById(id);
        entityManager.remove(board);
    }

    public boolean existsById(Long id) {
        Long count = entityManager.createQuery(
                        "SELECT COUNT(b) FROM Board b WHERE b.id = :id", Long.class)
                .setParameter("id", id)
                .getSingleResult();
        return count > 0;
    }

}
