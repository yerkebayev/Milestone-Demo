package unist.ep.milestone2.model;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ClubTypeTest {
    @Test
    public void testClubTypeConstructor() {
        ClubType clubType = new ClubType(1L, "Type Name");

        assertEquals(1L, clubType.getId());
        assertEquals("Type Name", clubType.getName());
    }

    @Test
    public void testClubTypeGettersAndSetters() {
        ClubType clubType = new ClubType();
        clubType.setId(1L);
        clubType.setName("Type Name");

        assertEquals(1L, clubType.getId());
        assertEquals("Type Name", clubType.getName());
    }

    @Test
    public void testClubTypeNoArgsConstructor() {
        ClubType clubType = new ClubType();

        assertNull(clubType.getId());
        assertNull(clubType.getName());
    }

    @Test
    public void testClubTypeEqualsAndHashCode() {
        ClubType clubType1 = new ClubType(1L, "Type Name");
        ClubType clubType2 = new ClubType(1L, "Type Name");

        assertFalse(clubType1.equals(clubType2));
        assertFalse(clubType1.hashCode() == clubType2.hashCode());
    }

}