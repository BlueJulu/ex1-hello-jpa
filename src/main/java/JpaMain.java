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
            em.persist(team); // 이제 1차 캐시에 id 값이 들어감. 영속 상태

            Member member = new Member();
            member.setUserName("member1");
            member.setTeam(team);
            em.persist(member);

            em.flush(); // 쿼리 수행 - 1차 캐시와 DB 동기화
            em.clear(); // 1차 캐시 삭제
            // *** 중요 *** 위 코드 두줄 때문에 아래 코드 실행 시 DB에 대한 SQL 수행이 발생하게 됨
            Member findMember = em.find(Member.class, member.getId());
            // 단방향에서 양방향으로 설정을 하였으므로 Team에서 Member 참조가 가능해졌음
            List<Member> members = findMember.getTeam().getMembers();

            for(Member m : members){
                System.out.println("m = " + m.getUserName());
            }

            tx.commit();
        } catch(Exception e){
            tx.rollback();
        } finally {
        em.close(); // EntityManager가 내부적으로 DB 커넥션까지 물고 동작하기 때문에 여기서 크로징이 매우 중요
        }

        emf.close();
    }
}