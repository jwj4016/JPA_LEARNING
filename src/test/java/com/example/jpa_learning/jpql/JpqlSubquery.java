package com.example.jpa_learning.jpql;

public class JpqlSubquery {
    //JPQL도 SQL 처럼 서브쿼리 지원한다.
    //서브쿼리를 WHERE, HAVING 절에서만 사용 가능하다. SELECT, FROM 절에서는 서브쿼리 사용 불가.
    //하이버네이트 HQL은 SELECT 절의 서브쿼리도 허용.
    //  예시
    //      - 나이가 평균보다 많은 회원
    //        SELECT m FROM Member m WHERE m.age > (SELECT AVG(m2.age) FROM Member m2)
    //      - 한 건이라도 주문한 고객
    //        SELECT m FROM Member m WHERE (SELECT COUNT(o) FROM Order o WHERE m = o.member) > 0
    //        SELECT m FROM Member m WHERE m.orders.size > 0 (위와 동일한 SQL이 실행된다.)
    //서브 쿼리 함수
    //  - EXISTS
    //      - 문법 : [NOT] EXISTS (subquery)
    //      - 설명 : 서브쿼리에 결과가 존재하면 참. NOT은 반대.
    //      - 예시 : 팀A 소속인 회원
    //            : SELECT m FROM Member m WHERE EXISTS (SELECT t FROM m.team t WHERE t.name = '팀A')
    //  - {ALL | ANY | SOME}
    //      - 문법 : {ALL | ANY | SOME} (subquery)
    //      - 설명 : 비교 연산자와 같이 사용한다. { = | > | >= | < | <= | <> }
    //          - ALL : 조건 모두 만족 시 참.
    //          - ANY, SOME : 둘은 같은 의미. 하나라도 만족하면 참.
    //      - 예시 : 전체 상품 각각의 재고보다 주문량이 많은 주문들
    //            : SELECT o FROM Order o WHERE o.orderAmount > ALL (SELECT p.stockAmount FROM Product p)
    //      - 예시 : 어떤 팀이든 팀에 소속된 회원
    //            : SELECT m FROM Member m WHERE m.team = ANY (SELECT t FROM Team t)
    //  - IN
    //      - 문법 : [NOT] IN (subquery)
    //      - 설명 : 서브쿼리의 결과 중 하나라도 같은 것이 있으면 참. IN은 서브쿼리가 아닌 곳에서도 사용.
    //      - 예시 : 20세 이상을 보유한 팀.
    //            : SELECT t FROM Team t WHERE t IN (SELECT t2 FROM Team t2 JOIN t2.members m2 WHERE m2.age >= 20)


}