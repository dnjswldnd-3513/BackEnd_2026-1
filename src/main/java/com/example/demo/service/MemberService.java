package com.example.demo.service;

import com.example.demo.entity.Member;
import com.example.demo.exception.BadRequestException;
import com.example.demo.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public List<Member> getMembers(){
        return memberRepository.findAll();
    }

    public Member getMember(Long id){
        return memberRepository.findById(id);
    }

    public Member createMember(String name, String email,String pass){
        if (name == null || email == null || pass == null) throw new BadRequestException("name,email,password는 필수입니다.");
        return memberRepository.save(new Member(name,email,pass));
    }

    public Member updateMember(Long id, String name, String email,String pass){
        Member member = memberRepository.findById(id);
        member.update(name,email,pass);
        return member;
    }

    public void deleteMember(Long id){
        memberRepository.deleteById(id);
    }
}
