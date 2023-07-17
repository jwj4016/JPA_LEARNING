package com.example.jpa_learning.entity.identifying.embeddedid;

import com.example.jpa_learning.entity.identifying.idclass.ChildEntityUsingIdClass;
import jakarta.persistence.*;

@Entity
public class GrandChildEntityUsingEmbeddedId {

    @EmbeddedId
    private GrandChildId id;

    @MapsId("childId")  //GrandChildId.childId 매핑
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "PARENT_ID")
            , @JoinColumn(name = "CHILD_ID")
    })
    private ChildEntityUsingIdClass child;

    private String name;
}
