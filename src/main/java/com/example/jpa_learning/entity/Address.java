package com.example.jpa_learning.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

//@Setter   불변 객체를 만들기 위해 setter는 만들지 않는다.
@Getter
@Embeddable     //값 타입을 정의하는 곳에 표시. 기본 생성자가 필수.
public class Address {

    @Column(name = "city")  //매핑할 컬럼 정의 가능.
    private String city;
    private String street;
    private String zipcode;

    protected Address(){}   //JPA에서 기본 생성자는 필수.

    //생성자로 초기 값을 설정한다.
    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}

//불변 객체 사용 예제
//      Address address = member1.getHomeAddress();
//      //회원1의 주소값을 조회해서 새로운 주소 생성.
//      Address newAddress = new Address(address.getCity());
//      member2.setHomeAddress(newAddress);