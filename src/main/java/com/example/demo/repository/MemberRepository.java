package com.example.demo.repository;

import com.example.demo.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long> {
    boolean existsByEmail(String email);
    boolean existsByEmailAndIdNot(String email,Long id);
    Optional<Member> findByEmail(String email);
    // 해당 이메일이 없을수도 있으니 Optional로 만듬
    // 또 로그인할때 email로 회원을 찾아야함 근데 기존 method는 findbyid이기에 email을 만듬
}
