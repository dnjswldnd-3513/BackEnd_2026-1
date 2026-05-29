package com.example.demo.repository;

import com.example.demo.entity.Article;
import com.example.demo.entity.Board;
import com.example.demo.entity.Member;
import com.example.demo.exception.EntityNotFoundException;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ArticleRepository {

    private Map<Long, Article> articles = new HashMap<>();
    private Long nextId = 0L;

    public List<Article> findAll(){
        return  new ArrayList<>(articles.values());
    }

    public Article findById(Long id){
        Article article = articles.get(id);
        if (article == null) throw new EntityNotFoundException("존재하지 않는 article id: " + id);
        return article;
    }

    public Article save(Article article){
        article.setId(++nextId);
        articles.put(article.getId(),article);
        return article;
    }

    public Article update(Long id,String title,String content){
        Article article = findById(id);
        article.update(title,content);
        return article;
    }

    public void delete(Long id){
        if (!articles.containsKey(id)) throw new EntityNotFoundException("존재하지 않는 article id:" +id);
        articles.remove(id);
    }

    public List<Article> findByBoardId(Long boardId) {
        return articles.values().stream()
                .filter(a -> a.getBoardId().equals(boardId))
                .toList();
    }

}
