package entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Team extends BaseEntity{

    @Id @GeneratedValue
    @Column(name = "TEAM_ID")
    private Long id;

    private String name;

    @OneToMany(mappedBy = "team") // 나는 변수명 team으로 매핑되어 있는 애야! 라는 뜻. mappedBy: 읽기만 가능. 업데이트 시 JPA는 이거 자체를 무시함
    private List<Member> members = new ArrayList<>(); // *** 초기화한다(관례), add 시 null 포인터가 안 뜸 ***

    // Member에 연관관계 전용 changeTeam 함수를 작성하거나, Team에 연관관계 전용 addMember 함수를 작성 : 둘중 하나 만 작성
    // 이 곳보다 Team에 만드는 것이 나는 더 편하다(상황에 따라 선택해야 한다) 그 경우, 아래 함수는 삭제할 것
//    public void addMember(Member member){
//        member.setTeam(this);
//        members.add(member);
//    }

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

    // 무한 루프 발생(StackOverflow)하게 됨. Member.toString() 볼 것
    @Override
    public String toString() {
        return "Team{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", members=" + members + // members 컬렉션 내에 있는 member.toString()을 또 호출
                '}';
    }
}
