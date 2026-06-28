package com.example.demo.service;

import com.example.demo.dto.request.ArticleCreateRequest;
import com.example.demo.dto.request.ArticleUpdateRequest;
import com.example.demo.dto.response.ArticleResponse;
import com.example.demo.entity.Article;
import com.example.demo.entity.Board;
import com.example.demo.entity.Member;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.EntityNotFoundException;
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

    private ArticleRepository articleRepository;
    private MemberRepository memberRepository;
    private BoardRepository boardRepository;

    public ArticleService(ArticleRepository articleRepository, MemberRepository memberRepository, BoardRepository boardRepository) {
        this.articleRepository = articleRepository;
        this.memberRepository = memberRepository;
        this.boardRepository = boardRepository;
    }

    @Transactional(readOnly = true)
    public List<ArticleResponse> getArticles() {
        return articleRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    private ArticleResponse toResponse(Article article) {
        return new ArticleResponse(article.getId(),
                article.getMember().getId(),
                article.getBoard().getId(),
                article.getTitle(),
                article.getContent(),
                article.getCreatedDate(),
                article.getModifiedDate());
    }


    private Article findArticleById(Long id) {
        return articleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 article id입니다. id : "+id));
    }
    @Transactional(readOnly = true)
    public ArticleResponse getArticle(Long id) {
        return toResponse(findArticleById(id));
    }


    @Transactional
    public ArticleResponse createArticle(ArticleCreateRequest request) {
        if (request.authorId() == null || request.boardId() == null || request.title() == null || request.content() == null)
            throw new BadRequestException("memberId,boardId, title,content는 필수입니다.");
        Member member = memberRepository.findById(request.authorId())
                .orElseThrow(() -> new BadRequestException("존재하지 않는 사용자입니다. : "+request.authorId()));
        Board board = boardRepository.findById(request.boardId()).orElseThrow(() -> new BadRequestException("존재하지 않는 게시판입니다. : "+request.boardId()));

        Article article = articleRepository.save(new Article(
                member,
                board,
                request.title(),
                request.content()));
        board.addArticles(article);
        Article sa = articleRepository.save(article);
        return toResponse(sa);
    }

    @Transactional
    public ArticleResponse updateArticle(Long id, ArticleUpdateRequest request) {
        if (request.authorId() == null || request.boardId() == null || request.title() == null || request.content() == null)
            throw new BadRequestException("memberId, boardId, title, content는 필수입니다.");

        Member member = memberRepository.findById(request.authorId())
                .orElseThrow(() -> new BadRequestException("존재하지 않는 사용자입니다: " + request.authorId()));

        Board board = boardRepository.findById(request.boardId())
                .orElseThrow(() -> new BadRequestException("존재하지 않는 게시판입니다: " + request.boardId()));

        Article article = findArticleById(id);

        article.update(
                member,
                board,
                request.title(),
                request.content());
        return toResponse(article);
    }

    @Transactional
    public void deleteArticle(Long id) {
        Article article = findArticleById(id);
        articleRepository.delete(article);
    }

    @Transactional(readOnly = true)
    public List<ArticleResponse> getArticlesByBoard(Long boardId) {
        return articleRepository.findByBoardId(boardId).stream()
                .map(this::toResponse)
                .toList();

    }

}
