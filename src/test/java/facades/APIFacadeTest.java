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
    private static APIFacade facade;

    private Person p1, p2, p3, p4;

    public APIFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = APIFacade.getAPIFacade(emf);
    }

    @AfterAll
    public static void tearDownClass() {
//        Clean up database after test is done or use a persistence unit with drop-and-create to start up clean on every test
    }

    // Setup the DataBase in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the script below to use YOUR OWN entity class
    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        Sport footballSport = new Sport("Football", "This a football sport description");
        Sport BasketballSport = new Sport("Basketball", "This a Basketball sport description");

        SportTeam footballTeamSenior = new SportTeam("Senior: football", 300000, 45, 99, footballSport);
        SportTeam footballTeamJunior = new SportTeam("Junior: football", 250000, 10, 15, footballSport);
        SportTeam basketballTeamSenior = new SportTeam("Senior: Basketball", 350000, 45, 99, BasketballSport);
        SportTeam basketballTeamJunior = new SportTeam("Junior: Basketball", 300000, 10, 15, BasketballSport);

        try {
            em.getTransaction().begin();
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

    @Test
    public void testGetS() {
        assertEquals(4, facade.getPersons().size(), "Expects four members in the database");
    }

    @Test
    public void testGetPersonsByHobby() {
        assertEquals(3, facade.getPersonsByHobby("Baking").size(), "Expects three differen members sharing the same hobby 'Baking'");
    }

    @Test
    public void testGetPersonsByZip() {
        assertEquals(2, facade.getPersonsByZip("2200").size(), "Expects two differen members sharing the same zipcode '2200'");
    }

    @Test
    public void testGetSamePersonByPhone() {
        PersonDTO p1 = facade.getPersonByPhone("20202020");
        PersonDTO p2 = facade.getPersonByPhone("30303030");
        assertEquals(p1.getEmail(), p2.getEmail(), "Expects the email address of the two different objects, to be equal, as the two different phone numbers are assigned to the same person");
    }

    @Test
    public void testGetDifferentPersonByPhone() {
        PersonDTO p1 = facade.getPersonByPhone("20202020");
        PersonDTO p2 = facade.getPersonByPhone("50505050");
        assertNotEquals(p1.getEmail(), p2.getEmail(), "Expects the email address of the two different objects, to be not be equal, as the two different phone numbers are assigned to different persons");
    }

    @Test
    public void testAddPerson() {
        Person p5 = new Person("Lars", "Larsen", "lars@mail.dk");
        Address a2 = new Address("Tagensvej 110", "N/A");
        Phone ph1 = new Phone("80808080", "");
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(p5);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        assertEquals(5, facade.getPersons().size(), "Expects the list of persons recieved from the database, to be 5, after adding an additional person");
    }

    //TODO public void testEditPerson()
    @Test
    public void testEditPerson() {
        /*p2.addPhone(new Phone("90909090",""));
        facade.editPerson(2, new PersonDTO(p2));
        List<Phone> phones = facade.getPersonByPhone("90909090").getPhones();
        assertEquals("90909090", phones.forEach(phone -> phone.getNumber()));*/
    }
}
