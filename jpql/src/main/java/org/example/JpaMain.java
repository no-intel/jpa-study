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
            Team teamA = new Team();
            teamA.setName("TeamA");

            Team teamB = new Team();
            teamB.setName("TeamB");
            em.persist(teamA);
            em.persist(teamB);

            Member member1 = new Member();
            member1.setUsername("회원1");
            member1.setTeam(teamA);
            Member member2 = new Member();
            member2.setUsername("회원2");
            member2.setTeam(teamA);
            Member member3 = new Member();
            member3.setUsername("회원3");
            member3.setTeam(teamB);

            em.persist(member1);
            em.persist(member2);
            em.persist(member3);

            em.flush();
            em.clear();

            List<Member> resultList = em.createQuery("SELECT m FROM Member m JOIN FETCH m.team", Member.class)
                    .getResultList();

            resultList.forEach(o -> System.out.println("o = " + o ));

            em.clear();

            List<Team> resultList1 = em.createQuery("SELECT t FROM Team t JOIN FETCH t.members", Team.class)
                    .getResultList();

            resultList1.forEach(o -> {
                System.out.println("o = " + o.getName());
                o.getMembers().forEach(m -> System.out.println("m.getUsername() = " + m.getUsername()));
            });

            em.clear();

            List<Team> resultList2 = em.createQuery("SELECT t FROM Team t", Team.class)
                    .setMaxResults(0)
                    .setMaxResults(2)
                    .getResultList();

            resultList2.forEach(o -> {
                System.out.println("o = " + o.getName());
                o.getMembers().forEach(m -> System.out.println("m.getUsername() = " + m.getUsername()));
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