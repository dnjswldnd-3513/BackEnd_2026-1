package com.example.demo.repository;

import com.example.demo.entity.Article;
import com.example.demo.entity.Board;
import com.example.demo.entity.Member;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArticleRepository {

    private Map<Long, Article> articles = new HashMap<>();
    private Map<Long, Member> members = new HashMap<>();
    private Map<Long, Board> boards = new HashMap<>();

    public ArticleRepository() {

    }
    public void initMembers(){

    }
    public void initBoards(){

    }
    public void initArticles(){

    }

    public List<Article> findAll(){
        return  null;
    }

    public Article findById(Long id){
        return  null;
    }

    public Member findMemberById(Long id){
        return null;
    }

    public Board findBoardById(Long id){
        return null;
    }

    public Article save(){
        return null;
    }

    public Article update(){
        return null;
    }

    public void delete(){

    }

}
