package com.example.jpa_learning.entity.items;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
//엔티티 저장 시 구분 컬럼에 입력할 값을 지정. 구분 컬럼인 DTYPE에 M이 저장.
@DiscriminatorValue("M")
public class Movie extends Item{
    private String director;        //감독
    private String actor;           //배우
}
