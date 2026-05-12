package com.example.demo.json;

public class Entity {

    private String name;
    private Long age;

    public Entity(Long age, String name) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public String getAge() {
        return age.toString();
    }
}
