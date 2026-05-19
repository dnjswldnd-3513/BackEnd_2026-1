package com.example.demo.entity;

public class Json {

    private String name;
    private Long age;

    public Json(Long age, String name) {
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
