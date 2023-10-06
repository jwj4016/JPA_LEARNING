package com.example.jpa_learning.criteria;

import com.example.jpa_learning.entity.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnit;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@DataJpaTest
@ActiveProfiles("local")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CriteriaTest {
    @PersistenceUnit
    EntityManagerFactory entityManagerFactory;

    void startCriteria() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        //JPQL : select m from Member m
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();    //Criteria 쿼리 빌더.

        //Criteria 생성, 반환 타입 지정.
        CriteriaQuery<Member> cq = cb.createQuery(Member.class);

        Root<Member> m = cq.from(Member.class);     //FROM 절
        cq.select(m);   //SELECT 절

        TypedQuery<Member> query = entityManager.createQuery(cq);
        List<Member> members = query.getResultList();
    }

    void addSearchOption() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        //JPQL
        //select m from Member m
        //where m.username = '회원1'
        //order by m.age desc

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        CriteriaQuery<Member> cq = cb.createQuery(Member.class);

        //m은 쿼리 루트. 쿼리 루트는 조회의 시작점이다.
        Root<Member> m = cq.from(Member.class); //FROM 절 생성

        //검색 조건 정의
        Predicate usernameEqual = cb.equal(m.get("username"), "회원1");

        //정렬 조건 정의
        Order ageDesc = cb.desc(m.get("age"));

        //쿼리 생성
        cq.select(m)
                .where(usernameEqual)       //WHERE 절 생성
                .orderBy(ageDesc);          //ORDER BY 절 생성

        List<Member> resultList = entityManager.createQuery(cq).getResultList();
    }

    void searchNumberType() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Member> cq = cb.createQuery(Member.class);

        //JPQL
        //select m from Member m
        //where m.age > 10 order by m.age desc
        Root<Member> m = cq.from(Member.class);

        //타입 정보 필요
        Predicate ageGt = cb.greaterThan(m.<Integer>get("age"), 10);

        cq.select(m)
                .where(ageGt)
                .orderBy(cb.desc(m.get("age")));

    }
}
