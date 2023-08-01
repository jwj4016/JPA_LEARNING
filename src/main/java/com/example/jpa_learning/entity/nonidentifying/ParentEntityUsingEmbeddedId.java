package com.example.jpa_learning.entity.nonidentifying;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "PARENT_ENTITY", schema = "jpa")
//@Entity
public class ParentEntityUsingEmbeddedId {
    @EmbeddedId
    private ParentIdUsingEmbeddedId id;

    private String name;
}
