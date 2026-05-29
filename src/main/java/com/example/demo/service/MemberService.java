package com.example.demo.service;

import com.example.demo.entity.Member;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.DuplicationEmailException;
import com.example.demo.repository.ArticleRepository;
import com.example.demo.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final ArticleRepository articleRepository;

    public MemberService(MemberRepository memberRepository,ArticleRepository articleRepository) {
        this.memberRepository = memberRepository;
        this.articleRepository =articleRepository;
    }

    public List<Member> getMembers(){
        return memberRepository.findAll();
    }

    public Member getMember(Long id){
        return memberRepository.findById(id);
    }

    public Member createMember(String name, String email,String pass){
        if (name == null || email == null || pass == null) throw new BadRequestException("name,email,password는 필수입니다.");
        if (memberRepository.checkByEmail(email)) throw new DuplicationEmailException("이미 사용중인 이메일입니다 :" + email);
        return memberRepository.save(new Member(name,email,pass));
    }

    public Member updateMember(Long id, String name, String email,String pass){
        if (memberRepository.checkByEmailAndID(id,email)) throw new DuplicationEmailException("이미 사용중인 이메일입니다.:"+email);
        Member member = memberRepository.findById(id);
        member.update(name,email,pass);
        return member;
    }

    public void deleteMember(Long id){
        memberRepository.findById(id);
        if (articleRepository.checkByMemberId(id)) throw new BadRequestException("작성한 게시물이 있어서 삭제할수 없습니다.");
        memberRepository.deleteById(id);
    }
}
