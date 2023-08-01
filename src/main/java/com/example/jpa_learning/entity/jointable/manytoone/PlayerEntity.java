package com.example.jpa_learning.entity.jointable.manytoone;

import jakarta.persistence.*;

//@Entity
public class PlayerEntity {      //선수
    @Id @GeneratedValue
    @Column(name = "PLAYER_ID")
    private Long id;
    private String name;

    @ManyToOne(optional = false)
    @JoinTable(name = "TEAM_PLAYER"
            , joinColumns = @JoinColumn(name = "PLAYER_ID")
            , inverseJoinColumns = @JoinColumn(name = "TEAM_ID"))
    private TeamEntity team;        //팀
}
