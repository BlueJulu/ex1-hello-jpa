package entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Team {

    @Id @GeneratedValue
    @Column(name = "TEAM_ID")
    private Long id;

    private String name;

    @OneToMany(mappedBy = "team") // 나는 변수명 team으로 매핑되어 있는 애야! 라는 뜻. mappedBy: 읽기만 가능
    private List<Member> members = new ArrayList<>(); // *** 초기화한다(관례), add 시 null 포인터가 안 뜸 ***

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }
}
