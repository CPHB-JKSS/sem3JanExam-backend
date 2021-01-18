package facades;

import dtos.SportDTO;
import dtos.SportTeamDTO;
import entities.Sport;
import entities.SportTeam;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Joakim Skaarup Stensnæs
 */
public class APIFacade {

    private static APIFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private APIFacade() {
    }


    /**
     * @param _emf
     * @return an instance of this facade class.
     */
    public static APIFacade getAPIFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new APIFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    /* Sport methods */
    public SportDTO getSportDTO(String name) {
        EntityManager em = emf.createEntityManager();
        Sport sport;
        try {
            sport = em.find(Sport.class, name);
        } finally {
            em.close();
        }
        return new SportDTO(sport);
    }

    public SportDTO addSport(String name, String desc) {
        EntityManager em = emf.createEntityManager();
        Sport sport = new Sport(name, desc);
        try {
            em.getTransaction().begin();
            em.persist(sport);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new SportDTO(sport);
    }

    public SportDTO editSport(String name, String description) {
        EntityManager em = emf.createEntityManager();
        Sport sport ;
        try {
            sport = em.find(Sport.class, name);
            sport.setSportDescription(description);

            em.getTransaction().begin();
            em.persist(sport);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new SportDTO(sport);
    }

    public void removeSport(String name) {
        EntityManager em = emf.createEntityManager();
        Sport sport;
        try {
            sport = em.find(Sport.class, name);
            em.getTransaction().begin();
            em.remove(sport);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public List<SportDTO> getAllSports() {
        EntityManager em = emf.createEntityManager();
        List<SportDTO> sportDTOList;
        try {
            TypedQuery<Sport> query = em.createQuery("SELECT s FROM Sport s", Sport.class);
            List<Sport> sports = query.getResultList();
            sportDTOList = new ArrayList<>();
            sports.forEach((Sport sport) -> sportDTOList.add(new SportDTO(sport)));
        } finally {
            em.close();
        }
        return sportDTOList;
    }

    /* SportTeam methods */
    public SportTeamDTO addSportTeam(String name, Integer price, Integer minAge, Integer maxAge, String sportString) {
        EntityManager em = emf.createEntityManager();
        Sport sport;
        SportTeam sportTeam;
        try {
            sport = em.find(Sport.class, sportString);
            System.out.println(sport.getSportName());
            sportTeam = new SportTeam(name, price, minAge, maxAge, sport);

            em.getTransaction().begin();
            em.persist(sportTeam);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new SportTeamDTO(sportTeam);
    }

    public SportTeamDTO editSportTeam(String name, Integer price, Integer minAge, Integer maxAge, String sportString) {
        EntityManager em = emf.createEntityManager();
        SportTeam sportTeam;
        try {
            sportTeam = em.find(SportTeam.class, name);
            sportTeam.setMinAge(minAge);
            sportTeam.setMaxAge(maxAge);
            sportTeam.setPricePerYear(price);
            sportTeam.setSport(em.find(Sport.class, sportString));
            em.getTransaction().begin();
            em.persist(sportTeam);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new SportTeamDTO(sportTeam);
    }

    public void removeSportTeam(String name) {
        EntityManager em = emf.createEntityManager();
        SportTeam sportTeam;
        try {
            sportTeam = em.find(SportTeam.class, name);
            em.getTransaction().begin();
            em.remove(sportTeam);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public List<SportTeamDTO> getAllTeams() {
        EntityManager em = emf.createEntityManager();
        List<SportTeamDTO> sportTeamDTOList;
        try {
            TypedQuery<SportTeam> query = em.createQuery("SELECT st FROM SportTeam st", SportTeam.class);
            List<SportTeam> sportTeams = query.getResultList();
            sportTeamDTOList = new ArrayList<>();
            sportTeams.forEach((SportTeam sportTeam) -> sportTeamDTOList.add(new SportTeamDTO(sportTeam)));
        } finally {
            em.close();
        }
        return sportTeamDTOList;
    }
}