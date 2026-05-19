package com.example.demo.repository;

import com.example.demo.entity.Article;
import com.example.demo.entity.Board;
import com.example.demo.entity.Member;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArticleRepository {

    private Map<Long, Article> articles = new HashMap<>();
    private Map<Long, Member> members = new HashMap<>();
    private Map<Long, Board> boards = new HashMap<>();
    private Long articleNextId = 0L;

    public List<Article> findAll(){
        return  new ArrayList<>(articles.values());
    }

    public Article findById(Long id){
        Article article = articles.get(id);
        if (article == null) throw new IllegalArgumentException("존재하지 않는 article id: " + id);
        return article;
    }

    public Member findMemberById(Long id){
        Member member = members.get(id);
        if (member == null) throw new IllegalArgumentException("존재하지 않는 member id: " + id);
        return member;
    }

    public Board findBoardById(Long id){
        Board board = boards.get(id);
        if (board == null) throw new IllegalArgumentException("board id: " + id);
        return board;
    }

    public Article save(Article article){
        article.setId(articleNextId);
        article.setCreatedAt(LocalDateTime.now());
        article.setUpdatedAt(LocalDateTime.now());
        articles.put(article.getId(),article);
        return article;
    }

    public Article update(Long id,String title,String content){
        Article article = findById(id);
        article.setTitle(title);
        article.setContent(content);
        article.setUpdatedAt(LocalDateTime.now());
        return article;
    }

    public void delete(Long id){
        if (!articles.containsKey(id)) throw new IllegalArgumentException("존재하지 않는 article id:" +id);
        articles.remove(id);
    }

}
