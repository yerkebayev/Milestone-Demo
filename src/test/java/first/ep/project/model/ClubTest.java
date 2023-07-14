package first.ep.project.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ClubTest {

    @Test
    public void testClubConstructor_WithAllFields_SetsFieldsCorrectly() {
        // Arrange
        Long id = 1L;
        String name = "Test Club";
        String email = "test@example.com";
        Long clubTypeId = 2L;
        String description = "Test description";
        String mission = "Test mission";
        String contact = "123456789";
        Long headId = 3L;
        String image = "image.jpg";

        // Act
        Club club = new Club(id, name, email, clubTypeId, description, mission, contact, headId, image);

        // Assert
        assertEquals(id, club.getId());
        assertEquals(name, club.getName());
        assertEquals(email, club.getEmail());
        assertEquals(clubTypeId, club.getClubType_id());
        assertEquals(description, club.getDescription());
        assertEquals(mission, club.getMission());
        assertEquals(contact, club.getContact());
        assertEquals(headId, club.getHead_id());
        assertEquals(image, club.getImage());
    }

    @Test
    public void testClubConstructor_WithRequiredFields_SetsFieldsCorrectly() {
        // Arrange
        String name = "Test Club";
        String email = "test@example.com";
        Long clubTypeId = 2L;
        String description = "Test description";
        String mission = "Test mission";
        String contact = "123456789";
        Long headId = 3L;
        String image = "image.jpg";

        // Act
        Club club = new Club(name, email, clubTypeId, description, mission, contact, headId, image);

        // Assert
        assertNull(club.getId());
        assertEquals(name, club.getName());
        assertEquals(email, club.getEmail());
        assertEquals(clubTypeId, club.getClubType_id());
        assertEquals(description, club.getDescription());
        assertEquals(mission, club.getMission());
        assertEquals(contact, club.getContact());
        assertEquals(headId, club.getHead_id());
        assertEquals(image, club.getImage());
    }

}
