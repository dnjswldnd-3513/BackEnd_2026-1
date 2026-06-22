package com.example.demo.dao;

import com.example.demo.entity.Member;
import com.example.demo.exception.EntityNotFoundException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MemberDao {

    private final JdbcTemplate jdbcTemplate;

    public MemberDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Member save(Member member) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(c -> {
            var ps = c.prepareStatement(
                    "INSERT INTO member (name, email, password) VALUES (?, ?, ?)",
                    new String[]{"id"});
            ps.setString(1, member.getName());
            ps.setString(2, member.getEmail());
            ps.setString(3, member.getPassword());
            return ps;
        }, keyHolder);
        member.setId(keyHolder.getKey().longValue());
        return member;
    }

    public Member findById(Long id) {
        List<Member> results = jdbcTemplate.query(
                "SELECT * FROM member WHERE id = ?", memberRowMapper(), id);
        if (results.isEmpty())
            throw new EntityNotFoundException("존재하지 않는 member id: " + id);
        return results.get(0);
    }

    public List<Member> findAll() {
        return jdbcTemplate.query("SELECT * FROM member", memberRowMapper());
    }

    public void update(Member member) {
        jdbcTemplate.update(
                "UPDATE member SET name = ?, email = ?, password = ? WHERE id = ?",
                member.getName(), member.getEmail(), member.getPassword(), member.getId());
    }

    public void deleteById(Long id) {
        jdbcTemplate.update("DELETE FROM member WHERE id = ?", id);
    }

    public boolean checkByEmail(String email) {
        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM member WHERE email = ?", Integer.class, email);
        return count != null && count > 0;
    }

    public boolean checkByEmailAndIdNot(Long id, String email) {
        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM member WHERE email = ? AND id != ?",
                Integer.class, email, id);
        return count != null && count > 0;
    }

    public boolean existsById(Long id) {
        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM member WHERE id = ?", Integer.class, id);
        return count != null && count > 0;
    }

    private RowMapper<Member> memberRowMapper() {
        return (rs, rowNum) -> new Member(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("email"),
                rs.getString("password"));
    }
}
