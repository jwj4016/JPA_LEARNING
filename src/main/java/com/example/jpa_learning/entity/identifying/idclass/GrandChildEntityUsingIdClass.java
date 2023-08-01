package com.example.jpa_learning.entity.identifying.idclass;

import jakarta.persistence.*;

//@Entity
@IdClass(GrandChildId.class)
public class GrandChildEntityUsingIdClass {
    @Id
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "PARENT_ID")
            , @JoinColumn(name = "CHILD_ID")
    })
    private ChildEntityUsingIdClass child;

    @Id
    @Column(name = "GRANDCHILD_ID")
    private String grandChildId;

    private String name;
}
