package unist.ep.milestone2.controller;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpSession;
import unist.ep.milestone2.model.Club;
import unist.ep.milestone2.model.User;
import unist.ep.milestone2.service.ClubService;
import unist.ep.milestone2.service.UserService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AdminControllerTest {

    @Mock
    private UserService userService;
    @Mock
    private ClubService clubService;

    @InjectMocks
    private AdminController adminController;

    private MockHttpSession session;

    @BeforeEach
    public void setUp() {
        session = new MockHttpSession();
    }


    @Test
    public void testLoginWithValidCredentials() {
        String email = "yerkebayev@unist.ac.kr";
        String password = "123hey";
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setRole(1);
        when(userService.getUserByEmail(email)).thenReturn(user);

        ResponseEntity<String> response = adminController.adminLogin(email, password, session);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Logged in successfully", response.getBody());
        assertEquals(user, session.getAttribute("user"));
    }

    @Test
    public void testAdminLoginIncorrectEmailOrPassword() {
        String email = "admin@example.com";
        String password = "password";
        when(userService.getUserByEmail(email)).thenReturn(null);

        ResponseEntity<String> response = adminController.adminLogin(email, password, session);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Incorrect email or password", response.getBody());
    }

    @Test
    public void testGetClubsForAdmin() {
        // Arrange
        Club club1 = new Club();
        club1.setName("Club 1");
        Club club2 = new Club();
        club2.setName("Club 2");
        List<Club> clubs = Arrays.asList(club1, club2);
        when(clubService.getAllClubs()).thenReturn(clubs);

        ResponseEntity<List<Club>> response = adminController.getClubsForAdmin();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(clubs, response.getBody());
    }
    @Test
    public void testGetClubsForAdminNoClubs() {
        List<Club> clubs = new ArrayList<>();
        when(clubService.getAllClubs()).thenReturn(clubs);

        ResponseEntity<List<Club>> response = adminController.getClubsForAdmin();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(clubs, response.getBody());
    }

    @Test
    public void testAddClub() {
        Club club = new Club();
        club.setName("New Club");
        Club savedClub = new Club();
        savedClub.setId(1L);
        savedClub.setName("New Club");
        when(clubService.saveClub(club)).thenReturn(savedClub);

        ResponseEntity<Club> response = adminController.addClub(club);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(savedClub, response.getBody());
    }
    @Test
    public void testAddClubNullName() {
        ResponseEntity<Club> response = adminController.addClub(new Club());

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testUpdateClubSuccess() {
        long id = 1L;
        Club club = new Club();
        club.setName("New Club");
        club.setId(id);
        Optional<Club> optionalClub = Optional.of(club);
        when(clubService.getClubById(id)).thenReturn(optionalClub);
        when(clubService.saveClub(any(Club.class))).thenReturn(club);

        ResponseEntity<Club> response = adminController.updateClub(id, club);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(club, response.getBody());
    }

    @Test
    public void testUpdateClubNotFound() {
        // Arrange
        long id = 1L;
        Club club = new Club();
        club.setName("New Club");
        club.setId(id);
        Optional<Club> optionalClub = Optional.empty();
        when(clubService.getClubById(id)).thenReturn(optionalClub);

        ResponseEntity<Club> response = adminController.updateClub(id, club);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testDeleteClubSuccess() {
        long id = 1L;
        when(clubService.deleteClubById(id)).thenReturn(id);

        ResponseEntity<String> response = adminController.deleteClub(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Deleted...", response.getBody());
    }

    @Test
    public void testDeleteClubNotFound() {
        long id = 1L;
        when(clubService.deleteClubById(id)).thenReturn(-1L);

        ResponseEntity<String> response = adminController.deleteClub(id);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Club not found.", response.getBody());
    }
}