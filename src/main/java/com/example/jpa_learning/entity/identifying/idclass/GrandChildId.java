package com.example.jpa_learning.entity.identifying.idclass;

import lombok.EqualsAndHashCode;

import java.io.Serializable;

//손자 ID
@EqualsAndHashCode
public class GrandChildId implements Serializable {
    private ChildId child;      //GrandChild.child 매핑
    private String grandChildId;          //GrandChild.grandChildId 매핑
}
