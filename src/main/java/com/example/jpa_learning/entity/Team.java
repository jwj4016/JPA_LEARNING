package com.example.jpa_learning.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "TEAM", schema = "jpa")
public class Team {
    //연관관계 매핑 기초
    //  KEY WORD : 방향, 다중성, 연관관계의 주인.
    //      방향 : 단방향 OR 양방향. 회원 -> 팀 OR 팀 -> 회원 둘 중 한 쪽만 참조 하면 단방향. 양쪽 모두 서로 참조하면 양방향.
    //            방향은 객체관계에서만 존재. 테이블은 항상 양방향이다.
    //      다중성 : 다대일(N:1) OR 일대다(1:N) OR 일대일(1:1) OR 다대다(N:M).
    //             회원과 팀의 관계에서 여러 회원은 한팀에 속하므로 회원과 팀은 다대일(N:1) 관계. 팀과 회원은 일대다(1:N) 관계.
    //      연관관계의 주인 : 객체를 양방향 연관관계로 만들면 연관관계의 주인을 정해야 한다.??

    //1. 단방향 연관관계
    //  - 다대일(N:1) 단방향.
    //      - 회원과 팀 존재. 회원은 하나의 팀에만 소속 가능. 회원과 팀은 다대일 관계.
    //      - 객체 연관관계
    //          - 회원 객체는 Member.team 필드로 팀 객체와 연관관계 맺음.
    //          - 회원 객체와 팀 객체는 단방향. 회원은 Member.team 으로 팀을 알 수 있지만. 팀은 멤버를 알 수 없다.
    //      - 테이블 연관관계
    //          - 회원 테이블은 TEAM_ID 외래키로 팀 테이블과 연관관계를 맺음.
    //          - 회원 테이블 & 팀 테이블은 양방향. MEMBER JOIN TEAM, TEAM JOIN MEMBER 둘 다 가능.
    //      - 객체 연관관계와 테이블 연관관계의 가장 큰 차이.
    //          - 참조를 통한 연관관계는 언제나 단방향. 객체간 관계를 양방향으로 하고 싶다면 반대쪽에도 필드 추가 후 참조를 보관해야한다.(연관관계를 하나 더 만들어야 한다.)
    //            이렇게 양쪽에서 참조하는 것을 양방향 연관관계라 한다. 정확히는 서도 다른 단방향 관계 2개.
    //            ex) class A{B b;}, class B{A a;} 이면 양방향 연관관계.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TEAM_ID")
    private Long id;
    private String name;

    //양방향 연관관계 추가
    //mappedBy 속성을 이용해 연관관계의 주인을 지정한다. 즉, mappedBy가 있으면 연관관계의 주인이 아니다.
    //연관관계의 주인은 테이블에 외래키가 있는 곳으로 정해야한다.(Member)
    //연관관계의 주인이 아닌 반대편은 읽기만 가능하고 외래키를 변경하지는 못한다.
    @OneToMany(mappedBy = "team")
    private List<Member> members = new ArrayList<Member>();
}
