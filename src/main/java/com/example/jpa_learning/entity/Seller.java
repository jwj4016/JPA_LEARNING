package com.example.jpa_learning.entity;

import jakarta.persistence.Entity;

@Entity
public class Seller extends BaseEntity{
    //id & name 상속.
    //private Long id;
    //private String name;

    private String shopName;
}
