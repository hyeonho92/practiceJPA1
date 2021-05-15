package com.jpaExample.jpaExample1.repository;

import com.jpaExample.jpaExample1.domain.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import java.util.List;

@Repository // 스프링 빈으로 등록
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager em;

    /*
    @PersistenceUnit // 엔티티 매니저 팩토리를 주입받고 싶으면 이걸 사용
    private EntityManagerFactory emf;
    test
     */

    public void save(Member member) {
        em.persist(member);
    }

    public Member findByOne(Long id) {
        return em.find(Member.class, id);
    }

    public List<Member> findByAll() {
        // 엔티티 객체로 쿼리를 보냄
        return em.createQuery("select m from Member m", Member.class).getResultList();
    }

    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class).setParameter("name", name).getResultList();
    }
}
