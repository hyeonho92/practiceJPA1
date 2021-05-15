package com.jpaExample.jpaExample1.service;

import com.jpaExample.jpaExample1.domain.Item.Item;
import com.jpaExample.jpaExample1.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    // 생성
    @Transactional
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    //전체 조회
    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    //한건 조회
    public Item findOne(Long itemId) {
        return itemRepository.findOne(itemId);
    }
}