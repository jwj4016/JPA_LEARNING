package com.example.jpa_learning;

import com.example.jpa_learning.entity.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceUnit;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@DataJpaTest
@ActiveProfiles("local")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class EntityManagerTest {
    @PersistenceUnit
    EntityManagerFactory entityManagerFactory;

    @Test
    void test1(){
        //엔티티 매니저는 내부에 데이터소스(db connection) 유지하며 db와 통신.
        //엔티티 매니저를 가상 db라고 생각하면 됨.
        //엔티티 매니저는 스레드간 공유하거나 재사용하면 안된다.
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();         //[트랜잭션] - 시작
            logic(em);          //비지니스 로직 실행
            tx.commit();        //[트랜잭션] - 커밋
        } catch (Exception e) {
            tx.rollback();      //]트랜잭션] - 롤백
        }finally {
            em.clear();
        }
        entityManagerFactory.close();

    }

    //비지니스 로직
    private static void logic(EntityManager entityManager) {
        String id = "jwj4016";
        Member member = new Member();
        member.setId(id);
        member.setUsername("우쨔");
        member.setAge(35);

        //등록
        entityManager.persist(member);

        //수정
        member.setAge(20);

        //한 건 조회
        Member findMember = entityManager.find(Member.class, id);
        System.out.println("findMemebr = " + findMember.getUsername() + ", age = " + findMember.getAge());


        //목록 조회
        List<Member> memberList = entityManager.createQuery("select m from Member m", Member.class).getResultList();
        System.out.println("members.size = " + memberList.size());

        //삭제
        entityManager.remove(member);
    }
}
