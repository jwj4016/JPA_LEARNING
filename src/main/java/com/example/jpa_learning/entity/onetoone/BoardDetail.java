package com.example.jpa_learning.entity.onetoone;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

//자식
@Getter
@Setter
//@Entity
public class BoardDetail {

    @Id
    private Long boardId;

    @MapsId     //@MapsId는 @Id를 사용해서 식별자로 지정한 BoardDetail.boardId와 매핑.
    @OneToOne
    @JoinColumn(name="BOARD_ID")
    private Board board;

    private String content;
}
