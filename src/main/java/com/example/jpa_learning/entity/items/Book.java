package com.example.jpa_learning.entity.items;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;

@Entity
@DiscriminatorValue("B")
//기본값으로 자식 테이블은 부모 테이블의 ID 컬럼명을 그대로 사용.
//자식 테이블의 기본키 컬럼명을 변경하고 싶다면 @PrimaryKeyJoinColun을 사용.
//기본키 컬럼명을 ITEM_ID -> BOOK_ID 로 변경.
@PrimaryKeyJoinColumn(name = "BOOK_ID") //ID 재정의
public class Book extends Item {
    private String author;      //작가
    private String isbn;        //ISBN
}