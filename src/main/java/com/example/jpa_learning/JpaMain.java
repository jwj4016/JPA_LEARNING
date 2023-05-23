//package com.example.jpa_learning;
//
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.EntityManagerFactory;
//import jakarta.persistence.EntityTransaction;
//import jakarta.persistence.Persistence;
//
//public class JpaMain {
//    public static void main(String[] args) {
//        //[엔티티 매니저 팩토리] - 생성
//        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");
//        //[엔티티 매니저] - 생성
//        EntityManager em = emf.createEntityManager();
//        //[트랜잭션] - 획득
//        EntityTransaction tx = em.getTransaction();
//
//        try {
//            tx.begin();         //[트랜잭션] - 시작
//            logic(em);          //비지니스 로직 실행
//            tx.commit();        //[트랜잭션] - 커밋
//        } catch (Exception e) {
//            tx.rollback();      //]트랜잭션] - 롤백
//        }finally {
//            em.clear();
//        }
//        emf.close();
//    }
//
//    //비지니스 로직
//    private static void logic(EntityManager entityManager) {
//
//    }
//}
//