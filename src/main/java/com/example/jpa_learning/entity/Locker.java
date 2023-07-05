package com.example.jpa_learning.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(schema = "jpa", name = "LOCKER")
public class Locker {
    @Id @GeneratedValue
    @Column(name = "LOCKER_ID")
    private Long id;

    private String name;

    @OneToOne(mappedBy = "locker")
    //@OneToOne @JoinColumn(name = "MEMBER_ID")     //대상테이블에 외래키 있을 경우.
    private Member member;
}
