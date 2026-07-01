package com.example.demo.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}") // application.yml에 적은 값을 코드로 가져오는 방법 ->
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    public String generateToken(Long memberId) { //로그인 성공시 memberId를 담아서 jwt를 만듬. 나중에 이 토큰을 받으면 누가 보낸건지 알수 있게
        return Jwts.builder()
                .subject(String.valueOf(memberId)) // payload에 memberid를 넣는다.
                .issuedAt(new Date()) // 발급 시간 기록
                .expiration(new Date(System.currentTimeMillis() + expiration)) // 만료시간을 설정
                .signWith(getKey()) //secret key로 서명,
                .compact();
    }

    private SecretKey getKey() { // 객체는 secret문자열인데 이걸 hmac-sha256 키 객체로 변환해서 라이브러리의 요구에 맞게
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }
}
