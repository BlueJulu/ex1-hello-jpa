import entity.Member;
import entity.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

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
            member.setTeam(team); // *** 팀을 지정해줘야 한다.(주의) 그래야 두 객체간에 연결이 된다 ***
            em.persist(member);

            team.getMembers().add(member); // 이 문장은 JPA에서 DB 적용 무시한다. read-only 이기 때문
            // 문제는 아래 두 코드를 주석 처리하고 그 아래애 find를 하게 되면 1차 캐시에서 찾기 때문에 위의 List에 add 하는 코드가 없으면
            // 결과가 아무 것도 나오지 않는다. 따라서 Team을 신규 생성, Member 생성(setTeam()), 그리고 다시 Team에서 List에 Member를 등록해야 한다.
            // 운영 코드에서는 아래 flush, clear 안 붙이니까. <<< 양방향 연관관계 매핑 시 주의 사항 임 >>>
            
            em.flush();
            em.clear();

            tx.commit();
        } catch(Exception e){
            tx.rollback();
        } finally {
        em.close(); // EntityManager가 내부적으로 DB 커넥션까지 물고 동작하기 때문에 여기서 크로징이 매우 중요
        }

        emf.close();
    }
}