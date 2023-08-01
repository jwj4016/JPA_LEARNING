package com.example.jpa_learning.entity.jointable.onetoone;

import jakarta.persistence.*;

//@Entity
public class ChildEntity {
    @Id @GeneratedValue
    @Column(name = "CHILD_ID")
    private Long id;
    private String name;

    //양방향 매핑을 위한 코드.
    @OneToOne(mappedBy = "child")
    private ParentEntity parent;
}
