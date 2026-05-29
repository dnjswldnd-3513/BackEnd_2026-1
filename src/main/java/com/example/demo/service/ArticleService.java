package com.example.demo.service;

import com.example.demo.entity.Article;
import com.example.demo.entity.Board;
import com.example.demo.entity.Member;
import com.example.demo.exception.BadRequestException;
import com.example.demo.repository.ArticleRepository;
import com.example.demo.repository.BoardRepository;
import com.example.demo.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;

    public ArticleService(ArticleRepository articleRepository, MemberRepository memberRepository, BoardRepository boardRepository) {
        this.articleRepository = articleRepository;
        this.memberRepository = memberRepository;
        this.boardRepository = boardRepository;
    }

    public List<Map<String,Object>> getArticles(){
        return articleRepository.findAll().stream().map(this::toMap).collect(Collectors.toList());
    }

    private Map<String,Object> toMap(Article article){
        Map<String,Object> map = new HashMap<>();
        Member member = memberRepository.findById(article.getMemberId());
        map.put("title",article.getTitle());
        map.put("author",member.getName());
        map.put("date",article.getCreatedAt());
        map.put("content",article.getContent());
        return map;
    }

    public Map<String,Object> getArticle(Long id){
        return toMap(articleRepository.findById(id));
    }

    public Article createArticle(Long memberId, Long boardId, String title, String content) {
        if (memberId == null || boardId == null || title == null || content == null) throw new BadRequestException("memberId,boardId, title,content는 필수입니다.");
        if (!memberRepository.checkById(memberId)) throw new BadRequestException("존재하지 않는 사용자입니다."+memberId);
        if (!boardRepository.checkById(boardId)) throw new BadRequestException("존재하지 않는 게시판입니다.."+boardId);
        return articleRepository.save(new Article(memberId, boardId, title, content));
    }

    public Article updateArticle(Long id, Long memberId, Long boardId, String title, String content) {
        if (memberId == null || boardId == null || title == null || content == null) {
            throw new BadRequestException("memberId, boardId, title, content는 필수입니다.");
        }
        if (!memberRepository.checkById(memberId)) {
            throw new BadRequestException("존재하지 않는 사용자입니다: " + memberId);
        }
        if (!boardRepository.checkById(boardId)) {
            throw new BadRequestException("존재하지 않는 게시판입니다: " + boardId);
        }
        return articleRepository.update(id, memberId, boardId, title, content);
    }

    public void deleteArticle(Long id) {
        articleRepository.delete(id);
    }

    public void setPostsModel(Model model){
        Board board = boardRepository.findById(1L);
        model.addAttribute("boardName",board.getName());
        model.addAttribute("articles",getArticles());
    }

    public List<Map<String,Object>> getArticlesByBoard(Long boardId) {
        return articleRepository.findByBoardId(boardId)
                .stream()
                .map(this::toMap)
                .collect(Collectors.toList());
    }

}
