package com.example.demo.dao;

import com.example.demo.entity.Article;
import com.example.demo.exception.EntityNotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ArticleDao {

    @PersistenceContext
    private EntityManager entityManager;

    public Article save(Article article) {
        entityManager.persist(article);
        return article;
    }

    public Article findById(Long id) {
        Article article = entityManager.find(Article.class, id);
        if (article == null)
            throw new EntityNotFoundException("존재하지 않는 article id: " + id);
        return article;
    }

    public List<Article> findAll() {
        return entityManager.createQuery("SELECT a FROM Article a", Article.class)
                .getResultList();
    }

    public List<Article> findByBoardId(Long boardId) {
        return entityManager.createQuery(
                        "SELECT a FROM Article a WHERE a.boardId = :boardId", Article.class)
                .setParameter("boardId", boardId)
                .getResultList();
    }

    public void deleteById(Long id) {
        Article article = findById(id);
        entityManager.remove(article);
    }

    public boolean existsByAuthorId(Long authorId) {
        Long count = entityManager.createQuery(
                        "SELECT COUNT(a) FROM Article a WHERE a.authorId = :authorId", Long.class)
                .setParameter("authorId", authorId)
                .getSingleResult();
        return count > 0;
    }

    public boolean existsByBoardId(Long boardId) {
        Long count = entityManager.createQuery(
                        "SELECT COUNT(a) FROM Article a WHERE a.boardId = :boardId", Long.class)
                .setParameter("boardId", boardId)
                .getSingleResult();
        return count > 0;
    }
}
