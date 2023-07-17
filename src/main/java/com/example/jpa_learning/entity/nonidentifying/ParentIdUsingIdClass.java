package com.example.jpa_learning.entity.nonidentifying;

import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode
public class ParentIdUsingIdClass implements Serializable {
    private String id1;     //Parent.id1 매핑
    private String id2;     //Parent.id2 매핑

    public ParentIdUsingIdClass() {}

    public ParentIdUsingIdClass(String id1, String id2) {
        this.id1 = id1;
        this.id2 = id2;
    }

}
