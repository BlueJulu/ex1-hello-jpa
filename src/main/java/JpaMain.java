import entity.Member;
import entity.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try{
            Team team = new Team();
            team.setName("TeamA");
            //team.getMembers().add(member);
            em.persist(team);

            Member member = new Member();
            member.setUserName("member1");
            member.changeTeam(team); // 연관관계 메소드로 보완했기 때문에 이 문장 하나로 끝. Member에 Team 정보 등록하고 Team의 List에 Member 정보 등록
                                  // Team에서 member add할 필요 없음. 메소드를 원자적으로 사용하게 됨(하나만 호출해도 양쪽으로 값이 걸림)
                                  // 함수명도 setTeam --> changeTeam.(geter/setter 관례 때문에 이름 변경)
            em.persist(member);

            // 연관관계 메소드(setTeam) 생성한 후, 아래 코드 삭제
            //team.getMembers().add(member);

//            em.flush();
//            em.clear();

            Team findTeam = em.find(Team.class, team.getId());
            List<Member> members = findTeam.getMembers();

            System.out.println("=================");
            for(Member m : members){
                System.out.println("m = " + m.getUserName());
            }
            System.out.println("=================");

            tx.commit();
        } catch(Exception e){
            tx.rollback();
        } finally {
        em.close(); // EntityManager가 내부적으로 DB 커넥션까지 물고 동작하기 때문에 여기서 크로징이 매우 중요
        }

        emf.close();
    }
}