package com.example.jpa_learning.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity //이 클래스가 테이블과 매핑된다고 jpa 알려주는 어노테이션.(엔티티 클래스)
@Table(name="MEMBER", schema = "jpa")   //엔티티 클래스와 매핑할 테이블 정보 noti. 생략 시 클래스 이름을 테이블 이름으로 매핑.
public class Member {
    @Id //테이블의 primary key와 매핑. 식별자 필드라고 함.
    @Column(name = "ID")    //컬럼과 필드를 매핑. 해당 어노테이션 없으면 필드명을 사용해 컬럼명을 매핑함. 대소문자 구분하는 db 일 경우 명시해야함.
    private String id;
    @Column(name = "NAME")
    private String username;
    private Integer age;
}
