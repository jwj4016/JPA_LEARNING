package com.example.jpa_learning.jpql;

public class JpqlPathExpressionTest {
    //상태 필드 경로 : 경로 탐색의 끝.
    //  예시 :
    //      JPQL : SELECT m.username, m.age FROM Member m
    //      SQL  : SELECT m.name, m.age FROM Member m

    //단일 값 연관 경로 : 묵시적으로 내부 조인. 단일 값 연관 경로는 계속 탐색 가능.
    //  예시 :
    //      JPQL : SELECT o.member from Order o
    //      SQL  : SELECT m.* FROM Orders o INNER JOIN Member m on o.member_id = m.id
    //  예시2 :
    //      JPQL : SELECT o.member.team FORM Order o WHERE o.product.name = 'productA' and o.address.city = 'JINJU'
    //      SQL  : SELECT t.*
    //             FROM Orders o
    //             INNER JOIN Member m ON o.member_id = m.id
    //             INNER JOIN Team t ON m.team_id = t.id
    //             INNER JOIN Product p ON o.product_id = p.id
    //             WHERE p.name = 'productA' AND o.city = 'JINJU'

    //컬렉션 값 연관 경로 : 묵시적으로 내부 조인. 경로 탐색의 끝. 단, FROM 절에서 조인을 통해 별칭을 얻으면 별칭으로 탐색 가능.
    //  예시 :
    //      JPQL : SELECT t.members FROM Team t (성공)
    //           : SELECT t.members.username FROM Team t (실패)
    //           : SELECT m.username FROM Team t JOIN t.members m (성공)
}
