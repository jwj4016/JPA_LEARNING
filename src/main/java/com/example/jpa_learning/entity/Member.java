package com.example.jpa_learning.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

@Getter
@Setter
@Entity //이 클래스가 테이블과 매핑된다고 jpa 알려주는 어노테이션.(엔티티 클래스)
//@DynamicUpdate 필드가 너무 많거나, 저장되는 내용이 너무 클 경우에 수정된 데이터만 사용해서 동적으로 UPDATE SQL 생성.
//변경되는 필드에 따라 쿼리가 매번 변경되기 때문에 캐시 적중률이 낮아질 수도 있다.
//일반적으로 컬럼이 30개 이상일 경우 동적 수동 쿼리가 더 빨라진다고 함.
//하지만 본인의 환경에서 테스트를 시행하기.
@Table(name="MEMBER", schema = "jpa")   //엔티티 클래스와 매핑할 테이블 정보 noti. 생략 시 클래스 이름을 테이블 이름으로 매핑.
public class Member {
    @Id //테이블의 primary key와 매핑. 식별자 필드라고 함.
    @Column(name = "ID")    //컬럼과 필드를 매핑. 해당 어노테이션 없으면 필드명을 사용해 컬럼명을 매핑함. 대소문자 구분하는 db 일 경우 명시해야함.
    private String id;
    @Column(name = "NAME")
    private String username;
    private Integer age;
}
