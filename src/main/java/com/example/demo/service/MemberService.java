package com.example.demo.service;

import com.example.demo.dto.request.LoginRequest;
import com.example.demo.dto.response.LoginResponse;
import com.example.demo.entity.Member;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.DuplicationEmailException;
import com.example.demo.dto.request.MemberCreateRequest;
import com.example.demo.dto.request.MemberUpdateRequest;
import com.example.demo.dto.response.MemberResponse;
import com.example.demo.exception.EntityNotFoundException;
import com.example.demo.repository.MemberRepository;
import com.example.demo.util.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public MemberService(MemberRepository memberRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @Transactional(readOnly = true)
    public List<MemberResponse> getMembers() {
        return memberRepository.findAll().stream()
                .map(m -> new MemberResponse(m.getId(), m.getName(), m.getEmail()))
                .toList();
    }

    private Member findMemberById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 member id 입니다. id : " + id));
    }

    @Transactional(readOnly = true)
    public MemberResponse getMember(Long id) {
        Member member = findMemberById(id);
        return new MemberResponse(member.getId(), member.getName(), member.getEmail());
    }

    @Transactional
    public MemberResponse createMember(MemberCreateRequest request) {
        if (request.name() == null || request.email() == null || request.password() == null)
            throw new BadRequestException("name, email, password는 필수입니다.");
        if (memberRepository.existsByEmail(request.email()))
            throw new DuplicationEmailException("이미 사용 중인 이메일입니다: " + request.email());
        String txt = passwordEncoder.encode(request.password());
        Member member = memberRepository.save(new Member(request.name(), request.email(), txt));
        return new MemberResponse(member.getId(), member.getName(), member.getEmail());
    }

    @Transactional
    public MemberResponse updateMember(Long id, MemberUpdateRequest request) {
        if (memberRepository.existsByEmailAndIdNot(request.email(), id))
            throw new DuplicationEmailException("이미 사용 중인 이메일입니다: " + request.email());
        String txt = passwordEncoder.encode(request.password());
        Member member = findMemberById(id);
        member.update(request.name(), request.email(),txt);
        return new MemberResponse(member.getId(), member.getName(), member.getEmail());
    }

    @Transactional
    public void deleteMember(Long id) {
        Member member = findMemberById(id);
        if (!member.getArticles().isEmpty())
            throw new BadRequestException("작성한 게시물이 있어 삭제할 수 없습니다.");
        memberRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public LoginResponse login(LoginRequest request) {
        Member member = memberRepository.findByEmail(request.email())
                .orElseThrow(() -> new BadRequestException("이메일 또는 비밀번호가 올바르지 않습니다."));
        if (!passwordEncoder.matches(request.password(), member.getPassword())) throw new BadRequestException("이메일 또는 비밀번호가 올바르지 않습니다.");
        String token = jwtUtil.generateToken(member.getId());
        return new LoginResponse(token);
    }
}
