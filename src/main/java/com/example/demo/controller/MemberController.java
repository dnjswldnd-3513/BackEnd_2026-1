package com.example.demo.controller;

import com.example.demo.entity.Member;
import com.example.demo.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PutMapping("/members/{id}")
    public ResponseEntity<Member> update(@PathVariable Long id,@RequestBody Map<String,Object> body){
        return ResponseEntity.ok(memberService.updateMember(
                id,
                body.get("name").toString(),
                body.get("email").toString(),
                body.get("password").toString()
        ));
    }

    @GetMapping("/members")
    public ResponseEntity<List<Member>> getMembers(){
        return ResponseEntity.ok(memberService.getMembers());
    }

    @GetMapping("/members/{id}")
    public ResponseEntity<Member> getMembers(@PathVariable Long id){
        return ResponseEntity.ok(memberService.getMember(id));
    }

    @PostMapping("/members")
    public ResponseEntity<Member> create(@RequestBody Map<String ,Object> body){
        return ResponseEntity.status(HttpStatus.CREATED).body(memberService.createMember(
                body.get("name").toString(),
                body.get("email").toString(),
                body.get("password").toString()));
    }

    @DeleteMapping("/members/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        memberService.deleteMember(id);
        return ResponseEntity.noContent().build();
    }
}
