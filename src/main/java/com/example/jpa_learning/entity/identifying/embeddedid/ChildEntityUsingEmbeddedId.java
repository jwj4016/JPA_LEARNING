package com.example.jpa_learning.entity.identifying.embeddedid;

import jakarta.persistence.*;

//@Entity
public class ChildEntityUsingEmbeddedId {

    @EmbeddedId
    private ChildId id;

    //@MapsId는 외래키와 매핑한 연관관계를 기본키에도 매핑하겠다는 뜻.
    //@MapsId의 속성 값은 @EmbeddedId를 사용한 식별자 클래스의 기본키 필드를 지정한다.
    @MapsId("parentId")     //ChildId.parentId 매핑
    @ManyToOne
    @JoinColumn(name = "PARENT_ID")
    public ParentEntityUsingEmbeddedId parent;

    private String name;
}
