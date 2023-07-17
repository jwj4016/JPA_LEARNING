package com.example.jpa_learning.entity.nonidentifying;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;

import java.io.Serializable;


//@EmbeddedId 적용 식별자 클래스가 필수 조건.
//  - @Embeddable 필수.
//  - Serializable 인터페이스 구현.
//  - equals & hashCode 구현.
//  - 기본 생성자 필수.
//  - 식별자 클래스는 public.
@EqualsAndHashCode
@Embeddable
public class ParentIdUsingEmbeddedId implements Serializable {
    @Column(name = "PARENT_ID1")
    private String id1;
    @Column(name = "PARENT_ID2")
    private String id2;

    public ParentIdUsingEmbeddedId() {}

    public ParentIdUsingEmbeddedId(String id1, String id2) {
        this.id1 = id1;
        this.id2 = id2;
    }

}
