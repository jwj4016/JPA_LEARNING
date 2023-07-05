package com.example.jpa_learning.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


//복합키 일 경우 별도의 식별자 클래스 만들어야함.
//Serializable을 구현해햐 한다.
//equals & hashCode 구현.
//기본 생성자 필요.
//식별자 클래스는 public 이어야 한다.
//@IdClass를 사용하는 방법 외에 @EmbeddedId를 사용하는 방법도 있다.
@Setter
@Getter
@EqualsAndHashCode
public class MemberProductId implements Serializable {
    private Long member;      //MemberProduct.member와 연결.
    private String product;     //MemberProduct.product와 연결.

}
