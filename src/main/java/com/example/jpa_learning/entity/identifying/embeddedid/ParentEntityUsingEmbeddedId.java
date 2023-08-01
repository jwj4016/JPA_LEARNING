package com.example.jpa_learning.entity.identifying.embeddedid;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

//@Entity
public class ParentEntityUsingEmbeddedId {

    @Id @Column(name = "PARENT_ID")
    private String id;

    private String name;
}
