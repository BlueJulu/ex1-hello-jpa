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

            // Member member = em.find(Member.class, 1L);
            // printMember(member); // 1) Member 만 select. 여기서 2)와 같이 select 하면 낭비
            //printMemberAndTeam(member); // 2) Member와 Team 을 select

            Member member = new Member();
            member.setUserName("hello");

            em.persist(member);

            em.flush();
            em.clear();

//            Member findMember = em.find(Member.class, member.getId());
//            System.out.println("findMember.id = " + findMember.getId());
//            System.out.println("findMember.username = " + findMember.getUserName());

            Member findMember = em.getReference(Member.class, member.getId()); // 이 싯점에는 DB에 쿼리를 안 함
            System.out.println("findMember = " + findMember.getClass()); // 프록시에서 가져온 가짜 Member 임
            System.out.println("findMember.id = " + findMember.getId());
            System.out.println("findMember.username = " + findMember.getUserName()); // DB에 쿼리를 함
            System.out.println("findMember.username = " + findMember.getUserName()); // 쿼리 안 하고 프록시가 실제 Entity에서 가져옴
            
            tx.commit();
        } catch(Exception e){
            tx.rollback();
        } finally {
        em.close(); // EntityManager가 내부적으로 DB 커넥션까지 물고 동작하기 때문에 여기서 크로징이 매우 중요
        }

        emf.close();
    }

    private static void printMember(Member member) {
        System.out.println("member = " + member.getUserName());
    }

    private static void printMemberAndTeam(Member member) {
        String userName = member.getUserName();
        System.out.println("username = " + userName);

        Team team = member.getTeam();
        System.out.println("team = " + team.getName());
    }
}