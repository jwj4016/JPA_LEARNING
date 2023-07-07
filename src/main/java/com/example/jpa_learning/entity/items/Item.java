package com.example.jpa_learning.entity.items;

import jakarta.persistence.*;

@Entity
//조인 전략 매핑
//  상속 매핑은 부모 클래스에 @Inheritance를 사용해야 한다.
//  - 조인 전략의 장점
//      - 테이블이 정규화 된다.
//      - 외래 키 참조 무결성 제약조건을 활용할 수 있다.
//      - 저장공간을 효율적으로 사용.
//  - 조인 전략의 단점
//      - 조회할 때 조인이 많이 사용되므로 성능이 저하될 수 있다.
//      - 조회 쿼리가 복잡하다.
//      - 데이터 등록 시 INSERT SQL 두번 실행.
@Inheritance(strategy = InheritanceType.JOINED)
//단일 테이블 전략 매핑
//  테이블 하나만 사용하는 전략. 조회 시 조인을 하지 않아 일반적으로 가장 빠르다. 자식 엔티티가 매핑한 컬럼은 모두 nullable.
//  - 단일 테이블 전략 장점
//      - 조인이 필요 없어 일반적으로 저회 성능 빠름.
//      - 조회 쿼리 단순.
//  - 단일 테이블 전략 단점
//      - 자식 엔티티가 매핑한 컬럼은 모두 null 허용해야 한다.
//      - 단일 테이블에 모든 것을 저장하므로 테이블이 커질 수 있다. 상황에 따라서는 조회 성능이 오히려 느려질 수도 있다.
//  - 단일 테이블 전략 특징
//      - 구분 컬럼을 꼭 사용해야 한다. @DiscriminatorColumn꼭 설정해야 한다.
//      - @DiscriminatorValue 미지정 시, 기본으로 엔티티 이름 사용.(ex : Movie, Album, Book)
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
//구현 클래스마다 테이블 전략
//  자식 엔티티 마다 테이블 만든다. 자식 테이블 각각에 필요한 컬럼이 모두 존재. 비추천 전략.
//  - 구현 클래스마다 테이블 전략 장점.
//      - 서브 타입을 구분해서 처리할 때 효과적이다.
//      - not null 제약조건을 사용할 수 있다.
//  - 구현 클래스마다 테이블 전략 단점.
//      - 여러 자식 테이블을 함께 조회할 때 성능이 느리다.(SQL에 UNION 사용해야 함.)
//      - 자식 테이블을 통합해서 쿼리하기 어렵다.
//  - 구현 클래스마다 테이블 전략 특징.
//      - 구분 컬럼을 사용하지 않는다.
//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)

//구현 클래스마다 테이블 전략을 사용할 경우 @DiscriminatorColumn 미사용.
//부모 클래스에 구분 컬럼 지정.
@DiscriminatorColumn(name = "DTYPE")
public abstract class Item {
    @Id @GeneratedValue
    @Column (name = "ITEM_ID")
    private Long id;

    private String name;    //이름
    private int price;      //가격
}
