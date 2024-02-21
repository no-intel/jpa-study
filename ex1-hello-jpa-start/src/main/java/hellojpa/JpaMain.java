package hellojpa;

import jakarta.persistence.*;

import java.util.List;

public class JpaMain {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            // 영속
//            Member member = em.find(Member.class, 150L);
            Member member = new Member(1L, "AAAA");
//            member.setName("AAAAA");

//            em.detach(member);
            em.persist(member);
            em.flush();
            em.clear();
            Member findMember = em.getReference(Member.class, member.getId());
            System.out.println("findMember type = " + findMember.getClass());
            System.out.println("=============================================");
            System.out.println("findMember = " + findMember.getName());
            System.out.println("=============================================");
            System.out.println("findMember type = " + findMember.getClass());

//            Member member2 = em.find(Member.class, 150L);
            System.out.println("=============================================");
            Member member1 = em.find(Member.class, member.getId());
            System.out.println("member1.getClass() = " + member1.getClass());
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
            emf.close();
        }

    }
}
