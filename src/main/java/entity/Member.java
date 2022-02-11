package entity;

import hellojpa.RoleType;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
public class Member {

    @Id // PK 매핑
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    //@Column(name = "name",) // 테이블 컬럼명과 변수명을 다르게 쓰고 싶을 때, 컬럼명을 이렇게 명시한다
    // columnDefinition을 통하여 컬럼 정보를 직접 명기할 수 있다(DDL 반영됨)
    @Column(name = "name", nullable = false, columnDefinition = "varchar(100) default 'EMPTY'")
    private String username;

    private Integer age; // Integer에 가장 적절한 숫자 타입으로 DB에서 선택이 되어짐

    @Enumerated(EnumType.STRING) // DB에는 enum 타입이 없기 때문에 이 어노테이션을 사용함
    private RoleType roleType; // EnumType은 반드시 STRING를 사용해야 함

    @Temporal(TemporalType.TIMESTAMP) // 날짜, 시간 외에 DB는 날짜시간 타입이 더 있기 때문에 타입을 지정해 줘야 한다
    private Date createdDate;

    private LocalDate testLocalDate; // DB의 date 타입으로 쿼리문 생성됨
    private LocalDateTime testLocalDateTime; // DB의 timestamp 타입으로 쿼리문 생성됨

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;

    @Lob // DB에 varchar를 넘어서는 큰 데이터를 넣고 싶을 때 사용
    private String description; // @Lob 인데, 문자타입 변수면 clob 필드 타입으로 쿼리가 생성된다
    
    @Transient // DB와 관계 없는 작업을 위한 멤버에 사용
    private int temp; // 위 어노테이션 때문에 쿼리문에 적용 안됨

    public Member() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public RoleType getRoleType() {
        return roleType;
    }

    public void setRoleType(RoleType roleType) {
        this.roleType = roleType;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getTemp() {
        return temp;
    }

    public void setTemp(int temp) {
        this.temp = temp;
    }
}
