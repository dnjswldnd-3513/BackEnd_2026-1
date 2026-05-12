package com.example.demo.apitest;

public class Aricle {
    private static Long nextId = 1L;

    private Long id;
    private String description;

    public Aricle(String description) {
        this.id = nextId++;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }
}
