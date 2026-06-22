package com.example.demo.service;

import com.example.demo.dao.ArticleDao;
import com.example.demo.dao.BoardDao;
import com.example.demo.dao.MemberDao;
import com.example.demo.dto.request.ArticleCreateRequest;
import com.example.demo.dto.request.ArticleUpdateRequest;
import com.example.demo.dto.response.ArticleResponse;
import com.example.demo.entity.Article;
import com.example.demo.entity.Board;
import com.example.demo.entity.Member;
import com.example.demo.exception.BadRequestException;
import com.example.demo.repository.ArticleRepository;
import com.example.demo.repository.BoardRepository;
import com.example.demo.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ArticleService {


    private final ArticleDao articleDao;
    private final MemberDao memberDao;
    private final BoardDao boardDao;

    public ArticleService(ArticleDao articleDao, MemberDao memberDao, BoardDao boardDao) {
        this.articleDao = articleDao;
        this.memberDao = memberDao;
        this.boardDao = boardDao;
    }

    @Transactional(readOnly = true)
    public List<ArticleResponse> getArticles() {
        return articleDao.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    private ArticleResponse toResponse(Article article) {
        return new ArticleResponse(article.getId(), article.getAuthorId(), article.getBoardId(), article.getTitle(), article.getContent(), article.getCreatedDate(), article.getModifiedDate());
    }

    @Transactional(readOnly = true)
    public ArticleResponse getArticle(Long id) {
        return toResponse(articleDao.findById(id));
    }


    @Transactional
    public ArticleResponse createArticle(ArticleCreateRequest request) {
        if (request.authorId() == null || request.boardId() == null || request.title() == null || request.content() == null)
            throw new BadRequestException("memberId,boardId, title,content는 필수입니다.");
        if (!memberDao.existsById(request.authorId()))
            throw new BadRequestException("존재하지 않는 사용자입니다." + request.authorId());
        if (!boardDao.existsById(request.boardId()))
            throw new BadRequestException("존재하지 않는 게시판입니다.." + request.boardId());

        Article article = articleDao.save(new Article(request.authorId(), request.boardId(), request.title(), request.content()));
        return toResponse(articleDao.findById(article.getId()));
    }

    @Transactional
    public ArticleResponse updateArticle(Long id, ArticleUpdateRequest request) {
        if (request.authorId() == null || request.boardId() == null || request.title() == null || request.content() == null)
            throw new BadRequestException("memberId, boardId, title, content는 필수입니다.");
        if (!memberDao.existsById(request.authorId()))
            throw new BadRequestException("존재하지 않는 사용자입니다: " + request.authorId());
        if (!boardDao.existsById(request.boardId()))
            throw new BadRequestException("존재하지 않는 게시판입니다: " + request.boardId());

        Article article = articleDao.findById(id);
        article.update(request.authorId(), request.boardId(), request.title(), request.content());
        articleDao.update(article);
        return toResponse(articleDao.findById(article.getId()));
    }

    @Transactional
    public void deleteArticle(Long id) {
        articleDao.findById(id);
        articleDao.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<ArticleResponse> getArticlesByBoard(Long boardId) {
        return articleDao.findByBoardId(boardId).stream()
                .map(this::toResponse)
                .toList();

    }

}
