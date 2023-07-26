package com.example.jpa_learning.entity.jointable.manytoone;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

//다대일 (선수 -> 팀)
@Entity
public class TeamEntity {     //팀
    @Id
    @GeneratedValue
    @Column(name = "TEAM_ID")
    private Long id;
    private String name;


    //선수
    @OneToMany(mappedBy = "team")
    private List<PlayerEntity> players = new ArrayList<>();
}
