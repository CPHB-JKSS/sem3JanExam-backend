package facades;

import dtos.SportDTO;
import entities.Sport;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Joakim Skaarup Stensnæs
 */
public class SportFacade {

    private static SportFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private SportFacade() {
    }


    /**
     * @param _emf
     * @return an instance of this facade class.
     */
    public static SportFacade getSportFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new SportFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }


    //TODO public SportDTO getSport(String id) {}
    public SportDTO getSportDTO(String id) {
        EntityManager em = emf.createEntityManager();
        Sport sport;
        try {
            sport = em.find(Sport.class, id);
        } finally {
            em.close();
        }
        return new SportDTO(sport);
    }

    //TODO public SportDTO addSport(SportDTO sportDTO) {}
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

    //TODO public String removeSport(String id) {}
    public void removeSport(String id) {
        EntityManager em = emf.createEntityManager();
        Sport sport;
        try {
            sport = em.find(Sport.class, id);
            em.getTransaction().begin();
            em.remove(sport);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    //TODO public List<SportDTO> getAllSports() {}
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
}