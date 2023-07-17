package com.example.jpa_learning.entity.identifying.idclass;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "PARENT_ENTITY", schema = "jpa")
@Entity
public class ParentEntityUsingIdClass {
    @Id
    @Column(name = "PARENT_ID")
    private Long id;
    private String name;
}
