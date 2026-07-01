package com.example.demo.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "article")
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch =  FetchType.LAZY) // 여러 글이 한 회원에 속할때(글:회원 ----> N:1) 일때 쓴다
    @JoinColumn(name = "author_id", nullable = false)
    private Member member;

    @JoinColumn(name = "board_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(name = "created_date", insertable = false, updatable = false)
    private LocalDateTime createdDate;
    @Column(name = "modified_date", insertable = false, updatable = false)
    private LocalDateTime modifiedDate;

    public Article() {
    }

    public Article(Member member, Board board, String title, String content) {
        this.member = member;
        this.board = board;
        this.title = title;
        this.content = content;
    }

    public void update(Member member, Board board, String title, String content) {
        this.member = member;
        this.board = board;
        this.title = title;
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Member getMember() {
        return member;
    }

    public Board getBoard() {
        return board;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public LocalDateTime getModifiedDate() {
        return modifiedDate;
    }
}
//현재 할거는 해당 객체에는 1 이런식으로 숫자 형태로  어디 게시판인지를 확인하는 방식인데 이걸 숫자가 아닌 객체로 알기 위해서 바꾼다.
