package unist.ep.milestone2.controller;

import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import unist.ep.milestone2.model.*;
import unist.ep.milestone2.repository.ClubRequest;
import unist.ep.milestone2.repository.ClubTypeResponse;
import unist.ep.milestone2.repository.HomeResponse;
import unist.ep.milestone2.repository.MainResponse;
import unist.ep.milestone2.service.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class IndexControllerTest {

    @Mock
    private UserService userService;
    @Mock
    private ClubService clubService;
    @Mock
    private RatingService ratingService;
    @Mock
    private TypeService typeService;
    @Mock
    private UserClubTypeService userClubTypeService;

    @Mock
    private HttpSession httpSession;

    @InjectMocks
    private IndexController indexController;
    @BeforeEach
    public void setup() {
    }

    @Test
    public void testLogin_WithValidCredentials_ReturnsUserId() {
        // Arrange
        String email = "test@example.com";
        String password = "password";
        User user = new User();
        user.setId(1L);
        user.setPassword(password);

        when(userService.getUserByEmail(email)).thenReturn(user);

        // Act
        ResponseEntity<Long> response = indexController.login(email, password, httpSession);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user.getId(), response.getBody());
        verify(httpSession).setAttribute("userId", user.getId());
    }

    @Test
    public void testLogin_WithInvalidCredentials_ReturnsUnauthorized() {
        // Arrange
        String email = "test@example.com";
        String password = "password";
        User user = null;

        when(userService.getUserByEmail(email)).thenReturn(user);

        // Act
        ResponseEntity<Long> response = indexController.login(email, password, httpSession);

        // Assert
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals(-1L, response.getBody());
    }

    @Test
    public void testLoginAdmin_WithValidAdminCredentials_ReturnsUserId() {
        // Arrange
        String email = "admin@example.com";
        String password = "password";
        User adminUser = new User();
        adminUser.setId(1L);
        adminUser.setPassword(password);
        adminUser.setRole(1);

        when(userService.getUserByEmail(email)).thenReturn(adminUser);

        // Act
        ResponseEntity<Long> response = indexController.loginAdmin(email, password, httpSession);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(adminUser.getId(), response.getBody());
        verify(httpSession).setAttribute("userId", adminUser.getId());
    }

    @Test
    public void testLoginAdmin_WithInvalidAdminCredentials_ReturnsBadRequest() {
        // Arrange
        String email = "admin@example.com";
        String password = "password";
        User nonAdminUser = new User();
        nonAdminUser.setId(1L);
        nonAdminUser.setPassword(password);
        nonAdminUser.setRole(0);

        when(userService.getUserByEmail(email)).thenReturn(nonAdminUser);

        // Act
        ResponseEntity<Long> response = indexController.loginAdmin(email, password, httpSession);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(-1L, response.getBody());
    }

    @Test
    public void testAddUser_WithValidUnistEmail_ReturnsUserCreated() {
        // Arrange
        String name = "John";
        String surname = "Doe";
        String email = "john.doe@unist.ac.kr";
        String password = "password";
        User newUser = new User(name, surname, email, password, 0);
        newUser.setId(1L);

        when(userService.getUserByEmail(email)).thenReturn(null);
        when(userService.saveUser(any(User.class))).thenReturn(newUser);

        // Act
        ResponseEntity<?> response = indexController.addUser(name, surname, email, password, httpSession);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(newUser, response.getBody());
        verify(httpSession).setAttribute("userId", newUser.getId());
    }

    @Test
    public void testAddUser_WithExistingEmail_ReturnsBadRequest() {
        // Arrange
        String name = "John";
        String surname = "Doe";
        String email = "john.doe@unist.ac.kr";
        String password = "password";
        User existingUser = new User(name, surname, email, password, 0);

        when(userService.getUserByEmail(email)).thenReturn(existingUser);

        // Act
        ResponseEntity<?> response = indexController.addUser(name, surname, email, password, httpSession);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("There is already a user with this email", response.getBody());
    }

    @Test
    public void testAddUser_WithNonUnistEmail_ReturnsBadRequest() {
        // Arrange
        String name = "John";
        String surname = "Doe";
        String email = "john.doe@gmail.com";
        String password = "password";

        // Act
        ResponseEntity<?> response = indexController.addUser(name, surname, email, password, httpSession);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("You can only register using your Unist account", response.getBody());
    }
    @Test
    public void testGetClubs_WithoutLoggedInUser_ReturnsEmptyResponse() {
        // Arrange
        when(httpSession.getAttribute("userId")).thenReturn(null);

        // Act
        HomeResponse response = indexController.getClubs(httpSession);

        // Assert
        assertEquals(0, response.getAllClubs().size());
        assertEquals(0, response.getPreferredClubs().size());
    }

    @Test
    public void testGetClubs_WithLoggedInUser_ReturnsClubsAndRecommendedClubs() {
        // Arrange
        Long userId = 1L;
        User user = new User();
        List<Club> clubs = new ArrayList<>();
        List<ClubType> clubTypes = new ArrayList<>();
        List<Club> recommendedClubs = new ArrayList<>();

        when(httpSession.getAttribute("userId")).thenReturn(userId);
        when(userService.getUserById(userId)).thenReturn(Optional.of(user));
        when(clubService.getAllClubs()).thenReturn(clubs);
        when(userService.getPreferredClubTypes(user)).thenReturn(clubTypes);
        when(clubService.getClubsByClubTypes(clubTypes)).thenReturn(recommendedClubs);

        // Act
        HomeResponse response = indexController.getClubs(httpSession);

        // Assert
        assertEquals(clubs, response.getAllClubs());
        assertEquals(recommendedClubs, response.getPreferredClubs());
    }

    @Test
    public void testGetClubPage_WithoutLoggedInUser_ReturnsNotFound() {
        // Arrange
        long clubId = 1L;
        when(httpSession.getAttribute("userId")).thenReturn(null);

        // Act
        ResponseEntity<MainResponse> response = indexController.getClubPage(clubId, httpSession);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testGetClubPage_WithInvalidClubId_ReturnsNotFound() {
        // Arrange
        long clubId = 1L;
        Long userId = 1L;
        when(httpSession.getAttribute("userId")).thenReturn(userId);
        when(clubService.getClubById(clubId)).thenReturn(Optional.empty());
        when(userService.getUserById(userId)).thenReturn(Optional.of(new User()));

        // Act
        ResponseEntity<MainResponse> response = indexController.getClubPage(clubId, httpSession);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testGetClubPage_WithValidData_ReturnsClubPage() {
        // Arrange
        long clubId = 1L;
        Long userId = 1L;
        Club club = new Club();
        User user = new User();
        List<ClubType> clubTypes = new ArrayList<>();
        List<Club> recommendedClubs = new ArrayList<>();
        List<Rating> ratings = new ArrayList<>();
        Double averageRating = 4.5;

        when(httpSession.getAttribute("userId")).thenReturn(userId);
        when(clubService.getClubById(clubId)).thenReturn(Optional.of(club));
        when(userService.getUserById(userId)).thenReturn(Optional.of(user));
        when(typeService.getClubTypeById(club.getClubType_id())).thenReturn(Optional.of(new ClubType()));
        when(clubService.getClubsByClubTypes(anyList())).thenReturn(recommendedClubs);
        when(ratingService.getRatingsByClubId(clubId)).thenReturn(ratings);
        when(ratingService.getAverageRatingByClubId(clubId)).thenReturn(averageRating);

        // Act
        ResponseEntity<MainResponse> response = indexController.getClubPage(clubId, httpSession);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testGetUserById_WithInvalidId_ReturnsNull() {
        // Arrange
        long userId = 1L;
        when(userService.getUserById(userId)).thenReturn(Optional.empty());

        // Act
        User result = indexController.getUserById(userId);

        // Assert
        assertNull(result);
    }

    @Test
    public void testGetUserById_WithValidId_ReturnsUser() {
        // Arrange
        long userId = 1L;
        User user = new User();
        when(userService.getUserById(userId)).thenReturn(Optional.of(user));

        // Act
        User result = indexController.getUserById(userId);

        // Assert
        assertEquals(user, result);
    }
    @Test
    public void testGetRatingById_WithInvalidId_ReturnsNull() {
        // Arrange
        long ratingId = 1L;
        when(ratingService.getRatingById(ratingId)).thenReturn(Optional.empty());

        // Act
        Rating result = indexController.getRatingById(ratingId);

        // Assert
        assertNull(result);
    }

    @Test
    public void testGetRatingById_WithValidId_ReturnsRating() {
        // Arrange
        long ratingId = 1L;
        Rating rating = new Rating();
        when(ratingService.getRatingById(ratingId)).thenReturn(Optional.of(rating));

        // Act
        Rating result = indexController.getRatingById(ratingId);

        // Assert
        assertEquals(rating, result);
    }

    @Test
    public void testGetAllClubTypes_WithoutLoggedInUser_ReturnsEmptyResponse() {
        // Arrange
        when(httpSession.getAttribute("userId")).thenReturn(null);

        // Act
        ClubTypeResponse response = indexController.getAllClubTypes(httpSession);

        // Assert
        assertEquals(0, response.getClubTypes().size());
        assertEquals(0, response.getPreferredClubTypes().size());
    }

    @Test
    public void testGetAllClubTypes_WithLoggedInUser_ReturnsClubTypesAndPreferredClubTypes() {
        // Arrange
        Long userId = 1L;
        User user = new User();
        List<ClubType> clubTypes = new ArrayList<>();
        List<Integer> preferredClubTypes = new ArrayList<>();

        when(httpSession.getAttribute("userId")).thenReturn(userId);
        when(userService.getUserById(userId)).thenReturn(Optional.of(user));
        when(typeService.getAllClubTypes()).thenReturn(clubTypes);
        when(userService.getPreferredClubTypesInInteger(user)).thenReturn(preferredClubTypes);

        // Act
        ClubTypeResponse response = indexController.getAllClubTypes(httpSession);

        // Assert
        assertEquals(clubTypes, response.getClubTypes());
        assertEquals(preferredClubTypes, response.getPreferredClubTypes());
    }

    @Test
    public void testGetAllClubTypesForAdmin_ReturnsClubTypesWithoutPreferredClubTypes() {
        // Arrange
        List<ClubType> clubTypes = new ArrayList<>();

        when(typeService.getAllClubTypes()).thenReturn(clubTypes);

        // Act
        ClubTypeResponse response = indexController.getAllClubTypes();

        // Assert
        assertEquals(clubTypes, response.getClubTypes());
        assertNull(response.getPreferredClubTypes());
    }

    @Test
    public void testAddClubTypes_WithoutLoggedInUser_ReturnsNegativeResult() {
        // Arrange
        String clubTypes = "1_2_3";
        when(httpSession.getAttribute("userId")).thenReturn(null);

        // Act
        Long result = indexController.addClubTypes(clubTypes, httpSession);

        // Assert
        assertEquals(-1L, result);
    }

    @Test
    public void testAddClubTypes_WithLoggedInUser_ReturnsPositiveResult() {
        // Arrange
        String clubTypes = "1_2_3";
        Long userId = 1L;
        User user = new User();
        when(httpSession.getAttribute("userId")).thenReturn(userId);
        when(userService.getUserById(userId)).thenReturn(Optional.of(user));

        // Act
        Long result = indexController.addClubTypes(clubTypes, httpSession);

        // Assert
        assertEquals(1L, result);
    }

    @Test
    public void testAddRating_WithoutLoggedInUser_ReturnsNotFoundResponse() {
        // Arrange
        long clubId = 1L;
        Integer rating = 5;
        String comment = "Great club";

        when(httpSession.getAttribute("userId")).thenReturn(null);

        // Act
        ResponseEntity<Rating> response = indexController.addRating(clubId, comment, rating, httpSession);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testAddRating_WithInvalidUserOrClub_ReturnsNullResponse() {
        // Arrange
        long clubId = 1L;
        Integer rating = 5;
        String comment = "Great club";
        Long userId = 1L;
        when(httpSession.getAttribute("userId")).thenReturn(userId);
        when(userService.getUserById(userId)).thenReturn(Optional.empty());
        when(clubService.getClubById(clubId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Rating> response = indexController.addRating(clubId, comment, rating, httpSession);

        // Assert
        assertNull(response);
    }

    @Test
    public void testAddRating_WithValidData_ReturnsCreatedResponseWithRating() {
        // Arrange
        long clubId = 1L;
        Integer rating = 5;
        String comment = "Great club";
        Long userId = 1L;
        User user = new User();
        Club club = new Club();

        when(httpSession.getAttribute("userId")).thenReturn(userId);
        when(userService.getUserById(userId)).thenReturn(Optional.of(user));
        when(clubService.getClubById(clubId)).thenReturn(Optional.of(club));

        // Act
        ResponseEntity<Rating> response = indexController.addRating(clubId, comment, rating, httpSession);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }
    @Test
    public void testGetAverageRatingOfClub_WithInvalidClubId_ReturnsNotFoundResponse() {
        // Arrange
        long clubId = 1L;
        when(clubService.getClubById(clubId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Double> response = indexController.getAverageRatingOfClub(clubId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testGetAverageRatingOfClub_WithValidClubId_ReturnsAverageRating() {
        // Arrange
        long clubId = 1L;
        double averageRating = 4.5;
        when(clubService.getClubById(clubId)).thenReturn(Optional.of(new Club()));
        when(ratingService.getAverageRatingByClubId(clubId)).thenReturn(averageRating);

        // Act
        ResponseEntity<Double> response = indexController.getAverageRatingOfClub(clubId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(averageRating, response.getBody());
    }

    @Test
    public void testGetClubsForAdmin_ReturnsListOfClubs() {
        // Arrange
        List<Club> clubs = new ArrayList<>();
        when(clubService.getAllClubs()).thenReturn(clubs);

        // Act
        ResponseEntity<List<Club>> response = indexController.getClubsForAdmin();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(clubs, response.getBody());
    }

    @Test
    public void testGetClubPageForAdmin_WithInvalidClubId_ReturnsNotFoundResponse() {
        // Arrange
        long clubId = 1L;
        when(clubService.getClubById(clubId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<ClubRequest> response = indexController.getClubPageForAdmin(clubId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testGetClubPageForAdmin_WithValidClubId_ReturnsClubRequest() {
        // Arrange
        long clubId = 1L;
        Club club = new Club();
        club.setId(clubId);
        club.setName("Test Club");
        club.setClubType_id(1L);
        club.setEmail("test@example.com");
        club.setMission("Test mission");
        club.setDescription("Test description");
        club.setHead_id(1L);
        club.setContact("123456789");
        club.setImage("image.jpg");

        User head = new User();
        head.setId(1L);
        head.setName("John");
        head.setSurname("Doe");

        when(clubService.getClubById(clubId)).thenReturn(Optional.of(club));
        when(userService.getUserById(club.getHead_id())).thenReturn(Optional.of(head));

        // Act
        ResponseEntity<ClubRequest> response = indexController.getClubPageForAdmin(clubId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testAddClub_WithMissingClubType_ReturnsClubWithDefaultClubType() {
        // Arrange
        String name = "Test Club";
        Long clubType = null;
        String headEmail = "john.doe@example.com";
        String email = "test@example.com";
        String description = "Test description";
        String mission = "Test mission";
        String contact = "123456789";
        String image = "image.jpg";

        when(userService.getUserByEmail(headEmail)).thenReturn(new User());
        when(clubService.getClubByEmail(email)).thenReturn(null);

        // Act
        ResponseEntity<Club> response = indexController.addClub(name, clubType, headEmail, email, description, mission, contact, image);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    // Add more tests for different scenarios of adding a club

    @Test
    public void testDeleteClub_WithInvalidClubId_ReturnsNotFoundResponse() {
        // Arrange
        long clubId = 1L;
        when(clubService.deleteClubById(clubId)).thenReturn(0L);

        // Act
        ResponseEntity<Long> response = indexController.deleteClub(clubId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(-1L, response.getBody());
    }

    @Test
    public void testDeleteClub_WithValidClubId_ReturnsOkResponse() {
        // Arrange
        long clubId = 1L;
        when(clubService.deleteClubById(clubId)).thenReturn(1L);

        // Act
        ResponseEntity<Long> response = indexController.deleteClub(clubId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1L, response.getBody());
    }
}
