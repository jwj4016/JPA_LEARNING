package com.example.jpa_learning.entity.identifying.embeddedid;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode
@Embeddable
public class ChildId implements Serializable {
    private String parentId;    //@MapsId("parentId")로 매핑

    @Column(name = "CHILD_ID")
    private String id;

}
