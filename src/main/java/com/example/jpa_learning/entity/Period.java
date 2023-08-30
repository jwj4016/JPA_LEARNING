package com.example.jpa_learning.entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import java.util.Date;

@Embeddable     //값 타입을 정의하는 곳에 표시. 기본 생성자가 필수.
public class Period {
    @Temporal(TemporalType.DATE)
    Date startDate;
    @Temporal(TemporalType.DATE)
    Date endDate;

    public boolean isWork(Date date) {
        //값 타입을 위한 메소드 정의 가능.
        return true;
    }
}
