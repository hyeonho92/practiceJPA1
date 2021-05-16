package com.jpaExample.jpaExample1.domain.order;

import com.jpaExample.jpaExample1.domain.code.OrderStatus;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderSearch {

    private String memberName;

    private OrderStatus orderStatus;
}