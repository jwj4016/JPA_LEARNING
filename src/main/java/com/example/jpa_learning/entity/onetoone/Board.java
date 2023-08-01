package com.example.jpa_learning.entity.onetoone;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

//부모
@Setter
@Getter
//@Entity
public class Board {
    @Id @GeneratedValue
    @Column(name = "BOARD_ID")
    private Long id;

    private String title;

    @OneToOne(mappedBy = "board")
    private BoardDetail boardDetail;
}
