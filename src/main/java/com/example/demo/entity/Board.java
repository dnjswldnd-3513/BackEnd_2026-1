package com.example.demo.entity;

public class Board {
    private Long id;
    private String name;

    public Board(String name) {
        this.name = name;
    }

    public void crateId(Long id){
        this.id = id;
    }

    public void update(String name){
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
