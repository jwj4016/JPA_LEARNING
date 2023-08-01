package com.example.jpa_learning.entity.nonidentifying;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Table(name = "PARENT_ENTITY", schema = "jpa")
//@Entity
//@IdClass 사용 시 필요 조건.
//  - 식별자 클래스의 속성명과 엔티티에서 사용하는 식별자 속성명이 동일해야 한다.
//    (ParentEntity.id1 == ParentId.id1 && ParentEntity.id2 == ParentId.id2)
//  - Serializable 인터페이스 구현.
//  - equals, hashCode 구현.
//  - 기본 생성자가 있어야 함.
//  - 식별자 클래스는 public.
@IdClass(ParentIdUsingIdClass.class)
public class ParentEntityUsingIdClass {
    @Id
    @Column(name = "PARENT_ID1")
    private String id1;     //ParentId.id1과 연결.

    @Id
    @Column(name = "PARENT_ID2")
    private String id2;     //ParentId.id2.와 연결.

    private String name;
}
