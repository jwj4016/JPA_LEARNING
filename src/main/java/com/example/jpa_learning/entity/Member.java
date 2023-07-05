package com.example.jpa_learning.entity;

import com.example.jpa_learning.enums.RoleType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

//'김영한'님의 '자바 ORM 표준 JPA 프로그래밍' 정리.
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity //이 클래스가 테이블과 매핑된다고 jpa 알려주는 어노테이션.(엔티티 클래스)
//@Entity(name = "name")
//  - @Entity는 속성으로 name을 가진다. jpa서 사용할 entity 이름을 지정하며, default 값은 클래스 이름이다.
//  - 다른 패키지에 이름이 동일한 엔티티 클래스가 있을 경우, 이름을 지정해서 충돌이 발생하지 않도록 해야 한다.
//  - @Entity가 적용될 클래스는 기본생성자가 필수로 필요하다.
//@DynamicUpdate
//  - 필드가 너무 많거나, 저장되는 내용이 너무 클 경우에 수정된 데이터만 사용해서 동적으로 UPDATE SQL 생성.
//  - 변경되는 필드에 따라 쿼리가 매번 변경되기 때문에 캐시 적중률이 낮아질 수도 있다.
//  - 일반적으로 컬럼이 30개 이상일 경우 동적 수동 쿼리가 더 빨라진다고 함.
//  - 하지만 본인의 환경에서 테스트를 시행하기.
@Table(name = "MEMBER", schema = "jpa")   //엔티티 클래스와 매핑할 테이블 정보 noti. 생략 시 클래스 이름을 테이블 이름으로 매핑.
//@Table
//  - 엔티티와 매핑할 테이블 지정. 생략 시 엔티티 이름을 테이블 이름으로 사용.
//  - 속성
//      - name : 매핑할 테이블 이름.
//      - catalog : catalog 기능이 있는 db에서 catalog를 매핑한다.
//      - schema : schema 기능이 있는 db에서 schema를 매핑한다.
//                 DDL 생성 시에 유니크 제약조건을 만든다. 2개 이상의 복합 유니크 제약조건도 만들 수 있음.
public class Member {
    @Id //테이블의 primary key와 매핑. 식별자 필드라고 함.
    @Column(name = "ID")    //컬럼과 필드를 매핑. 해당 어노테이션 없으면 필드명을 사용해 컬럼명을 매핑함. 대소문자 구분하는 db 일 경우 명시해야함.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //기본키 할당 방식
    //  1. 직접할당 : 기본 키를 앱에서 직접 할당.
    //  2. 자동생성 : 대리 키 사용.
    //      - IDENTITY : 기본키 생성을 데이터베이스에 위임.
    //          - MySQL, PostgreSQL, SQL Server, DB2 등에서 주로 사용.
    //          - MySQL의 경우 AUTO_INCREMENT 기능을 사용해 기본키 자동 생성.
    //          - 데이터를 DB에 INSERT한 후에 기본키 조회 가능.
    //          - 엔티티 영속 상태가 되려면 반드시 식별자가 필요하다. 따라서 em.persist() 호출 즉시 DB에서 insert 수행. 트랜잭션 지원하는 쓰기 지연 동작하지 않음.
    //      - SEQUENCE : 데이터베이스 시퀀스를 사용해 기본키를 할당한다.
    //          - 오라클, PostgreSQL, DB2, H2 에서 사용 가능.
    //          - @SequenceGenerator(
    //                name = "BOARD_SEQ_GENERATOR"      //식별자 생성기 이름
    //              , sequenceName = "BOARD_SEQ"        //매핑할 데이터베이스 시퀀스 이름.
    //              , initialValue = 1                  //시퀀스 생성시 처음 값을 설정한다.
    //              , allocationSize = 1                //시퀀스 호출 시 증가하는 시퀀스 값.(성능 최적화에 사용). default : 50
    //              , catalog, schema                   //DB catalog, schema 이름.
    //            ) //클래스에 적용해도 되는 어노테이션.
    //          - @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BOARD_SEQ_GENERATOR")
    //          - persist()호출 시 DB 시퀀스로 식별자 조회 후 엔티티에 할당한다. 이후 엔티티는 영속성 컨텍스트에 저장되고, 트랜잭션 커밋 후 플러시가 일어나면서 db에 반영된다.
    //          - SEQUENCE 전략은 DB와 2번 통신한다.(시퀀스 생성 및 엔티티 저장)
    //      - TABLE : 키 생성 테이블을 사용한다.
    //          - DB 시퀀스를 흉내낸 테이블을 만들어 키 생성하는 전략.
    //          - 모든 DB에 적용 가능.
    //          - @TableGenerator(
    //                name = "BOARD_SEQ_GENERATOR"      //식별자 생성기 이름. non null
    //              , table = "MY_SEQUENCES"            //키생성 테이블 명
    //              , pkColumnName
    //              , pkColumnValue = "BOARD_SEQ"       //시퀀스 컬럼명.
    //              , valueColumnName                   //시퀀스 값 컬럼명. default : next_val
    //              , initialValue                      //초기 값, 마지막으로 생성된 값이 기준. default : 0
    //              , allocationSize = 1                //시퀀스 호출 시 증가하는 시퀀스 값.(성능 최적화에 사용). default : 50
    //              , catalog, schema                   //DB catalog, schema 이름
    //              , uniqueConstraints(DDL)            //유니크 제약 조건 지정.
    //            )
    //          - @GeneratorValue(strategy = GenerationType.TABLE, generator = "BOARD_SEQ_GENERATOR")
    //      - AUTO : 선택된 DB에 따라 IDENTITY, SEQUENCE, TABLE 전략 중 하나 자동 선택.

    //필드와 컬럼 매핑
    //  1. @Column : 컬럼을 매핑.
    //      - @Column의 속성.
    //          - name = ""                             //필드와 매핑할 테이블 컬럼 명.
    //          - insertable = true/false               //엔티티 저장 시 이 필드도 같이 저장. fasle 일 경우 db 저장하지 않음. false 는 읽기 전용일 경우 사용. default = true
    //          - updatable = true/false                //엔티티 수정 시 이 필드도 같이 수정. fasle 일 경우 db 저장하지 않음. false 는 읽기 전용일 경우 사용. default = true
    //          - table                                 //하나의 엔티티를 두 개 이상의 테이블과 매핑시 사용.
    //          - nullable(DDL)                         //null 값의 허용 여부 설정. false이면 DDL 생성 시 not null 제약조건 붙음. default = true
    //          - unique(DDL)                           //@Table의 uniqueConstraints와 같지만 한 컬럼에 간단히 유니크 제약조건 걸 때 사용. 만약 두 컬럼 이상을 사용해 유니크 제약조건 사용하려면 클래스 레벨엣
    //                                                    @Table.uniqueConstraints를 사용해야 한다.
    //          - length(DDL)                           //문자 길이 제약조건, String 타입에만 사용한다. default = 255
    //          - precision, scale(DDL)                 //BigDecimal 타입에서 사용.(BigInteger도 사용 가능.) precision은 소수점을 포함한 전체 자릿수.
    //                                                  //scale은 소수의 자릿수. double, floast 타입에는 적용되지 않음. 아주 큰 숫자나 정밀한 소수를 다룰 때만 사용.
    //                                                  //default -> precision = 19, scale = 2
    //                                                  //ex) @Column(precision = 10, scale = 2)private BigDecimal cal;
    //                                                        ---> 생성된 DDL :
    //                                                              cal numeric(10,2) //H2, PostgreSQL
    //                                                              cal number(10,2)  //오라클
    //                                                              cal decimal(10,2) //MySQL
    //          - columnDefinition(DDL)                 //ex) @Column(columnDefinition = "varchar(100) default 'EMPTY'")
    //  2. @Enumerated : 자바의 enum 타입을 매핑.
    //      - @Enumerated의 속성.
    //          - value                                 //EnumType.ORDINAL : enum의 순서를 DB에 저장.
    //                                                  //EnumType.STRING : enum 이름을 DB에 저장.
    //                                                  //default = EnumType.ORDINAL
    //      - 사용예시
    //          - @Enumerated(EnumType.STRING)private RoleType roleType;
    //            member.setRoleType(RoleType.ADMIN);   //---> DB에 문자 ADMIN으로 저장.
    //          - ORDINAL이라면 enum에 정의된 순서대로 ADMIN은 0, USER는 1로 DB에 저장됨.(ORDINAL은 문제 발생 소지가 ㅁ낳으므로 STRING으로 사용하기.)
    //  3. @Temporal : 날짜 타입 매핑.
    //      - @Temporal의 속성.
    //          - value                                 //TemporalType.DATE : 날짜, DB date 타입과 매핑.(2000-01-01)
    //                                                  //TemporalType.TIME : 시간, DB time 타입과 매핑.(01:01:00)
    //                                                  //TemporalType.TIMESTAMP : 날짜와 시간, DB timestamp 타입과 매핑.(2000-01-01 01:01:00)
    //      - @Temporal 생략 시 timestamp(H2, 오라클, PostgreSQL) or datetime(MySQL)으로 지정.
    //  4. @Lob : BLOB, CLOB 매핑.
    //      - @Lob은 지정할 수 있는 속성이 없다.
    //      - 매핑하는 필드 타입이 문자면 CLOB으로 매핑. 나머지는 BLOB으로 매핑.
    //      - CLOB : String, char[], java.sql.CLOB
    //      - BLOB : byte[], java.sqlBLOB
    //  5. @Transient : 특정 필드를 DB와 매핑하지 않음.
    //      - DB와 매핑하지 않음. 저장, 조회 불가. 객체에 임시로 값을 저장하고 싶을 경우 사용.
    //  6. @Access : JPA가 엔티티에 접근하는 방식 지정.
    //      - 필드 접근 : @Access(AccessType.FIELD)으로 지정. 필드에 직접 접근. 필드 접근 권한이 private여도 접근 가능. @Id가 필드에 지정된다.
    //      - 프로퍼티 접근 : @Access(AccessType.PROPERTY)로 지정. 접근자(getter) 사용. @Id가 프로퍼티에 있다.
    //      - 필드 접근과 프로퍼티 접근을 함께 사용 가능.
    private Long id;

    @Column(name = "NAME")
//            , nullable = false, length = 10)
    private String username;

    private Integer age;

    //다양한 매핑 사용을 위해 컬럼 추가.
    @Enumerated(EnumType.STRING)        //ENUM 사용시 매핑.
    private RoleType roleType;          //일반 회원과 관리자 구분.

    @Temporal(TemporalType.TIMESTAMP)   //자바의 날짜타입과 매핑.
    private Date createDate;            //회원 가입일.

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;      //회원 수정일.

    @Lob                                //길이 제한이 없기 때문에 VARCHAR 대신 CLOB 타입이 필요하다. @Lob 사용 시 CLOB, BLOB 타입을 매핑할 수 있다.
    private String description;         //회원을 설명할 수 있는 필드. 길이제한 없음.

    //연관관계 매핑
    @ManyToOne                          //다대일(N:1) 관계. 필수로 사용해야 한다.
    //@ManyToOne
    //  - 다대일(@ManyToOne)과 비슷한 일대일(@OneToOne) 관계도 있다. 반대편이 일대다 관계면 다대일 사용. 반대편 일대일 관계면 일대일 사용.
    //  - @ManyToOne 의 속성.
    //      - optional                                  //false로 설정하면 연관된 엔티티가 항상 있어야 한다. default = true
    //      - fetch                                     //글로벌 페치 전략을 설정한다. @ManyToOne=FetchType.EAGER, @OneToMany=FetchType.LAZY
    //      - cascade                                   //영속성 전이 기능 사용.
    //      - targetEntity                              //연관된 엔티티의 타입 정보를 설정한다. 이 기능은 거의 미사용. 컬렉션 사용해도 제네릭으로 타입 정보를 알 수 있다.
    @JoinColumn(name = "TEAM_ID")       //외래키를 매핑할 때 사용. 생략 가능하다.
    //@JoinColumn
    //  - @JoinColumn 의 속성.
    //      - name :                                    //매핑할 외래 키 이름. default = 필드명 + _ + 참조하는 테이블의 기본키 컬럼명.
    //      - referencedColumnName                      //외래키가 참조하는 대상 테이블의 컬럼속성은 테이블 생성시에만 사용.
    //      - unique, nullable, insertable,명. defualt = 참조하는 테이블의 기본키 컬럼명.
    //    //      - foreignKey(DDL)                           //외래키 제약조건을 직접 지정 가능. 이
    //        updatable, columnDefinition, table        //@Column의 속성과 같다.
    private Team team;                  //팀의 참조를 보관.


    //setTeam하나로 양방향 연관관계 모두 설정하도록 수정.
    public void setTeam(Team team) {

        //기존 팀이 있을 경우 기존 팀과 회원의 연관관계 제거.
        //이 코드가 없어도 테이블의 외래키는 정상적으로 반영된다.
        //하지만, 관계를 member1 -> teamA 에서 member1 -> teamB로 변경한 후 영속성 컨텍스트가 살아있을 경우
        //teamA.getMembers() 호출 시 member1이 반환되는 문제점이 있다.
        if (this.team != null) {
            this.team.getMembers().remove(this);
        }
        this.team = team;
        team.getMembers().add(this);
    }

    //@OneToOne
    //  - 테이블 관계에서 일대일 관계는 테이블 둘 중 어느 곳에나 외래키를 가질 수 있다.
    //  - 주테이블에 외래키 있을 경우
    //      - 외래 키를 객체 참조와 비슷하게 사용할 수 있다. 객체지향 개발자들이 선호.
    //      - 주테이블에 외래키가 있으므로 주테이블만 확인해도 대상 테이블과 연관관게가 있는지 확인 가능.
    //  - 대상 테이블에 외래키 있을 경우
    //      - 전통적인 DB 개발자들은 대상 테이블에 외래키를 두는 것을 선호.
    //      - 일대일에서 일대다로 변경될 때 테이블 구조를 그대로 유지할 수 있다.
    @OneToOne
    @JoinColumn(name = "LOCKER_ID")     //주테이블에 외래키 있을 경우. 연관 관계의 주인.
    //@OneToOne(mappedBy = "member")    //대상테이블에 외래키 있을 경우.
    private Locker locker;

    //RDB에서는 정규화된 테이블 2개로 다대다 관계를 표현할 수 없다.
    //따라서 다대다 관계를 일대다, 다대일 관계로 풀어내는 연결 테이블을 사용.
    //객체는 객체 2개로 다대다 관계 만들기 가능.(컬렉션 이용)
    @ManyToMany
    @JoinTable(name = "MEMBER_PRODUCT"                                  //@JoinTable.name : 연결 테이블 지정.
            , joinColumns = @JoinColumn(name = "MEMBER_ID")             //@JoinTable.joinColumns : 현재 방향인 회원과 매핑할 조인 컬럼 정보 지정.
            , inverseJoinColumns = @JoinColumn(name = "PRODUCT_ID"))
    //@JoinTable.inverseJoinColumns : 반대 방향인 상품과 매핑할 조인 컬럼 정보 지정.
    private List<Product> products = new ArrayList<Product>();

    //양방향 연관관계를 위한 편의 메소드 추간.
    public void addProduct(Product product) {
        this.products.add(product);
        product.getMembers().add(this);
    }

    //역방향. 다대다 양방향 연관관계 한계 극복을 위한 연결 엔티티 사용.
    @OneToMany(mappedBy = "member")
    private List<MemberProduct> memberProducts;


    //다대다:새로운 기본키를 사용. 기존 MEMBER_PRODUCT 테이블이 아닌 ORDER 테이블을 사용.
    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();


}
