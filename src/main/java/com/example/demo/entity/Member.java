package com.example.demo.entity;

public class Member {

    private Long id;
    private String name;
    private String email;
    private String password;

    public Member(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public void createId(Long id){
        this.id = id;
    }

    public void update(String name,String email,String password){
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
