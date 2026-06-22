package com.example.demo.dto.response;
import java.time.LocalDateTime;
public record ArticleResponse(Long id, Long authorId, Long boardId, String title, String content,
                              LocalDateTime createdDate, LocalDateTime modifiedDate) {}
