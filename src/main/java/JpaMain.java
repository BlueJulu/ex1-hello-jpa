import entity.Member;

import javax.persistence.*;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try{
            List<Member> result = em.createQuery("select m from Member as m", Member.class)
                    .getResultList();
            // m 으로 한 이유: 대상이 객체가 됨. 테이블을 대상으로 절대 코드를 작성하지 않음
            // 객체를 대상으로 쿼리를 날림
            // 위 쿼리문의 의미: Member 객체를 다 가져와~!
            for(Member member : result){
                System.out.println("member.name = " + member.getName());
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
