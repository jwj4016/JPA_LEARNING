package com.example.jpa_learning.nativesql;

import com.example.jpa_learning.entity.Member;
import jakarta.persistence.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigInteger;
import java.util.List;

@DataJpaTest
@ActiveProfiles("local")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class NativeSQLTests {
    @PersistenceUnit
    EntityManagerFactory entityManagerFactory;

    void 엔티티_조회(){
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        //SQL 정의
        String sql = "SELECT ID, AGE, NAME, TEAM_ID "
                   + "FROM MEMBER WHERE AGE > ?";

        Query nativeQuery = entityManager.createNativeQuery(sql, Member.class)
                .setParameter(1, 20);

        List<Member> resultList = nativeQuery.getResultList();
    }

    void 값_조회(){
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        //SQL 정의
        String sql = "SELECT ID, AGE, NAME, TEAM_ID "
                   + "FROM MEMBER WHERE AGE > ?";

        Query nativeQuery = entityManager.createNativeQuery(sql)
                .setParameter(1, 10);

        List<Object[]> resultList = nativeQuery.getResultList();
        for (Object[] row : resultList) {
            System.out.println("id = " + row[0]);
            System.out.println("age = " + row[1]);
            System.out.println("name = " + row[2]);
            System.out.println("team_id = " + row[3]);
        }
    }


    //결과 매핑 정의
    //@Entity
    //@SqlResultSetMapping(name = "memberWithOrderCount"
    //        , entities = {@EntityResult(entityClass = Member.class)}
    //        , columns = {@ColumnResult(name = "ORDER_COUNT")}
    //)
    //public class Member {...}
    void 결과_매핑_사용() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        //SQL 정의
        String sql = "SELECT M.ID, AGE, NAME, TEAM_ID, I.ORDER_cOUNT "
                + "FROM MEMBER M "
                + "LEFT JOIN "
                + "    (SELECT IM.ID, COUNT(*) AS ORDER_COUNT "
                + "     FROM ORDERS O, MEMBER IM "
                + "     WHERE O.MEMBER_ID = IM.ID) I "
                + "ON M.ID = I.ID";

        Query nativeQuery = entityManager.createNativeQuery(sql, "memberWithOrderCount");

        List<Object[]> resultList = nativeQuery.getResultList();
        for (Object[] row : resultList) {
            Member member = (Member) row[0];
            BigInteger orderCount = (BigInteger) row[1];

            System.out.println("member = " + member);
            System.out.println("orderCount = " + orderCount);
        }
    }


    //@SqlResultSetMapping(name = "OrderResults"
    //        , entities = {
    //            @EntityResult(entityClass = com.xxx.xxx.Order.class
    //                , fields = {
    //                    @FieldResult(name = "id", column = "order_id")    //FieldResult를 한 번이라도 사용하면 전체펠드를 @FieldResult로 매핑해야한다.
    //                    , @FieldResult(name = "quantity", column = "order_quantity")
    //                    , @FieldResult(name = "item", column = "order_item")
    //                }
    //            )
    //        }
    //        , columns = {
    //            @ColumnResult(name = "item_name")
    //        }
    //)
    void 표준_명세_예제() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        //SQL 정의
        String sql = "SELECT o.id AS order_id, "
                   + "o.quantity  AS order_quantity, "
                   + "o.item      AS order_item, "
                   + "i.name      AS item_name, "
                   + "FROM Order o, Item i "
                   + "WHERE (order_quantity > 25) AND (order_item = i.id)";

        Query query = entityManager.createNativeQuery(sql, "OrdersResults");
    }
}
