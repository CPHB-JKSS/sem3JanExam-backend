package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import entities.Role;
import entities.Sport;
import entities.SportTeam;
import entities.User;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import org.eclipse.persistence.jpa.jpql.Assert;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.*;
import utils.EMF_Creator;

import javax.json.Json;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

//Disabled
public class PublicResourceTest {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;

    private Sport footballSport, BasketballSport;
    private SportTeam footballTeamSenior, footballTeamJunior, basketballTeamSenior, basketballTeamJunior;

    static HttpServer startServer() {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

    @BeforeAll
    public static void setUpClass() {
        //This method must be called before you request the EntityManagerFactory
        EMF_Creator.startREST_TestWithDB();
        emf = EMF_Creator.createEntityManagerFactoryForTest();

        httpServer = startServer();
        //Setup RestAssured
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
        RestAssured.defaultParser = Parser.JSON;
    }

    @AfterAll
    public static void closeTestServer() {
        //Don't forget this, if you called its counterpart in @BeforeAll
        EMF_Creator.endREST_TestWithDB();
        
        httpServer.shutdownNow();
    }

    // Setup the DataBase (used by the test-server and this test) in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the EntityClass used below to use YOUR OWN (renamed) Entity class
    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();

        footballSport = new Sport("Football", "This a football sport description");
        BasketballSport = new Sport("Basketball", "This a Basketball sport description");

        footballTeamSenior = new SportTeam("Senior: Football", 300000, 45, 99, footballSport);
        footballTeamJunior = new SportTeam("Junior: Football", 250000, 10, 15, footballSport);
        basketballTeamSenior = new SportTeam("Senior: Basketball", 350000, 45, 99, BasketballSport);
        basketballTeamJunior = new SportTeam("Junior: Basketball", 300000, 10, 15, BasketballSport);

        try {
            em.getTransaction().begin();
            //Delete existing users and roles to get a "fresh" database
            em.createQuery("delete from User").executeUpdate();
            em.createQuery("delete from Role").executeUpdate();
            em.createNamedQuery("SportTeam.deleteAllRows").executeUpdate();
            em.createNamedQuery("Sport.deleteAllRows").executeUpdate();

            Role userRole = new Role("user");
            Role adminRole = new Role("admin");
            User user = new User("user", "test");
            user.addRole(userRole);
            User admin = new User("admin", "test");
            admin.addRole(adminRole);
            User both = new User("user_admin", "test");
            both.addRole(userRole);
            both.addRole(adminRole);
            em.persist(userRole);
            em.persist(adminRole);
            em.persist(user);
            em.persist(admin);
            em.persist(both);

            em.persist(footballSport);
            em.persist(BasketballSport);
            em.persist(footballTeamSenior);
            em.persist(footballTeamJunior);
            em.persist(basketballTeamSenior);
            em.persist(basketballTeamJunior);
            //System.out.println("Saved test data to database");
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    //This is how we hold on to the token after login, similar to that a client must store the token somewhere
    private static String securityToken;

    //Utility method to login and set the returned securityToken
    private static void login(String role, String password) {
        String json = String.format("{username: \"%s\", password: \"%s\"}", role, password);
        securityToken = given()
                .contentType("application/json")
                .body(json)
                //.when().post("/api/login")
                .when().post("/login")
                .then()
                .extract().path("token");
        //System.out.println("TOKEN ---> " + securityToken);
    }

    private void logOut() {
        securityToken = null;
    }

    @Test
    public void serverIsRunning() {
        given().when().get("/info").then().statusCode(200);
    }

    @Test
    public void testRestPublicSportsEndpoint() {
        given()
                .contentType("application/json")
                .when()
                .get("/public/sports").then()
                .statusCode(200)
                .body(CoreMatchers.allOf(
                        containsString("Basketball"),
                        containsString("Football")
                ));
    }

    @Test
    public void testRestPublicTeamsEndpoint() {
        given()
                .contentType("application/json")
                .when()
                .get("/public/teams").then()
                .statusCode(200)
                .body(CoreMatchers.allOf(
                        containsString("Junior: Basketball"),
                        containsString("Junior: Football"),
                        containsString("Senior: Basketball"),
                        containsString("Senior: Football")
                ));
    }
}
