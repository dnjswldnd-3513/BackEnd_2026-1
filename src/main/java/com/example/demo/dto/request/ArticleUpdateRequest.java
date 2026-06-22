package com.example.demo.dto.request;

public record ArticleUpdateRequest(Long authorId,Long boardId,String title,String content) {
}
