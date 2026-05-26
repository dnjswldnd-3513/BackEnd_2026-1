package com.example.demo.entity;

import java.time.LocalDateTime;

public class Article {

    private Long id;
    private Long memeberId;
    private Long boardId;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Article(Long memeberId, Long boardId, String title, String content) {
        this.memeberId = memeberId;
        this.boardId = boardId;
        this.title = title;
        this.content = content;
        this.createdAt = LocalDateTime.now();
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void update(String title,String content){
        this.content = content;
        this.title = title;
        this.updatedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public Long getMemberID() {
        return memeberId;
    }

    public Long getBoardId() {
        return boardId;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
