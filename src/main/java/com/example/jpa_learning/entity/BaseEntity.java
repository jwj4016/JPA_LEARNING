package com.example.jpa_learning.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

//@MappedSuperclass
//  - @Entity는 실제 테이블과 매핑되지만, @MappedSuperClass는 실제 테이블과 매핑되지 않는다.
//  - 단순히 매핑 정보를 상속할 목적으로만 사용.
//  - @MappedSuperclass의 특징.
//      - 테이블과 매핑되지 않고, 자식 클래스에 엔티티의 매핑 정보를 상속하기 위해 사용.
//      - @MappedSuperclass로 지정한 클래스는 엔티티가 아니므로 em.find()나 JPQL에 서 사용 불가능.
//      - 이 클래스를 직접 생성해서 사용할 일은 거의 없다. 추상 클래스로 만드는 것 권장.
//      - 단순히 엔티티가 공통으로 사용하는 매핑 정보 모아주는 역할.
@MappedSuperclass
public abstract class BaseEntity {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
}
