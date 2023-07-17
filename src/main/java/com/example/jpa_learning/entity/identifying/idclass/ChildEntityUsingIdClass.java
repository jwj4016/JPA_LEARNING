package com.example.jpa_learning.entity.identifying.idclass;

import jakarta.persistence.*;

@IdClass(ChildId.class)
@Table(name = "CHILD_ENTITY", schema = "jpa")
@Entity
public class ChildEntityUsingIdClass {
    @Id
    @JoinColumn(name = "CHILD_ID")
    private String childId;

    @Id
    @ManyToOne
    @JoinColumn(name = "PARENT_ID")
    private ParentEntityUsingIdClass parent;

    private String name;
}
