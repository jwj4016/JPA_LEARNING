package com.example.jpa_learning.jpql;

import com.example.jpa_learning.entity.Member;
import com.example.jpa_learning.entity.Team;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnit;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@DataJpaTest
@ActiveProfiles("local")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class JpqlJoinTest {
    @PersistenceUnit
    EntityManagerFactory entityManagerFactory;

    @Test
    void usingInnerJoin() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        String teamName = "팀A";
        //JPQL 조인은 연관 필드를 사용한다. m.team이 연관 필드!!
        //FROM Member m JOIN Team t 처럼 사용하면 에러 발생.
        String query = "SELECT m FROM Member m INNER JOIN m.team t "
                + "WHERE t.name = :teamName";

        List<Member> members = entityManager.createQuery(query, Member.class)
                .setParameter("teamName", teamName)
                .getResultList();
    }

    @Test
    void usingOuterJoin() {
        String query = "SELECT m FROM Member m LEFT JOIN m.team t";
    }

    void usingCollectionJoin() {
        String query = "SELECT t, m FROM Team t LEFT JOIN t.members m";
    }

    void usingThetaJoin() {
        String query = "SELECT count(m) FROM Member m, Team t"
                + "WHERE m.username = t.name";
    }

    void usingJoinOn() {
        //SELECT m.*, t.* FROM Member m LEFT JOIN Team t ON m.TEAM_ID = t.id AND t.name = 'A'
        String query = "SELECT m, t FROM Member m LEFT JOIN m.team t on t.name = 'A'";
    }

    void UsingEntityFetchJoin() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        //SELECT M.*, T.* FROM MEMBER M INNER JOIN TEAM T ON M.TEAM_ID = T.ID
        String query = "SELECT m FROM Member m JOIN FETCH m.team";

        List<Member> members = entityManager.createQuery(query, Member.class).getResultList();

        for (Member member : members) {
            //페치 조인으로 회원과 팀을 함께 조회해서 지연 로딩 발생 안 함.
            System.out.println("username = " + member.getUsername() + ", " + "teamname = " + member.getTeam().getName());
        }
    }

    //FETCH JOIN VS JOIN
    //  1. 내부조인 JPQL        : SELECT t FROM Team t JOIN t.members m WHERE t.name = '팀A'
    //     실행된 SQL          : SELECT T.* FROM TEAM T INNER JOIN MEMBER M ON T.ID = M.TEAM_ID WHERE T.NAME = '팀A'
    //  2. 컬렉션 페치 조인 JPQL : SELECT t FROM Team t JOIN FETCH t.members WHERE t.name = '팀A'
    //     실행된 SQL          : SELECT T.*, M.* FROM TEAM T INNER JOIN MEMBER M ON T.ID = M.TEAM_ID WHERE T.NAME = '팀A'
    //  - 페치 조인의 특징과 한계
    //      - 특징
    //          - 페치 조인 사용 시 SQL 한번으로 연관된 엔티티 함께 조회 가능.(SQL 호출 횟수 줄여 성능 최적화 가능.)
    //          - 페치 조인은 글로벌 로딩 전략보다 우선.(@OneToMany(fetch = FetchType.LAZY) //글로벌 로딩 전략)
    //          - 따라서 최적화를 위해서는 글로벌 로딩 전략은 지연 로딩 사용하고, 최적화 필요 시 페치 조인 적용하면 효과적.
    //      - 한계
    //          - 페치 조인에는 별칭 줄 수 없다. 따라서 SELECT, WHERE 절, 서브 쿼리에 페치 조인 대상 사용 불가.
    //          - JPA 표준에서는 페치 조인 지원하지 않지만, 하이버네이트는 페치 조인 별칭 지원함. 하지만 무결성 깨질 가능성이 있음.(2차 캐시와 동시에 사용 시 위험.)
    //          - 둘 이상의 컬렉션을 페치 불가.
    //          - 컬렉션 페치 조인 시 페이징 API 사용 불가.
    void UsingCollectionFetchJoin() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        //SELECT T.*, M.* FROM TEAM T INNER JOIN MEMBER M ON T.ID = M.TEAM_ID WHERE T.NAME = '팀A'
        String query = "SELECT t FROM Team t JOIN FETCH t.members WHERE t.name = '팀A'";
        List<Team> teams = entityManager.createQuery(query, Team.class).getResultList();


        //출력 결과
        // teamname = 팀A, team = Team@0x100
        // ->username = 회원1, member = Member@0x200
        // ->username = 회원2, member = Member@0x300
        // teamname = 팀A, team = Team@0x100
        // ->username = 회원1, member = Member@0x200
        // ->username = 회원2, member = Member@0x300
        for (Team team : teams) {
            System.out.println("teamname = " + team.getName() + ", team = " + team);

            for (Member member : team.getMembers()) {
                //페치 조인으로 팀과 회원을 함께 조회해서 지연 로딩 발생 안 함.
                System.out.println("->username = " + member.getUsername() + ", member = " + member);
            }
        }
    }

    void UsingDistinctFetchJoin() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        //SELECT T.*, M.* FROM TEAM T INNER JOIN MEMBER M ON T.ID = M.TEAM_ID WHERE T.NAME = '팀A'
        String query = "SELECT distinct t FROM Team t JOIN FETCH t.members WHERE t.name = '팀A'";
        List<Team> teams = entityManager.createQuery(query, Team.class).getResultList();


        //출력 결과
        // teamname = 팀A, team = Team@0x100
        // ->username = 회원1, member = Member@0x200
        // ->username = 회원2, member = Member@0x300
        for (Team team : teams) {
            System.out.println("teamname = " + team.getName() + ", team = " + team);

            for (Member member : team.getMembers()) {
                //페치 조인으로 팀과 회원을 함께 조회해서 지연 로딩 발생 안 함.
                System.out.println("->username = " + member.getUsername() + ", member = " + member);
            }
        }
    }

}
