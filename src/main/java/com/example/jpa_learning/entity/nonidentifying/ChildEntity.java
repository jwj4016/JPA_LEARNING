package com.example.jpa_learning.entity.nonidentifying;

import jakarta.persistence.*;

//@Entity
public class ChildEntity {
    @Id
    private String id;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "PARENT_ID1", referencedColumnName = "PARENT_ID1")
            , @JoinColumn(name = "PARENT_ID2", referencedColumnName = "PARENT_ID2")
    })
    private ParentEntityUsingIdClass parent;
}
