package first.ep.project.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;
import first.ep.project.model.Club;
import first.ep.project.model.ClubType;
import first.ep.project.repository.ClubRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClubServiceImplTest {

    @Mock
    private ClubRepository clubRepository;

    @InjectMocks
    private ClubServiceImpl clubService;

    @Test
    public void testGetAllClubs_ReturnsListOfClubs() {
        // Arrange
        List<Club> expectedClubs = new ArrayList<>();
        expectedClubs.add(new Club(1L, "Club 1", "club1@example.com", 1L, "Description 1", "Mission 1", "Contact 1", 1L, "image1.jpg"));
        expectedClubs.add(new Club(2L, "Club 2", "club2@example.com", 2L, "Description 2", "Mission 2", "Contact 2", 2L, "image2.jpg"));

        when(clubRepository.findAll(any(Sort.class))).thenReturn(expectedClubs);

        // Act
        List<Club> actualClubs = clubService.getAllClubs();

        // Assert
        assertEquals(expectedClubs, actualClubs);
        verify(clubRepository, times(1)).findAll(any(Sort.class));
    }

    @Test
    public void testGetClubById_WithExistingId_ReturnsClubOptional() {
        // Arrange
        Long clubId = 1L;
        Club expectedClub = new Club(1L, "Club 1", "club1@example.com", 1L, "Description 1", "Mission 1", "Contact 1", 1L, "image1.jpg");

        when(clubRepository.findById(clubId)).thenReturn(Optional.of(expectedClub));

        // Act
        Optional<Club> actualClub = clubService.getClubById(clubId);

        // Assert
        assertEquals(Optional.of(expectedClub), actualClub);
        verify(clubRepository, times(1)).findById(clubId);
    }

    @Test
    public void testGetClubById_WithNonExistingId_ReturnsEmptyOptional() {
        // Arrange
        Long clubId = 1L;

        when(clubRepository.findById(clubId)).thenReturn(Optional.empty());

        // Act
        Optional<Club> actualClub = clubService.getClubById(clubId);

        // Assert
        assertEquals(Optional.empty(), actualClub);
        verify(clubRepository, times(1)).findById(clubId);
    }

    @Test
    public void testGetClubByEmail_WithExistingEmail_ReturnsClub() {
        // Arrange
        String email = "club1@example.com";
        Club expectedClub = new Club(1L, "Club 1", "club1@example.com", 1L, "Description 1", "Mission 1", "Contact 1", 1L, "image1.jpg");

        when(clubRepository.getClubByEmail(email)).thenReturn(expectedClub);

        // Act
        Club actualClub = clubService.getClubByEmail(email);

        // Assert
        assertEquals(expectedClub, actualClub);
        verify(clubRepository, times(1)).getClubByEmail(email);
    }

    @Test
    public void testSaveClub_ReturnsSavedClub() {
        // Arrange
        Club clubToSave = new Club("Club 1", "club1@example.com", 1L, "Description 1", "Mission 1", "Contact 1", 1L, "image1.jpg");
        Club expectedClub = new Club(1L, "Club 1", "club1@example.com", 1L, "Description 1", "Mission 1", "Contact 1", 1L, "image1.jpg");

        when(clubRepository.save(clubToSave)).thenReturn(expectedClub);

        // Act
        Club savedClub = clubService.saveClub(clubToSave);

        // Assert
        assertEquals(expectedClub, savedClub);
        verify(clubRepository, times(1)).save(clubToSave);
    }

    @Test
    public void testDeleteClubById_WithExistingId_ReturnsSuccessIndicator() {
        // Arrange
        Long clubId = 1L;

        when(clubRepository.existsById(clubId)).thenReturn(true);

        // Act
        Long result = clubService.deleteClubById(clubId);

        // Assert
        assertEquals(1L, result);
        verify(clubRepository, times(1)).existsById(clubId);
        verify(clubRepository, times(1)).deleteById(clubId);
    }

    @Test
    public void testDeleteClubById_WithNonExistingId_ReturnsFailureIndicator() {
        // Arrange
        Long clubId = 1L;

        when(clubRepository.existsById(clubId)).thenReturn(false);

        // Act
        Long result = clubService.deleteClubById(clubId);

        // Assert
        assertEquals(-1L, result);
        verify(clubRepository, times(1)).existsById(clubId);
        verify(clubRepository, never()).deleteById(clubId);
    }

    @Test
    public void testGetClubsByClubTypes_ReturnsListOfClubs() {
        // Arrange
        List<ClubType> clubTypes = new ArrayList<>();
        clubTypes.add(new ClubType(1L, "Type 1"));
        clubTypes.add(new ClubType(2L, "Type 2"));

        List<Club> expectedClubs = new ArrayList<>();
        expectedClubs.add(new Club(1L, "Club 1", "club1@example.com", 1L, "Description 1", "Mission 1", "Contact 1", 1L, "image1.jpg"));
        expectedClubs.add(new Club(2L, "Club 2", "club2@example.com", 2L, "Description 2", "Mission 2", "Contact 2", 2L, "image2.jpg"));

        when(clubRepository.getClubsByClubTypes(1L)).thenReturn(expectedClubs.subList(0, 1));
        when(clubRepository.getClubsByClubTypes(2L)).thenReturn(expectedClubs.subList(1, 2));

        // Act
        List<Club> actualClubs = clubService.getClubsByClubTypes(clubTypes);

        // Assert
        assertEquals(expectedClubs, actualClubs);
        verify(clubRepository, times(1)).getClubsByClubTypes(1L);
        verify(clubRepository, times(1)).getClubsByClubTypes(2L);
    }

}
