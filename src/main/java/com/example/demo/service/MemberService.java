package com.example.demo.service;

import com.example.demo.entity.Member;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.DuplicationEmailException;
import com.example.demo.dao.ArticleDao;
import com.example.demo.dao.MemberDao;
import com.example.demo.dto.request.MemberCreateRequest;
import com.example.demo.dto.request.MemberUpdateRequest;
import com.example.demo.dto.response.MemberResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MemberService {

    private final MemberDao memberDao;
    private final ArticleDao articleDao;

    public MemberService(MemberDao memberDao, ArticleDao articleDao) {
        this.memberDao = memberDao;
        this.articleDao = articleDao;
    }

    @Transactional(readOnly = true)
    public List<MemberResponse> getMembers() {
        return memberDao.findAll().stream()
                .map(m -> new MemberResponse(m.getId(), m.getName(), m.getEmail()))
                .toList();
    }

    @Transactional(readOnly = true)
    public MemberResponse getMember(Long id) {
        Member member = memberDao.findById(id);
        return new MemberResponse(member.getId(), member.getName(), member.getEmail());
    }

    @Transactional
    public MemberResponse createMember(MemberCreateRequest request) {
        if (request.name() == null || request.email() == null || request.password() == null)
            throw new BadRequestException("name, email, password는 필수입니다.");
        if (memberDao.checkByEmail(request.email()))
            throw new DuplicationEmailException("이미 사용 중인 이메일입니다: " + request.email());
        Member member = memberDao.save(new Member(request.name(), request.email(), request.password()));
        return new MemberResponse(member.getId(), member.getName(), member.getEmail());
    }

    @Transactional
    public MemberResponse updateMember(Long id, MemberUpdateRequest request) {
        if (memberDao.checkByEmailAndIdNot(id, request.email()))
            throw new DuplicationEmailException("이미 사용 중인 이메일입니다: " + request.email());
        Member member = memberDao.findById(id);
        member.update(request.name(), request.email(), request.password());
        memberDao.update(member);
        return new MemberResponse(member.getId(), member.getName(), member.getEmail());
    }

    @Transactional
    public void deleteMember(Long id) {
        memberDao.findById(id);
        if (articleDao.existsByAuthorId(id))
            throw new BadRequestException("작성한 게시물이 있어 삭제할 수 없습니다.");
        memberDao.deleteById(id);
    }
}
