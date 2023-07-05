package com.example.jpa_learning;

import com.example.jpa_learning.entity.*;
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
public class CorrelationTest {
    @PersistenceUnit
    EntityManagerFactory entityManagerFactory;

    //연관관계가 있는 엔티티 조회하는 방법은 크게 2가지.
    //  1. 객체 그래프 탐색(객체 연관관계를 사용한 조회)
    //  2. 객체지향 쿼리 사용 JPQL
    @Test
    void 연관관계_엔티티_조회_객체_그래프() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Member member = entityManager.find(Member.class, "3");
        Team team = member.getTeam();   //객체 그래프 탐색.
        System.out.println("팀 이름 = " + team.getName());
    }

    @Test
    void 연관관계_엔티티_조회_객체지향_쿼리() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        String jpql = "select m from Member m join m.team t where t.name=:teamName";

        List<Member> resultList = entityManager.createQuery(jpql, Member.class)
                .setParameter("teamName", "팀1")
                .getResultList();

        for (Member member : resultList) {
            System.out.println("[query] member.username = " + member.getUsername());
        }
    }

    @Test
    void 연관관계_수정() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction tx = entityManager.getTransaction();
        tx.begin();

        //새로운 팀2
        Team team2 = new Team();
        team2.setName("팀3");
        //트랜잭션이 없는 상태에서 persist 하면 team이 생성되지 않음.
        entityManager.persist(team2);

        //회원A에 새로운 팀2 설정
        Member member = entityManager.find(Member.class, "3");
        member.setTeam(team2);

        tx.commit();
    }

    @Test
    void 연관관계_제거() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction tx = entityManager.getTransaction();

        tx.begin();

        Member member1 = entityManager.find(Member.class, "3");
        member1.setTeam(null);  //연관관계 제거

        //entityManager.remove(team);       //연관관계를 제거하기 위해서는 기존 연관관계를 먼저 제거하고 삭제해야한다.

        tx.commit();

    }

    @Test
    void 일대다_객체그래프_탐색() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Team team = entityManager.find(Team.class, "2");

        List<Member> members = team.getMembers();   //(팀->회원) 객체 그래프 탐색.

        for (Member member : members) {
            System.out.println("member.username = " + member.getUsername());
        }
    }

    @Test
    void 양방향_연관관계_저장() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction tx = entityManager.getTransaction();
        tx.begin();

        //팀 3 저장
        Team team1 = new Team();
        team1.setName("팀3");
        entityManager.persist(team1);

        //회원1 저장
        Member member1 = new Member();
        member1.setUsername("A");
        member1.setTeam(team1);     //연관관계 설정 member1 -> team1
        entityManager.persist(member1);

        //회원2 저장
        Member member2 = new Member();
        member2.setUsername("B");
        member2.setTeam(team1);     //연관관계 설정 member2 -> team1
        entityManager.persist(member2);

        //아래의 memberList의 size는 0.
        //기대하던 양방향 연관관계가 아니다.
        //따라서 양방향 모두 관계를 설정해야한다. -> team1.getMembers().add(member1), team1.getMembers().add(member2)
        //하지만 이처럼 설정해도 저장 시에는 위의 코드가 사용되지 않는다.
        //List<Member> memberList = team1.getMembers();



        tx.commit();
    }

    @Test
    void 양방향_연관관계_주의점() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction tx = entityManager.getTransaction();
        tx.begin();

        //회원1 저장
        Member member1 = new Member();
        member1.setUsername("회원1");
        entityManager.persist(member1);

        //회원2 저장
        Member member2 = new Member();
        member2.setUsername("회원2");
        entityManager.persist(member2);

        Team team1 = new Team();
        team1.setName("팀5");
        //주인이 아닌곳만 연관관계 설정.
        //테이블의 TEAM_ID 컬럼에 NULL로 저장된다.
        team1.getMembers().add(member1);
        team1.getMembers().add(member2);

        entityManager.persist(team1);

        tx.commit();
    }

    @Test
    void 양방향_리팩토링_전체코드() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction tx = entityManager.getTransaction();
        tx.begin();

        Team team1 = new Team();
        team1.setName("팀1");
        entityManager.persist(team1);

        Member member1 = new Member();
        member1.setUsername("회원1");
        member1.setTeam(team1);     //양방향 연관관계 설정. Member.setTeam() 리팩토링.
        entityManager.persist(member1);

        Member member2 = new Member();
        member2.setUsername("회원2");
        member2.setTeam(team1);     //양방향 연관관계 설정. Member.setTeam() 리팩토링.
        entityManager.persist(member2);


        tx.commit();
    }

    @Test
    void 다대다_저장() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction tx = entityManager.getTransaction();
        tx.begin();

        Product productA = new Product();
        productA.setId("productA");
        productA.setName("상품A");
        entityManager.persist(productA);

        Member member1 = new Member();
        member1.setUsername("회원1");
        member1.getProducts().add(productA);    //연관관계 설정
        entityManager.persist(member1);

        tx.commit();
    }

    @Test
    void 다대다_탐색() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction tx = entityManager.getTransaction();
        tx.begin();

        Member member = entityManager.find(Member.class, 5);
        List<Product> products = member.getProducts();  //객체 그래프 탐색
        for (Product product : products) {
            System.out.println("product.name = " + product.getName());
        }

        tx.commit();
    }

    @Test
    void 다대다_역방향_탐색() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction tx = entityManager.getTransaction();
        tx.begin();

        Product product = entityManager.find(Product.class, "productA");
        List<Member> members = product.getMembers();
        for (Member member : members) {
            System.out.println("member = " + member.getUsername());


        }


        tx.commit();
    }

    @Test
    void 다대다_매핑한계_극복_저장() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction tx = entityManager.getTransaction();
        tx.begin();

        //회원 저장
        Member member1 = new Member();
        member1.setUsername("다대다극복");
        entityManager.persist(member1);

        //상품 저장
        Product productA = new Product();
        productA.setId("productA");
        productA.setName("상품1");
        entityManager.persist(productA);

        //회원상품 저장
        MemberProduct memberProduct = new MemberProduct();
        memberProduct.setMember(member1);       //주문 회원 - 연관관계 설정
        memberProduct.setProduct(productA);     //주문 상품 - 연관관계 설정
        memberProduct.setOrderAmount(2);        //주문 수량
        entityManager.persist(memberProduct);

        tx.commit();
    }

    @Test
    void 다대다_조회() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction tx = entityManager.getTransaction();
        tx.begin();

        //기본키 값 생성
        MemberProductId memberProductId = new MemberProductId();
        memberProductId.setMember(1L);
        memberProductId.setProduct("productA");

        MemberProduct memberProduct = entityManager.find(MemberProduct.class, memberProductId);

        Member member = memberProduct.getMember();
        Product proudct = memberProduct.getProduct();

        tx.commit();
    }


}
