package entity;

import javax.persistence.*;

@Entity
public class Member {

    @Id @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    @Column(name = "USERNAME")
    private String userName;

//    @Column(name = "TEAM_ID")
//    private Long teamId;
    @ManyToOne(fetch = FetchType.LAZY) // Member가 여럿이고 Team은 하나. 지연로딩 전략을 사용하면 쿼리가 분리되어서 나감
    @JoinColumn(name = "TEAM_ID") // teamId가 아닌 Team 참조와 FK를 매핑. DB에 값을 넣거나 업데이트할 때는 이 객체만 참조함.
    private Team team; // 맞은편 객체인 Team에서는 읽기(조회)만 가능함. 이것을 모르고 Team에서 setXXX() 하여 값이 반영 안 되서 고생하지 말 것

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    // Member에 연관관계 전용 changeTeam 함수를 작성하거나, Team에 연관관계 전용 addMember 함수를 작성 : 둘중 하나 만 작성
    public void changeTeam(Team team) { // 연관관계 편의 메소드. 메소드 이름도 setTeam --> changeTeam으로 변경
        this.team = team;               // 다시 setTeam(Setter)는 추가할 것
        team.getMembers().add(this);
    }

    // 무한 루프 발생(StackOverflow)하게 됨. Team.toString() 볼 것
    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", team=" + team + // team.toString()을 또 호출
                '}';
    }
}
