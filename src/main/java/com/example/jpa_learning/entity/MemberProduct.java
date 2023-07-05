package com.example.jpa_learning.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
//회원상품 엔티티는 기본키가 MEMBER_ID와 PRODUCT_ID로 이루어진 복합 기본키다.
//JPA에서 복합키 사용을 위해서는 별도의 식별자 클래스 필요.(@IdClass로 식별자 클래스 지정.)
//복합키를 위한 식별자 클래스의 특징.
//  1. 복합키는 별도의 식별자 클래스로 만들어야한다.
//  2. Serializable을 구현해야한다.
//  3. equals와 hashCode 메소드 구현.
//  4. 기본 생성자 필요.
//  5. 식별자 클래스는 public이어야 한다.
//  6. @IdClass를 사용하는 방법 외에 @EmbeddedId를 사용하는 방법도 있다.
//++식별 관계란??
//  - 부모 테이블의 기본키를 받아 자신의 기본키 + 외래키로 사용하는 것을 식별 관계(Identifying Relationship)라 한다.
@IdClass(MemberProductId.class) //복합 기본키 매핑.
@Getter
@Setter
@Table(schema = "jpa", name = "MEMBER_PRODUCT")
public class MemberProduct {
    //기본키+외래키 한번에 매핑.
    @Id
    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;  //MemberProductId.member와 연결.

    //기본키+외래키 한번에 매핑.
    @Id
    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;    //MemberProductId.product와 연결.

    private int orderAmount;
    private Date orderDate;
}
