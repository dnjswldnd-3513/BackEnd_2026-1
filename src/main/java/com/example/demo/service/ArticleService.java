package com.example.demo.service;

import com.example.demo.entity.Article;
import com.example.demo.entity.Member;
import com.example.demo.repository.ArticleRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ArticleService {

    private ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public List<Map<String,Object>> getArticles(){
        List<Map<String,Object>> result = new ArrayList<>();
        return articleRepository.findAll().stream().map(this::toMap).collect(Collectors.toList());
    }

    private Map<String,Object> toMap(Article article){
        Map<String,Object> map = new HashMap<>();
        Member member = articleRepository.findMemberById(article.getMemberID());
        map.put("title",article.getTitle());
        map.put("author",member.getName());
        map.put("date",article.getCreatedAt());
        map.put("content",article.getContent());
        return map;
    }

    public Map<String,Object> getArticle(Long id){
        return toMap(articleRepository.findById(id));
    }

    public Article createArticle(Long memberId, Long boardId, String title, String content) {
        return articleRepository.save(new Article(memberId, boardId, title, content));
    }

    public Article updateArticle(Long id, String title, String content) {
        return articleRepository.update(id, title, content);
    }

    public void deleteArticle(Long id) {
        articleRepository.delete(id);
    }



}
