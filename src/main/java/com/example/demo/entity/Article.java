package com.example.demo.entity;

public class Article {
    private static Long nextId = 1L;

    private Long id;
    private String description;

    public Article(String description) {
        this.id = nextId++;
        this.description = description;
    }

    public Article(){
        this.id = nextId++;
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
