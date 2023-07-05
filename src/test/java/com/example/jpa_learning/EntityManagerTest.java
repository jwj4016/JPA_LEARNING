package com.example.jpa_learning;

import com.example.jpa_learning.entity.Member;
import com.example.jpa_learning.entity.Team;
import jakarta.persistence.*;
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
    @Test
    void 엔티티를_영속성_컨텍스트에_등록하는_테스트() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        //entityManager.setFlushMode(FlushModeType.AUTO);   //커밋 or 쿼리 실행(JPQL 등) 시 플러시.(default)
        //entityManager.setFlushMode(FlushModeType.COMMIT); //커밋할 때만 플러시.
        //참고로 플러시는 영속성 컨텍스트에 보관된 엔티티를 지우는 것이 아니다.
        EntityTransaction transaction = entityManager.getTransaction();
        //엔티티 매니저는 데이터 변경 시 트랜잭션을 시작해야 한다.
        transaction.begin();    //[트랜잭션] 시작

        Team team1 = new Team();
        team1.setName("팀1");

        entityManager.persist(team1);

        Member memberA = new Member();
//        memberA.setId("memberA");
        memberA.setUsername("A");
        memberA.setAge(1);
        memberA.setTeam(team1);



        Member memberB = new Member();
//        memberB.setId("memberB");
        memberB.setUsername("B");
        memberB.setAge(32);
        memberB.setTeam(team1);

        entityManager.persist(memberA);
        entityManager.persist(memberB);
        //여기까지는 INSERT SQL을 DB에 던지지 않는다.

        //커밋하는 순간 데이터베이스에 INSERT SQL 보냄.
        transaction.commit();   //[트랜잭션] 커밋
    }

    @Test
    void testDetached() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        //준영속 상태
        //entityManager.detach();
        entityManager.clear();
        entityManager.close();
    }

    //비지니스 로직
    private static void logic(EntityManager entityManager) {
        String id = "jwj4016";
        Member member = new Member();
//        member.setId(id);
        member.setUsername("우쨔");
        member.setAge(35);

        //등록
        //1차 캐시에 저장된다.
        entityManager.persist(member);

        //수정
        member.setAge(20);

        //한 건 조회
        //1차 캐시에서 조회.
        Member findMember = entityManager.find(Member.class, id);
        System.out.println("findMemebr = " + findMember.getUsername() + ", age = " + findMember.getAge());


        //목록 조회
        List<Member> memberList = entityManager.createQuery("select m from Member m", Member.class).getResultList();
        System.out.println("members.size = " + memberList.size());

        //삭제
        //영속성 컨텍스트에서도 제거된다.
        //이렇게 삭제된 엔티티의 경우 재사용하지 않고 가비지 컬렉션의 대상이 되도록 놔두기.
        entityManager.remove(member);
    }

    @Test
    void mergeTest() {
        Member member = createMember("memberA", "회원1");

        member.setUsername("회원명변경");

        mergeMember(member);

//        EntityManager entityManager = entityManagerFactory.createEntityManager();
//        EntityTransaction transaction = entityManager.getTransaction();
    }

    private Member createMember(String id, String username) {
        //영속성 컨텍스트1 시작
        EntityManager em1 = entityManagerFactory.createEntityManager();
        //동일 트랜잭션 내에서만 1차 캐시가 존재한다.
        EntityTransaction tx1 = em1.getTransaction();
        tx1.begin();

        Member member = new Member();
//        member.setId(id);
        member.setUsername(username);
        member.setAge(10);

        em1.persist(member);
        tx1.commit();

        em1.close();    //영속성 컨텍스트1 종료, member 엔티티는 준영속 상태가 됨.
        //영속성 컨텍스트1 종료.

        //준영속 상태의 member entity 반환.
        // 수정사항이 생겨도 db에 반영 불가.
        return member;
    }

    private void mergeMember(Member member) {
        //영속성 컨텍스트2 시작
        EntityManager em2 = entityManagerFactory.createEntityManager();
        EntityTransaction tx2 = em2.getTransaction();

        tx2.begin();
        //준영속 상태를 다시 영속 상태로 변경.
        //준영속이 아닌 비영속 엔티티도 영속 상태로 변환가능.
        //Member newMember = new Member();
        //Member mergeMember = em.merge(newMember);
        Member mergeMember = em2.merge(member);
        tx2.commit();

        //준영속 상태
        System.out.println("member = " + member.getUsername());

        //영속 상태
        System.out.println("mergeMember = " + mergeMember.getUsername());

        System.out.println("em2 contains member = " + em2.contains(member));

        System.out.println("em2 contains mergeMember = " + em2.contains(mergeMember));

        em2.close();
        //영속성 컨텍스트2 종료
    }

}