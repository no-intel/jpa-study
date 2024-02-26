package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.List;

public class JpaMain {
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            for (int i = 0; i < 100; i++) {
                Member member = new Member();
                member.setUsername("member" + i);
                member.setAge(i + 1);
                em.persist(member);
                if (i == 0) {
                    member.setType(MemberType.ADMIN);
                }
            }

            em.flush();
            em.clear();

            List<Object[]> resultList = em.createQuery("select m.username, 'HELLO', 'She''s', true from Member m " +
                            "where m.type = org.example.MemberType.ADMIN", Object[].class)
                    .getResultList();
            resultList.forEach(m -> {
                System.out.println("m[0] = " + m[0]);
                System.out.println("m[1] = " + m[1]);
                System.out.println("m[2] = " + m[2]);
                System.out.println("m[3] = " + m[3]);
            });

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