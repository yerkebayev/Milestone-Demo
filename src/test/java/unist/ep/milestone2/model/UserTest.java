package unist.ep.milestone2.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    @Test
    public void testUserConstructor() {
        User user = new User(1L, "John", "Doe", "johndoe@test.com", "password", 1);

        assertEquals(1L, user.getId());
        assertEquals("John", user.getName());
        assertEquals("Doe", user.getSurname());
        assertEquals("johndoe@test.com", user.getEmail());
        assertEquals("password", user.getPassword());
        assertEquals(1, user.getRole());
    }

    @Test
    public void testUserGettersAndSetters() {
        User user = new User();
        user.setId(1L);
        user.setName("John");
        user.setSurname("Doe");
        user.setEmail("johndoe@test.com");
        user.setPassword("password");
        user.setRole(1);

        assertEquals(1L, user.getId());
        assertEquals("John", user.getName());
        assertEquals("Doe", user.getSurname());
        assertEquals("johndoe@test.com", user.getEmail());
        assertEquals("password", user.getPassword());
        assertEquals(1, user.getRole());
    }

    @Test
    public void testUserNoArgsConstructor() {
        User user = new User();

        assertNull(user.getId());
        assertNull(user.getName());
        assertNull(user.getSurname());
        assertNull(user.getEmail());
        assertNull(user.getPassword());
        assertNull(user.getRole());
    }

    @Test
    public void testUserEqualsAndHashCode() {
        User user1 = new User(1L, "John", "Doe", "johndoe@test.com", "password", 1);
        User user2 = new User(1L, "John", "Doe", "johndoe@test.com", "password", 1);

        assertFalse(user1.equals(user2));
        assertFalse(user1.hashCode() == user2.hashCode());
    }
}
