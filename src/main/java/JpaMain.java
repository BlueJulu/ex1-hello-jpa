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
            em.persist(team); // 이제 1차 캐시에 id 값이 들어감. 영속 상태

            Member member = new Member();
            member.setUserName("member1");
            member.setTeam(team);
            em.persist(member);

            // 아래 코드 밑에 있는 find 문은 영속성 컨텍스트 때문에 1차 캐시에서 가져오게 된다.
            // 아래 두 코드는 원래 안 쓰는데, 굳이 select 쿼리문을 보고 싶다면 이 두가지 코드를 앞에 삽입하면 된다
            em.flush(); // 쿼리 수행 - 1차 캐시와 DB 동기화
            em.clear(); // 1차 캐시 삭제

            Member findMember = em.find(Member.class, member.getId()); // 1차 캐시가 삭제 되었기 때문에 쿼리문 수행
                            // 만약 위 두줄 코드(flush, clear)를 삭제하면 쿼리문 수행 안함(1차 캐시에서 가져오기 때문)
            Team findTeam = findMember.getTeam(); // 객체지향적 방식
            
            // 수정 - 손쉽게 변경이 가능0
            Team newTeam = em.find(Team.class, 100L); // 만약 100번 데이터가 있다면
            findMember.setTeam(newTeam); // 수정도 이렇게 간단히 할 수 있다. update 쿼리에 의해서 자동 FK가 업데이트 됨

            tx.commit();
        } catch(Exception e){
            tx.rollback();
        } finally {
        em.close(); // EntityManager가 내부적으로 DB 커넥션까지 물고 동작하기 때문에 여기서 크로징이 매우 중요
        }

        emf.close();
    }
}