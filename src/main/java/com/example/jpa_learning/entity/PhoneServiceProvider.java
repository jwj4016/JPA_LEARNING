package com.example.jpa_learning.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class PhoneServiceProvider {
    @Id
    String name;
}
