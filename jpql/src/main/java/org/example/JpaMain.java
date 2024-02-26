package org.example;

import jakarta.persistence.*;

import java.util.List;

public class JpaMain {
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            Member member = new Member();
            member.setUsername("member1");
            em.persist(member);

            TypedQuery<Member> query = em.createQuery("select m from Member as m", Member.class);
            List<Member> resultList = query.getResultList();
            resultList.forEach(System.out::println);

            Member parameter = em.createQuery("select m from Member AS m WHERE m.username=:parameter", Member.class)
                    .setParameter("parameter", member.getUsername())
                    .getSingleResult();
            System.out.println(parameter);

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