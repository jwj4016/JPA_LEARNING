package com.example.jpa_learning.entity;

import jakarta.persistence.ManyToOne;

public class PhoneNumber {
    private String areaCode;
    private String localNumber;
    @ManyToOne
    PhoneServiceProvider provider;  //엔티티 참조
}
