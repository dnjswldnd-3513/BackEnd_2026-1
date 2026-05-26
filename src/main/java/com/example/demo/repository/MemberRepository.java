package com.example.demo.repository;

import com.example.demo.entity.Member;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class MemberRepository {

    private final Map<Long, Member> members = new HashMap<>();
    private Long nextId = 0L;


    public MemberRepository() {
        //testcode
        save(new Member("테스트유저이름","testemail","1234"));
    }

    public Member save(Member member){
        member.createId(++nextId);
        members.put(member.getId(),member);
        return member;
    }

    public Member findById(Long id){
        Member member = members.get(id);
        if(member == null){
            throw new IllegalArgumentException("존재하지 않는 member id " + id);
        }
        return member;
    }

    public List<Member> findAll() {
        return new ArrayList<>(members.values());
    }

    public void deleteById(Long id) {
        members.remove(id);
    }

}
