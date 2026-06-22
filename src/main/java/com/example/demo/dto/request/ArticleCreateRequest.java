package com.example.demo.dto.request;

public record ArticleCreateRequest(Long authorId,Long boardId,String title,String content) {
}
