package com.example.jpa_learning.entity.jointable.onetomany;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class ParentEntity {
    @Id
    @GeneratedValue
    @Column(name= "PARENT_ID")
    private Long id;
    private String name;

    @OneToMany
    @JoinTable(name = "PARENT_CHILD"
            , joinColumns = @JoinColumn(name = "PARENT_ID")
            , inverseJoinColumns = @JoinColumn(name = "CHILD_ID"))
    private List<ChildEntity> child = new ArrayList<>();
}
