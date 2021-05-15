package com.jpaExample.jpaExample1.domain.order;

import javax.persistence.*;

import com.jpaExample.jpaExample1.domain.common.Address;
import com.jpaExample.jpaExample1.domain.code.DeliveryStatus;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Delivery {
    @Id @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
    private Order order;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING) // ORDINAL는 숫자로 들어가기 때문에 추가로 생겼을때 문제가 많이 생길수 있기 때문에 STRING사용
    private DeliveryStatus status; //ENUM [READY(준비), COMP(배송)]
}