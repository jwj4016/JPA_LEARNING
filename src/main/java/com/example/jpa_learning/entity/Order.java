package com.example.jpa_learning.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

//다대다:새로운 기본 키 사용. 복합키를 만들지 않아도 돼서 간단히 매핑 가능.
@Getter
@Setter
@Entity
@Table(schema = "jpa", name = "ORDER")
public class Order {
    @Id
    @GeneratedValue
    @Column(name = "ORDER_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    private int orderAmount;

    private Date orderDate;
}
