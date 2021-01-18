package facades;

import entities.Sport;
import entities.SportTeam;
import org.junit.jupiter.api.*;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

//Uncomment the line below, to temporarily disable this test
//@Disabled
public class APIFacadeTest {

    private static EntityManagerFactory emf;
    private static APIFacade FACADE;

    private Sport footballSport, BasketballSport;
    private SportTeam footballTeamSenior, footballTeamJunior, basketballTeamSenior, basketballTeamJunior;

    public APIFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        FACADE = APIFacade.getAPIFacade(emf);
    }

    @AfterAll
    public static void tearDownClass() {
        //Clean up database after test is done or use a persistence unit with drop-and-create to start up clean on every test
    }

    // Setup the DataBase in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the script below to use YOUR OWN entity class
    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        footballSport = new Sport("Football", "This a football sport description");
        BasketballSport = new Sport("Basketball", "This a Basketball sport description");

        footballTeamSenior = new SportTeam("Senior: football", 300000, 45, 99, footballSport);
        footballTeamJunior = new SportTeam("Junior: football", 250000, 10, 15, footballSport);
        basketballTeamSenior = new SportTeam("Senior: Basketball", 350000, 45, 99, BasketballSport);
        basketballTeamJunior = new SportTeam("Junior: Basketball", 300000, 10, 15, BasketballSport);

        try {
            em.getTransaction().begin();
            em.createNamedQuery("SportTeam.deleteAllRows").executeUpdate();
            em.createNamedQuery("Sport.deleteAllRows").executeUpdate();
            em.persist(footballSport);
            em.persist(BasketballSport);
            em.persist(footballTeamSenior);
            em.persist(footballTeamJunior);
            em.persist(basketballTeamSenior);
            em.persist(basketballTeamJunior);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @AfterEach
    public void tearDown() {
        //Remove any data after each test was run
    }

    //TODO addSport
    @Test
    void testAddSport() {
        assertEquals(2, FACADE.getAllSports().size(), "Expects 2 sports in the database BEFORE adding");
        FACADE.addSport("Handball", "This a Handball sport description");
        assertEquals(3, FACADE.getAllSports().size(), "Expects 3 sports in the database AFTER adding");
    }

    //TODO editSport
    @Test
    void testEditSport() {
        //FACADE.editSport()
    }

    @Test
    void testRemoveSport() {
        assertEquals(2, FACADE.getAllSports().size(), "Expects 2 sports in the database BEFORE removing");
        FACADE.removeSportTeam("Senior: Basketball");//Is referencing 'Basketball'
        FACADE.removeSportTeam("Junior: Basketball");//Is referencing 'Basketball'
        FACADE.removeSport("BasketBall");
        assertEquals(1, FACADE.getAllSports().size(), "Expects 1 sports in the database AFTER removing");
    }

    @Test
    void testGetAllSport() {
        assertEquals(2, FACADE.getAllSports().size(), "Expects 2 sports in the database");
    }

    @Test
    void testAddSportTeam() {
        assertEquals(4, FACADE.getAllTeams().size(), "Expects 4 teams in the database BEFORE adding");
        FACADE.addSportTeam("Senior: football (2)", 300000, 45, 99, "Football");
        assertEquals(5, FACADE.getAllTeams().size(), "Expects 5 teams in the database AFTER adding");
    }
    //TODO editTeam
    @Test
    void testEditSportTeam() {
        //FACADE.editSportTeam()
    }

    @Test
    void testRemoveSportTeam() {
        assertEquals(4, FACADE.getAllTeams().size(), "Expects 4 teams in the database BEFORE removing");
        FACADE.removeSportTeam("Junior: Basketball");
        assertEquals(3, FACADE.getAllTeams().size(), "Expects 3 teams in the database AFTER removing");
    }
    @Test
    void testGetAllTeams() {
        assertEquals(4, FACADE.getAllTeams().size(), "Expects 4 teams in the database");
    }

    //THIS METHOD IS FOR REFERENCE
    @Test
    public void testEditPerson() {
        /*p2.addPhone(new Phone("90909090",""));
        facade.editPerson(2, new PersonDTO(p2));
        List<Phone> phones = facade.getPersonByPhone("90909090").getPhones();
        assertEquals("90909090", phones.forEach(phone -> phone.getNumber()));*/
    }
}
