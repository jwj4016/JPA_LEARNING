package com.example.jpa_learning.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(schema = "jpa", name = "PRODUCT")
public class Product {

    @Id
    @Column(name = "PRODUCT_ID")
    private String id;

    private String name;

    //1. 역방향 추가(다대다 단방향에서 양방향으로 변경)
    //2. 상품 엔티티에서 회원상품 엔티티로 객체 그래프 탐색 기능이 필요하지 않다 생각해서 연관관계 삭제.
    @ManyToMany(mappedBy = "products")
    private List<Member> members;

}
