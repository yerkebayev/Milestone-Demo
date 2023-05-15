package unist.ep.milestone2.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ClubTest {

    @Test
    public void testClubConstructor() {
        Club club = new Club(1L, "Club Name", "club@test.com", 1L, "Club description", "Club mission", "Club contact", 1L);

        assertEquals(1L, club.getId());
        assertEquals("Club Name", club.getName());
        assertEquals("club@test.com", club.getEmail());
        assertEquals(1L, club.getClubType_id());
        assertEquals("Club description", club.getDescription());
        assertEquals("Club mission", club.getMission());
        assertEquals("Club contact", club.getContact());
        assertEquals(1L, club.getHead_id());
    }

    @Test
    public void testClubGettersAndSetters() {
        Club club = new Club();
        club.setId(1L);
        club.setName("Club Name");
        club.setEmail("club@test.com");
        club.setClubType_id(1L);
        club.setDescription("Club description");
        club.setMission("Club mission");
        club.setContact("Club contact");
        club.setHead_id(1L);

        assertEquals(1L, club.getId());
        assertEquals("Club Name", club.getName());
        assertEquals("club@test.com", club.getEmail());
        assertEquals(1L, club.getClubType_id());
        assertEquals("Club description", club.getDescription());
        assertEquals("Club mission", club.getMission());
        assertEquals("Club contact", club.getContact());
        assertEquals(1L, club.getHead_id());
    }

    @Test
    public void testClubNoArgsConstructor() {
        Club club = new Club();

        assertNull(club.getId());
        assertNull(club.getName());
        assertNull(club.getEmail());
        assertNull(club.getClubType_id());
        assertNull(club.getDescription());
        assertNull(club.getMission());
        assertNull(club.getContact());
        assertNull(club.getHead_id());
    }

    @Test
    public void testClubEqualsAndHashCode() {
        Club club1 = new Club(1L, "Club Name", "club@test.com", 1L, "Club description", "Club mission", "Club contact", 1L);
        Club club2 = new Club(1L, "Club Name", "club@test.com", 1L, "Club description", "Club mission", "Club contact", 1L);

        assertFalse(club1.equals(club2));
        assertFalse(club1.hashCode() == club2.hashCode());
    }
}
