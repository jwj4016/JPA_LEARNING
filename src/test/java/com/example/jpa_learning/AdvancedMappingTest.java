package com.example.jpa_learning;

import com.example.jpa_learning.entity.nonidentifying.ParentEntityUsingEmbeddedId;
import com.example.jpa_learning.entity.nonidentifying.ParentEntityUsingIdClass;
import com.example.jpa_learning.entity.nonidentifying.ParentIdUsingEmbeddedId;
import com.example.jpa_learning.entity.nonidentifying.ParentIdUsingIdClass;
import com.example.jpa_learning.entity.onetoone.Board;
import com.example.jpa_learning.entity.onetoone.BoardDetail;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceUnit;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
//식별, 비식별 관계의 장단점.
//  - 식별 관계 단점
//      1. 식별 관계는 부모 테이블의 기본키를 자식 테이블로 전파한다. 따라서 자식 테이블의 기본키 컬럼이 점점 늘어난다. 조인할 때 SQL이 복잡해지고, 기본키 인덱스가 불필요하게 커질 수 있다.
//      2. 식별 관계는 2개 이상의 컬럼을 합해서 복합 기본키를 만들어야 하는 경우가 많다.
//      3. 식별 관계 사용 시 기본키로 비니지스 의미가 있는 자연키 컬럼을 조합하는 경우가 많다.(비지니스 요구사항은 시간이 지나면서 변한다. 따라서 자식까지 전파된 자연키 컬럼을 변경하기 어렵다.)
//      4. 식별 관계는 부모 테이블의 기본키를 자식 테이브르이 기본키로 사용하므로 테이블 구조가 유연하지 못함.
//      5. 식별 관계는 일대일 관계를 제외하고 2개 이상의 컬럼을 묶은 복합 기본키 사용. JPA에서 복합 키는 별도의 복합 키 클래스 필요.
//  - 식별 관계의 장점

@DataJpaTest
@ActiveProfiles("local")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AdvancedMappingTest {
    @PersistenceUnit
    EntityManagerFactory entityManagerFactory;

    void save_using_idClass(){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction tx = entityManager.getTransaction();
        tx.begin();

        ParentEntityUsingIdClass parent = new ParentEntityUsingIdClass();
        parent.setId1("myId1");     //식별자
        parent.setId2("myId2");     //식별자
        parent.setName("parentName");

        entityManager.persist(parent);

        tx.commit();

    }

    @Test
    void find_using_idClass() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction tx = entityManager.getTransaction();
        tx.begin();

        ParentIdUsingIdClass parentIdUsingIdClass = new ParentIdUsingIdClass("myId1", "myId2");
        ParentEntityUsingIdClass parent = entityManager.find(ParentEntityUsingIdClass.class, parentIdUsingIdClass);

        tx.commit();
    }

    @Test
    void save_using_embeddedId() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction tx = entityManager.getTransaction();
        tx.begin();

        ParentEntityUsingEmbeddedId parent = new ParentEntityUsingEmbeddedId();
        ParentIdUsingEmbeddedId parentId = new ParentIdUsingEmbeddedId("myId1","myId2");
        parent.setId(parentId);
        parent.setName("parentName");

        entityManager.persist(parent);

        tx.commit();
    }

    @Test
    void find_using_embeddedId() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction tx = entityManager.getTransaction();
        tx.begin();

        ParentIdUsingEmbeddedId parentId = new ParentIdUsingEmbeddedId("myId1","myId2");
        ParentEntityUsingEmbeddedId parent = entityManager.find(ParentEntityUsingEmbeddedId.class, parentId);

        tx.commit();
    }

    @Test
    void save_using_onetoone_identifying(){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction tx = entityManager.getTransaction();
        tx.begin();

        Board board = new Board();
        board.setTitle("제목");
        entityManager.persist(board);

        BoardDetail boardDetail = new BoardDetail();
        boardDetail.setContent("내용");
        boardDetail.setBoard(board);
        entityManager.persist(boardDetail);

        tx.commit();
    }
}
