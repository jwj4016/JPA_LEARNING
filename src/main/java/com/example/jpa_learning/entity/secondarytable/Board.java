package com.example.jpa_learning.entity.secondarytable;

import jakarta.persistence.*;

//@SecondaryTable 사용보다는 테이블당 엔티티 만들어 매핑하는게 좋다. 최적화의 어려움이 있다.
//@Entity
@Table(name = "BOARD")  //BOARD 테이블과 매핑.
//BOARD_DETAIL 테이블과 매핑.
@SecondaryTable(
        name = "BOARD_DETAIL",      //매핑할 다른 테이블 이름.
        pkJoinColumns = @PrimaryKeyJoinColumn(name = "BOARD_DETAIL_ID"))    //매핑할 다른 테이블의 기본키 컬럼 속성.
//@SecondaryTables() 사용 시 더 많은 테이블 매핑 가능.
//@SecondaryTable({
//  @SecondaryTable(name="BOARD_DETAIL"),
//  @SecondaryTable(name="BOARD_FILE")
//})
public class Board {
    @Id @GeneratedValue
    @Column(name = "BOARD_ID")
    private Long id;

    private String title;

    @Column(table = "BOARD_DETAIL")
    private String content;

}
