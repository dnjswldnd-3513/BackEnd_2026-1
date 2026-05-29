package com.example.demo.entity;

import java.time.LocalDateTime;

public class Article {

    private Long id;
    private Long memberId;
    private Long boardId;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Article(Long memberId, Long boardId, String title, String content) {
        this.memberId = memberId;
        this.boardId = boardId;
        this.title = title;
        this.content = content;
        this.createdAt = LocalDateTime.now();
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void update(Long memberId, Long boardId, String title, String content) {
        this.memberId = memberId;
        this.boardId = boardId;
        this.title = title;
        this.content = content;
        this.updatedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public Long getMemberId() {
        return memberId;
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
