package utils;

import entities.Sport;
import entities.SportTeam;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class SetupTestSports {

  public static void main(String[] args) {

    EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
    EntityManager em = emf.createEntityManager();

    Sport footballSport = new Sport("Football", "This a football sport description");
    Sport BasketballSport = new Sport("Basketball", "This a Basketball sport description");

    SportTeam footballTeamSenior = new SportTeam("Senior: football", 300000, 45, 99, footballSport);
    SportTeam footballTeamJunior = new SportTeam("Junior: football", 250000, 10, 15, footballSport);
    SportTeam basketballTeamSenior = new SportTeam("Senior: Basketball", 350000, 45, 99, BasketballSport);
    SportTeam basketballTeamJunior = new SportTeam("Junior: Basketball", 300000, 10, 15, BasketballSport);

    em.getTransaction().begin();

    em.persist(footballSport);
    em.persist(BasketballSport);
    em.persist(footballTeamSenior);
    em.persist(footballTeamJunior);
    em.persist(basketballTeamSenior);
    em.persist(basketballTeamJunior);

    em.getTransaction().commit();
  }
}