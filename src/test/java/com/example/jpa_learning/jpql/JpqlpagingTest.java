package com.example.jpa_learning.jpql;

import com.example.jpa_learning.entity.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnit;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("local")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class JpqlpagingTest {
    @PersistenceUnit
    EntityManagerFactory entityManagerFactory;

    @Test
    void usingPaging() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        TypedQuery<Member> query = entityManager.createQuery("SELECT m FROM Member m ORDER BY m.username DESC", Member.class);

        //setFirstResult(int startPosition) : 조회 시작 위치(0부터 시작한다.)
        //11번째부터 총 20건의 데이터 조회. 11~30번 조회.
        query.setFirstResult(10);
        query.setMaxResults(20);    //setMaxResults(int maxResult) : 조회할 데이터 수
        query.getResultList();


    }
}
