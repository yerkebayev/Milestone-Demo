package first.ep.project.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserClubTypeTest {
    @Test
    public void testUserClubTypeConstructor() {
        UserClubType userClubType = new UserClubType(1L, 2L);

        assertNull(userClubType.getId());
        assertEquals(1L, userClubType.getUser_id());
        assertEquals(2L, userClubType.getClubType_id());
    }

    @Test
    public void testUserClubTypeGettersAndSetters() {
        UserClubType userClubType = new UserClubType();
        userClubType.setId(1L);
        userClubType.setUser_id(2L);
        userClubType.setClubType_id(3L);

        assertEquals(1L, userClubType.getId());
        assertEquals(2L, userClubType.getUser_id());
        assertEquals(3L, userClubType.getClubType_id());
    }

    @Test
    public void testUserClubTypeNoArgsConstructor() {
        UserClubType userClubType = new UserClubType();

        assertNull(userClubType.getId());
        assertNull(userClubType.getUser_id());
        assertNull(userClubType.getClubType_id());
    }

    @Test
    public void testUserClubTypeEqualsAndHashCode() {
        UserClubType userClubType1 = new UserClubType(1L, 2L);
        UserClubType userClubType2 = new UserClubType(1L, 2L);

        assertFalse(userClubType1.equals(userClubType2));
        assertFalse(userClubType1.hashCode() == userClubType2.hashCode());
    }

}