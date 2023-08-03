package com.example.jpa_learning;

import com.example.jpa_learning.entity.Member;
import com.example.jpa_learning.entity.Orders;
import com.example.jpa_learning.entity.Team;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceUnit;
import org.hibernate.LazyInitializationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@DataJpaTest
@ActiveProfiles("local")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ProxyTest {
    @PersistenceUnit
    EntityManagerFactory entityManagerFactory;

    @Test
    public void printUserAndTeam() {
        String memberId = "1";
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Member member = entityManager.find(Member.class, memberId);
        Team team = member.getTeam();
        System.out.println("회원 이름 : " + member.getUsername());
        System.out.println("소속팀 : " + team.getName());
    }

    @Test
    public void printUser() {
        String memberId = "1";
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Member member = entityManager.find(Member.class, memberId);
        System.out.println("회원 이름 : " + member.getUsername());
    }

    @Test
    public void proxyInit(){
        String memberId = "1";
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        //Member 프록시를 반환한다.
        Member member = entityManager.getReference(Member.class, memberId);
        member.getUsername();
    }

    //프록시 특징.
    // - 프록시 객체는 처음 사용시 한번 초기화된다.
    //   프록시 객체가 실제 사용될 때, DB 조회해 실제 엔티티 객체를 생성한다. 이것을 프록시 객체의 초기화라고 한다.
    // - 프록시 객체 초기화 되면, 프록시 객체를 통해 실제 엔티티에 접근 가능.
    // - 프록시 객체는 원본 엔티티를 상속 받는다. 따라서 타입 체크에 주의해야 한다.
    // - 영속성 컨텍스트에 엔티티가 이미 있을 경우 getReferecne()호출 시 프록시가 아닌 실제 엔티티 반환한다.
    // - 초기화는 영속성 컨텍스를 통해 이루어진다. 따라서 준영속 상태의 프록시 초기화 시 문제 발생.
    //   (샘플코드)
    //   Member member = em.getReference(Member.class, "id1");  //MemberProxy 반환.
    //   tx.commit();
    //   em.close();                                            //영속성 컨텍스트 종료.
    //   member.getName();                                      //준영속 상태 초기화 시도.
    //                                                          //org.hibernate.LazyInitializationException 예외 발생.
    @Test
    void proxyInitError() {
        String memberId = "1";
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        EntityTransaction tx = entityManager.getTransaction();
        tx.begin();
        Member member = entityManager.getReference(Member.class, "1");
        tx.commit();
        entityManager.close();
        Assertions.assertThrows(LazyInitializationException.class, ()->{
            System.out.println(member.getUsername());
        });
    }

    //프록시 클래스 예상 코드.
    //class MemberProxy extends Member {
    //    Member target = null;   //실제 엔티티 참조.
    //
    //    public String getName() {
    //        if (target == null) {
    //            //초기화 요청.
    //            //DB 조회.
    //            //실제 엔티티 생성 및 보관.
    //            this.target = ...;
    //        }
    //        return target.getUsername();
    //    }
    //}

    @Test
    void collectionWrapper() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Member member = entityManager.find(Member.class, "1");
        List<Orders> orders = member.getOrders();
        System.out.println("orders = " + orders.getClass().getName());

    }

    @Test
    void saveNoCascade(){
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        //부모 저장
        Team team = new Team();
        entityManager.persist(team);

        //1번 자식 저장
        Member member1 = new Member();
        member1.setTeam(team);              //자식 -> 부모 연관관계 설정
        team.getMembers().add(member1);     //부모 -> 자식
        entityManager.persist(member1);

        //2번 자식 저장
        Member member2 = new Member();
        member2.setTeam(team);              //자식 -> 부모 연관관계 설정
        team.getMembers().add(member2);     //부모 -> 자식
        entityManager.persist(member2);
    }

    @Test
    void saveWithCascade() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Member member1 = new Member();
        Member member2 = new Member();

        Team team1 = new Team();
        member1.setTeam(team1);     //연관관계 추가
        member2.setTeam(team1);     //연관관계 추가

        team1.getMembers().add(member1);
        team1.getMembers().add(member2);

        entityManager.persist(team1);
    }
}
