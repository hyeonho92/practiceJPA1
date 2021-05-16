package com.jpaExample.jpaExample1.service;

import com.jpaExample.jpaExample1.domain.Item.Book;
import com.jpaExample.jpaExample1.domain.Item.Item;
import com.jpaExample.jpaExample1.domain.code.OrderStatus;
import com.jpaExample.jpaExample1.domain.common.Address;
import com.jpaExample.jpaExample1.domain.member.Member;
import com.jpaExample.jpaExample1.domain.order.Order;
import com.jpaExample.jpaExample1.exception.NotEnoughStockException;
import com.jpaExample.jpaExample1.repository.OrderRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrderServiceTest {

    @Autowired
    EntityManager em;

    @Autowired
    OrderService service;

    @Autowired
    OrderRepository repository;

    @Test
    public void order() throws Exception {

        Member member = createMember();
        Item item = createBook("JPA", 10000, 10); //이름, 가격, 재고
        int orderCount = 2;

        Long orderId = service.order(member.getId(), item.getId(), orderCount);

        Order getOrder = repository.findOne(orderId);
        Assert.assertEquals(OrderStatus.ORDER, getOrder.getStatus());
        Assert.assertEquals(1, getOrder.getOrderItems().size());
        Assert.assertEquals(10000 * 2, getOrder.getTotalPrice());
        Assert.assertEquals(8, item.getStockQuantity());
    }

    @Test(expected = NotEnoughStockException.class)
    public void inventory() throws Exception {
        //Given
        Member member = createMember();
        Item item = createBook("JPA", 10000, 10); //이름, 가격, 재고
        int orderCount = 11;

        service.order(member.getId(), item.getId(), orderCount);
        //Then
        Assert.fail("재고 수량 부족 예외가 발생해야 한다.");
    }

    @Test
    public void cancel() {
        Member member = createMember();
        Item item = createBook("JPA", 10000, 10); //이름, 가격, 재고
        int orderCount = 2;
        Long orderId = service.order(member.getId(), item.getId(), orderCount);

        service.cancelOrder(orderId);

        Order getOrder = repository.findOne(orderId);
        Assert.assertEquals(OrderStatus.CANCEL, getOrder.getStatus());
        Assert.assertEquals(10, item.getStockQuantity());
    }

    private Member createMember() {
        Member member = new Member();
        member.setName("회원1");
        member.setAddress(new Address("서울", "강가", "123-123")); em.persist(member);
        return member;
    }

    private Book createBook(String name, int price, int stockQuantity) {
        Book book = new Book();
        book.setName(name);
        book.setStockQuantity(stockQuantity);
        book.setPrice(price);
        em.persist(book);
        return book;
    }

}
