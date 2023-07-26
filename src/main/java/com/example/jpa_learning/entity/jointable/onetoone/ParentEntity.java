package com.example.jpa_learning.entity.jointable.onetoone;

import jakarta.persistence.*;

@Table(name = "PARENT_ONE_TO_ONE_JOIN_TABLE", schema = "jpa")
@Entity
public class ParentEntity {
    @Id @GeneratedValue
    @Column(name = "PARENT_ID")
    private Long id;
    private String name;

    @OneToOne
    @JoinTable(name = "PARENT_CHILD"
            , joinColumns = @JoinColumn(name = "PARENT_ID")         //현재 엔티티를 참조하는 외래 키
            , inverseJoinColumns = @JoinColumn(name = "CHILD_ID"))  //반대방향 엔티티를 참조하는 외래 키
    private ChildEntity child;
}
