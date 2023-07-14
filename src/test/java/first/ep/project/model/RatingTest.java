package first.ep.project.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RatingTest {

    @Test
    public void testRatingConstructor() {
        Rating rating = new Rating(1L, 1L, 4, "Good club");

        assertNull(rating.getId());
        assertEquals(1L, rating.getUser_id());
        assertEquals(1L, rating.getClub_id());
        assertEquals(4, rating.getValue());
        assertEquals("Good club", rating.getComment());
    }

    @Test
    public void testRatingGettersAndSetters() {
        Rating rating = new Rating();
        rating.setId(1L);
        rating.setUser_id(1L);
        rating.setClub_id(1L);
        rating.setValue(4);
        rating.setComment("Good club");

        assertEquals(1L, rating.getId());
        assertEquals(1L, rating.getUser_id());
        assertEquals(1L, rating.getClub_id());
        assertEquals(4, rating.getValue());
        assertEquals("Good club", rating.getComment());
    }

    @Test
    public void testRatingNoArgsConstructor() {
        Rating rating = new Rating();

        assertNull(rating.getId());
        assertNull(rating.getUser_id());
        assertNull(rating.getClub_id());
        assertNull(rating.getValue());
        assertNull(rating.getComment());
    }

    @Test
    public void testRatingEqualsAndHashCode() {
        Rating rating1 = new Rating(1L, 1L, 4, "Good club");
        Rating rating2 = new Rating(1L, 1L, 4, "Good club");

        assertFalse(rating1.equals(rating2));
        assertFalse(rating1.hashCode() == rating2.hashCode());
    }
}
