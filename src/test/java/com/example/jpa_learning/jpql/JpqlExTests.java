package com.example.jpa_learning.jpql;

import com.example.jpa_learning.entity.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnit;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@DataJpaTest
@ActiveProfiles("local")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class JpqlExTests {
    @PersistenceUnit
    EntityManagerFactory entityManagerFactory;

    void usingJpql(){
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        String jpql = "select m from Member as m where m.name = 'kim'";
        List<Member> resultList = entityManager.createQuery(jpql, Member.class).getResultList();
    }

    void usingCriteria() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        //Criteria 사용 준비
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Member> query = cb.createQuery(Member.class);

        //루트 클래스(조회를 시작할 클래스)
        Root<Member> m = query.from(Member.class);

        //쿼리 생성
        //m.get("name")에서 필드 명이 문자. 코드로 작성하고 싶으면 메타 모델 사용해야 한다.
        //메타 모델 사용 전 -> 사용 후
        //m.get("name") -> m.get(Member_.name)
        CriteriaQuery<Member> cq = query.select(m).where(cb.equal(m.get("name"), "kim"));
        List<Member> resultList = entityManager.createQuery(cq).getResultList();
    }

    void usingQueryDSL(){
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        //준비
        JPAQuery query = new JPAQuery(entityManager);
        QMember member = QMember.member;

        //쿼리, 겨로가 조회
        List<Member> members = query.from(member)
                .where(member.name.eq("kim"))
                .list(member);
    }

    void usingNativeSQL() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        String sql = "SELECT ID, AGE, TEAM_ID, NAME FROM MEMBER WHERE NAME = 'kim'";
        List<Member> resultList = entityManager.createNativeQuery(sql, Member.class).getResultList();
    }

    //JPA는 JDBC 커넥션을 획득하는 api 제공하지 않는다. 따라서 jpa 구현체가 제공하는 방법 사용해야 함.
    void usingJdbc() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Session session = entityManager.unwrap(Session.class);
        session.doWork(new Work(){

            @Override
            public void execute(Connection connection) throws SQLException {
                //work...
            }
        });
    }
}
