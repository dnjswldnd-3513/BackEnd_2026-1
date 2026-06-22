package com.example.demo.dao;

import com.example.demo.entity.Member;
import com.example.demo.exception.EntityNotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MemberDao {

    @PersistenceContext
    private EntityManager entityManager;

    public Member save(Member member) {
        entityManager.persist(member);
        return member;
    }

    public Member findById(Long id) {
        Member member = entityManager.find(Member.class, id);
        if (member == null) throw new EntityNotFoundException("존재하지 않는 member id: " + id);
        return member;
    }

    public List<Member> findAll() {
        return entityManager.createQuery("SELECT m FROM Member m", Member.class).getResultList();
    }

    public void deleteById(Long id) {
        Member member = findById(id);
        entityManager.remove(member);
    }

    public boolean checkByEmail(String email) {
        Long count = entityManager.createQuery(
                        "SELECT COUNT(m) FROM Member m WHERE m.email = :email", Long.class)
                .setParameter("email", email)
                .setParameter("email", email)
                .getSingleResult();
        return count > 0;
    }

    public boolean checkByEmailAndNot(Long id, String email) {
        Long count = entityManager.createQuery(
                        "SELECT COUNT(m) FROM Member m WHERE m.email = :email AND m.id != :id", Long.class)
                .setParameter("email", email)
                .setParameter("id", id)
                .getSingleResult();
        return count > 0;
    }

    public boolean existsById(Long id) {
        Long count = entityManager.createQuery("SELECT COUNT(m) FROM Member m WHERE m.id = :id", Long.class)
                .setParameter("id", id)
                .getSingleResult();
        return count > 0;
    }
}
