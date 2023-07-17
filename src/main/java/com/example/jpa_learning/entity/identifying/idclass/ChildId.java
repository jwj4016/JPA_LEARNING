package com.example.jpa_learning.entity.identifying.idclass;

import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode
public class ChildId implements Serializable {
    private String parent;      //Child.parent 매핑.
    private String childId;     //Child.childId 매핑.
}
