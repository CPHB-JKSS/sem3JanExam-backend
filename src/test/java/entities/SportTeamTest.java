package entities;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SportTeamTest {

    private Sport footballSport, BasketballSport;
    private SportTeam footballTeamSenior, footballTeamJunior, basketballTeamSenior, basketballTeamJunior;

    @BeforeEach
    void setUp() {
        footballSport = new Sport("Football", "This a football sport description");
        BasketballSport = new Sport("Basketball", "This a Basketball sport description");

        footballTeamSenior = new SportTeam("Senior: Football", 300000, 45, 99, footballSport);
        footballTeamJunior = new SportTeam("Junior: Football", 250000, 10, 15, footballSport);
        basketballTeamSenior = new SportTeam("Senior: Basketball", 350000, 45, 99, BasketballSport);
        basketballTeamJunior = new SportTeam("Junior: Basketball", 300000, 10, 15, BasketballSport);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testGetTeamName() {
        assertEquals("Senior: Football", footballTeamSenior.getTeamName());
    }

    @Test
    void testSetTeamName() {
        assertEquals("Senior: Football", footballTeamSenior.getTeamName());
        footballTeamSenior.setTeamName("Pro: Football");
        assertEquals("Pro: Football", footballTeamSenior.getTeamName());
    }

    @Test
    void testGetPricePerYear() {
        assertEquals(350000, basketballTeamSenior.getPricePerYear());
    }

    @Test
    void testSetPricePerYear() {
        assertEquals(350000, basketballTeamSenior.getPricePerYear());
        basketballTeamSenior.setPricePerYear(380000);
        assertEquals(380000, basketballTeamSenior.getPricePerYear());
    }

    @Test
    void testGetMinAge() {
        assertTrue(12 > basketballTeamJunior.getMinAge());
        assertFalse(12 > basketballTeamSenior.getMinAge());
        assertEquals(10, basketballTeamJunior.getMinAge());
    }

    @Test
    void testSetMinAge() {
        assertEquals(10, basketballTeamJunior.getMinAge());
        basketballTeamJunior.setMinAge(9);
        assertNotEquals(10, basketballTeamJunior.getMinAge());
    }

    @Test
    void testGetMaxAge() {
        assertTrue(12 < basketballTeamJunior.getMaxAge());
        assertFalse(12 > basketballTeamSenior.getMaxAge());
        assertEquals(15, basketballTeamJunior.getMaxAge());
    }

    @Test
    void testSetMaxAge() {
        assertEquals(15, basketballTeamJunior.getMaxAge());
        basketballTeamJunior.setMaxAge(18);
        assertNotEquals(15, basketballTeamJunior.getMaxAge());
    }

    @Test
    void testGetSport() {
        assertSame(footballSport, footballTeamJunior.getSport());
    }

    @Test
    void testSetSport() {
        assertSame(footballSport, footballTeamJunior.getSport());
        footballTeamJunior.setSport(BasketballSport);
        assertNotSame(footballSport, footballTeamJunior.getSport());
        assertSame(BasketballSport, footballTeamJunior.getSport());
    }
}