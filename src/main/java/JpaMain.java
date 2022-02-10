import entity.Member;

import javax.persistence.*;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try{
            // 비영속 상태
            Member member = new Member();
            member.setId(6L);
            member.setName("HelloJPA");

            // 영속 상태 - 이 때 DB에 저장되는 것이 아님
            System.out.println("=== BEFORE ===");
            em.persist(member);
            System.out.println("=== AFTER ===");

            // 영속 상태가 됐다고 바로 DB로 쿼리가 가지 않음
            // --> commit() 하는 싯점에 영속성 컨텍스트에 있는 내용이 DB로 쿼리가 날아감
            tx.commit();
        } catch(Exception e){
            tx.rollback();
        } finally {
        em.close(); // EntityManager가 내부적으로 DB 커넥션까지 물고 동작하기 때문에 여기서 크로징이 매우 중요
        }

        emf.close();
    }
}