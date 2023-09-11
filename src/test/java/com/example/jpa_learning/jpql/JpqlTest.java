package com.example.jpa_learning.jpql;

import com.example.jpa_learning.entity.Member;
import com.example.jpa_learning.entity.Product;
import jakarta.persistence.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * JPQL에 SELECT, UPDATE, DELETE 사용 가능. INSERT는 없다.
 */
@DataJpaTest
@ActiveProfiles("local")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class JpqlTest {
    @PersistenceUnit
    EntityManagerFactory entityManagerFactory;

    /**
     * SELECT
     *  EX) SELECT m FROM Member AS m where m.username = 'Hello'
     *  - 대소문자 구분 : 엔티티와 속성은 대소문자 구분.
     *  - 엔티티 이름 : JPQL에서 사용한 Member는 클래스 명이 아니라 엔티티 명이다.
     *                엔티티 명은 @Entity(name = "XXX")로 지정 가능. 엔티티 명 미지정 시 클래스명을 기본값으로 사용.
     *  - 별칭 필수
     */

    void usingTypeQuery(){
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        TypedQuery<Member> query = entityManager.createQuery("SELECT m FROM Member m", Member.class);

        List<Member> resultList = query.getResultList();
        for (Member member : resultList) {
            System.out.println("member = " + member);
        }
    }

    void usingQuery() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Query query = entityManager.createQuery("SELECT m.name, m.age FROM Member m");
        List resultList = query.getResultList();

        for (Object o : resultList) {
            //SELECT m.name FROM Member m 이면 결과를 Object로 반환.
            //SELECT m.name, m.age FROM Member m 이면 Object[]를 반환.
            Object[] result = (Object[]) o; //결과가 둘 이상이면 Object[] 반환.
            System.out.println("name = " + result[0]);
            System.out.println("age = " + result[1]);
        }
    }

    void parameterBinding() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        String usernameParam = "User1";

        TypedQuery<Member> query = entityManager.createQuery("SELECT m FROM Member m WHERE m.name = :name", Member.class);

        query.setParameter("name", usernameParam);
        List<Member> resultList = query.getResultList();
    }

    void positionParameter() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        String usernameParam = "User1";
        List<Member> members = entityManager.createQuery("SELECT m FROM Member m WHERE m.username = ?1", Member.class)
                .setParameter(1, usernameParam)
                .getResultList();
    }

    /**
     * 프로젝션 
     */
    @Test
    void projection(){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Query query = entityManager.createQuery("SELECT m.username, m.age FROM Member m");
        List resultList = query.getResultList();

        Iterator iterator = resultList.iterator();
        while (iterator.hasNext()) {
            Object[] row = (Object[]) iterator.next();
            String username = (String) row[0];
            Integer age = (Integer) row[1];
        }

        List<Object[]> improvedResultList = query.getResultList();
        for (Object[] row : improvedResultList) {
            String username = (String) row[0];
            Integer age = (Integer) row[1];
        }

        List<Object[]> resultList2 = entityManager.createQuery("SELECT o.member, o.product, o.orderAmount FROM Orders o")
                .getResultList();
        for (Object[] row : resultList2) {
            Member member = (Member) row[0];
            Product product = (Product) row[1];
            int orderAmount = (Integer) row[2];
        }

        List<Object[]> resultList3 = entityManager.createQuery("SELECT m.username, m.age FROM Member m")
                .getResultList();
        //객체 변환 작업
        List<UserDTO> userDTOS = new ArrayList<>();
        for (Object[] row : resultList3) {
            UserDTO userDTO = new UserDTO((String) row[0], (Integer) row[1]);
            userDTOS.add(userDTO);
        }

        //new 명령어 사용 시 주의 사항
        //1. 패키지 명을 포함한 전체 클래스 명을 입력해야 한다.
        //2. 순서와 타입이 일치하는 생성자 필요.
        TypedQuery<UserDTO> typedQuery = entityManager.createQuery("SELECT new com.example.jpa_learning.UserDTO(m.username, m.age) FROM Member m", UserDTO.class);
        List<UserDTO> resultList4 = typedQuery.getResultList();
    }

    public class UserDTO{
        private String username;
        private int age;

        public UserDTO(String username, int age) {
            this.username = username;
            this.age = age;
        }
    }
}
