package com.example.jpa_learning.entity.identifying.embeddedid;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Embeddable
@EqualsAndHashCode
public class GrandChildId implements Serializable {
    private ChildId childId;    //@MapsId("childId")로 매핑

    @Column(name = "GRANDCHILD_ID")
    private String id;
}
