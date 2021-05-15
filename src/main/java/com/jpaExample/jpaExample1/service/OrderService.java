package com.jpaExample.jpaExample1.service;

import com.jpaExample.jpaExample1.domain.Item.Item;
import com.jpaExample.jpaExample1.domain.code.DeliveryStatus;
import com.jpaExample.jpaExample1.domain.member.Member;
import com.jpaExample.jpaExample1.domain.order.Delivery;
import com.jpaExample.jpaExample1.domain.order.Order;
import com.jpaExample.jpaExample1.domain.order.OrderItem;
import com.jpaExample.jpaExample1.repository.ItemRepository;
import com.jpaExample.jpaExample1.repository.MemberRepository;
import com.jpaExample.jpaExample1.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {
    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;

    /** 주문 */
    @Transactional
    public Long order(Long memberId, Long itemId, int count) {

        //엔티티 조회
        Member member = memberRepository.findByOne(memberId);
        Item item = itemRepository.findOne(itemId);

        //배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());
        delivery.setStatus(DeliveryStatus.READY);

        //주문상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(),count);

        /*
        이런식으로 개발도 가능
        //이런식으로 퍼지게 되면 생성로직을 변경 및 필드 추가시 분산되기때문에 유지보수가힘듬
        //이런식으로 생성이 안되도록 막아야함
        // 엔티티에 protected로 생성자를 만들어서 사용하지 못하도록 가능
        OrderItem orderItem1 = new OrderItem();
        orderItem.setCount();
        ....
        */

        //주문 생성
        Order order = Order.createOrder(member, delivery, orderItem);

        //주문 저장
        //order안에서 casCade를 하기 때문에 order만 저장해도 delivery, orderItem도 함께 저장이 됨
        //caseCade범위는 persist하는 라이프사이클이 똑같을때 사용한다?
        orderRepository.save(order);
        return order.getId();
    }

    /** 주문 취소 */
    @Transactional
    public void cancelOrder(Long orderId) {

        //주문 엔티티 조회
        Order order = orderRepository.findOne(orderId);

        //주문 취소
        order.cancel();
    }
    /** 주문 검색 */
/*
 public List<Order> findOrders(OrderSearch orderSearch) {
 return orderRepository.findAll(orderSearch);
 }
*/

}
