package com.jpaExample.jpaExample1.repository;

import com.jpaExample.jpaExample1.domain.Item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {
    private final EntityManager em;
    public void save(Item item) {
        // 아이템은 JPA저장 전까지 ID값이 없음
        if (item.getId() == null) {
            // 생성
            //ID값이 없으면 신규로 보고 저장
            em.persist(item);
        } else {
            // 업데이트
            //ID값이 있으면 이미 저장된 엔티티를 수정한다고 보고 수정
            em.merge(item);
        }
    }
    public Item findOne(Long id) {
        return em.find(Item.class, id);
    }
    public List<Item> findAll() {
        return em.createQuery("select i from Item i",Item.class).getResultList();
    }
}