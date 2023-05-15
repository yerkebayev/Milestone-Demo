package unist.ep.milestone2.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import unist.ep.milestone2.model.Club;
import unist.ep.milestone2.model.ClubType;
import unist.ep.milestone2.repository.ClubRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClubTypeServiceImplTest {

    @Mock
    private ClubRepository clubRepository;

    @InjectMocks
    private ClubServiceImpl clubService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllClubs() {
        List<Club> expected = new ArrayList<>();
        Mockito.when(clubRepository.findAll()).thenReturn(expected);

        List<Club> actual = clubService.getAllClubs();

        Assertions.assertEquals(expected, actual);
        Mockito.verify(clubRepository).findAll();
    }

    @Test
    public void testGetClubById() {
        Long id = 1L;
        Club expected = new Club(id, "Test Club", "testclub@example.com", 2L, "Test description", "Test mission", "Test contact", 3L);
        Mockito.when(clubRepository.findById(id)).thenReturn(Optional.of(expected));

        Optional<Club> actual = clubService.getClubById(id);

        Assertions.assertEquals(expected, actual.get());
        Mockito.verify(clubRepository).findById(id);
    }

    @Test
    public void testSaveClub() {
        Long id = 1L;
        Club club = new Club(id, "Test Club", "testclub@example.com", 2L, "Test description", "Test mission", "Test contact", 3L);
        Mockito.when(clubRepository.save(club)).thenReturn(club);

        Club actual = clubService.saveClub(club);

        Assertions.assertEquals(club, actual);
        Mockito.verify(clubRepository).save(club);
    }

    @Test
    public void testDeleteClubByIdIfExists() {
        Long id = 1L;
        Mockito.when(clubRepository.existsById(id)).thenReturn(true);

        Long actual = clubService.deleteClubById(id);

        Assertions.assertEquals(1L, actual);
        Mockito.verify(clubRepository).existsById(id);
        Mockito.verify(clubRepository).deleteById(id);
    }

    @Test
    public void testDeleteClubByIdIfNotExists() {
        Long id = 1L;
        Mockito.when(clubRepository.existsById(id)).thenReturn(false);

        Long actual = clubService.deleteClubById(id);

        Assertions.assertEquals(-1L, actual);
        Mockito.verify(clubRepository).existsById(id);
        Mockito.verify(clubRepository, Mockito.never()).deleteById(id);
    }

    @Test
    public void testGetClubsByClubTypes() {
        List<ClubType> clubTypes = new ArrayList<>();
        clubTypes.add(new ClubType(2L, "Test Club Type"));
        List<Club> expected = new ArrayList<>();
        expected.add(new Club(1L, "Test Club", "testclub@example.com", 2L, "Test description", "Test mission", "Test contact", 3L));
        Mockito.when(clubRepository.getClubsByClubTypes(2L)).thenReturn(expected);

        List<Club> actual = clubService.getClubsByClubTypes(clubTypes);
        Assertions.assertEquals(expected, actual);
        Mockito.verify(clubRepository, Mockito.times(1)).getClubsByClubTypes(2L);
    }
}