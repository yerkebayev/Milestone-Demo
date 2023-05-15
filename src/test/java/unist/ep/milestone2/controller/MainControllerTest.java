package unist.ep.milestone2.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import unist.ep.milestone2.model.*;
import unist.ep.milestone2.repository.HomeResponse;
import unist.ep.milestone2.repository.MainResponse;
import unist.ep.milestone2.service.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MainControllerTest {

    @Mock
    private UserService userService;
    @Mock
    private ClubService clubService;
    @Mock
    private TypeService typeService;
    @Mock
    private UserClubTypeService userClubTypeService;

    @Mock
    private RatingService ratingService;


    @InjectMocks
    private MainController mainController;

    private User testUser;

    private ClubType testClubType;

    private List<ClubType> testClubTypes;

    private Club testClub;
    private List<Club> testClubs;
    private Rating testRating;
    private MainController.RatingData testRatingData;


    @BeforeEach
    public void setUp() {
        testUser = new User();
        testUser.setId(1L);

        testClubType = new ClubType();
        testClubType.setId(1L);
        testClubType.setName("Sport");

        testClubTypes = List.of(testClubType);

        testClub = new Club();
        testClub.setId(1L);
        testClub.setName("Strikers");
        testClub.setClubType_id(testClubType.getId());

        testClubs = List.of(testClub);

        testRating = new Rating();
        testRating.setId(1L);
        testRating.setClub_id(testClub.getId());
        testRating.setUser_id(testUser.getId());
        testRating.setValue(4);
        testRating.setComment("Good club!");

        testRatingData = new MainController.RatingData(4, "Good club!");
    }

    @Test
    void testAddClubTypesWithExistingTypes() {
        List<Integer> clubTypesIds = new ArrayList<>();
        for(ClubType ct : testClubTypes) {
            clubTypesIds.add(Math.toIntExact(ct.getId()));
        }
        when(userService.getUserById(testUser.getId())).thenReturn(Optional.of(testUser));
        when(userService.getPreferredClubTypes(testUser)).thenReturn(testClubTypes);

        ResponseEntity<String> response = mainController.addClubTypes(testUser.getId(), clubTypesIds);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Club types added successfully", response.getBody());
    }

    @Test
    public void testAddClubTypesWithInvalidUser() {
        List<Integer> clubTypesIds = new ArrayList<>();
        for(ClubType ct : testClubTypes) {
            clubTypesIds.add(Math.toIntExact(ct.getId()));
        }
        when(userService.getUserById(1L)).thenReturn(Optional.empty());

        ResponseEntity<String> response = mainController.addClubTypes(testUser.getId(), clubTypesIds);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("User not authenticated", response.getBody());
    }

    @Test
    public void testGetRecommendedClubsByUsersPrefer() {
        when(userService.getUserById(1L)).thenReturn(Optional.of(testUser));
        when(userService.getPreferredClubTypes(testUser)).thenReturn(testClubTypes);
        when(clubService.getClubsByClubTypes(testClubTypes)).thenReturn(List.of(testClub));

        ResponseEntity<List<Club>> response = mainController.getRecommendedClubsByUsersPrefer(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testClub, Objects.requireNonNull(response.getBody()).get(0));
    }

    @Test
    public void testGetRecommendedClubsByUsersPreferWithInvalidUser() {
        when(userService.getUserById(1L)).thenReturn(Optional.empty());

        ResponseEntity<List<Club>> response = mainController.getRecommendedClubsByUsersPrefer(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(0, Objects.requireNonNull(response.getBody()).size());
    }

    @Test
    public void testGetRecommendedClubsByClubType() {
        when(clubService.getClubById(1L)).thenReturn(Optional.of(testClub));
        when(userService.getUserById(1L)).thenReturn(Optional.of(testUser));
        when(typeService.getClubTypeById(testClub.getClubType_id())).thenReturn(Optional.of(testClubType));
        when(clubService.getClubsByClubTypes(List.of(testClubType))).thenReturn(List.of(testClub));

        ResponseEntity<List<Club>> response = mainController.getRecommendedClubsByClubType(1L, 1L);


        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, Objects.requireNonNull(response.getBody()).size());
    }
    @Test
    public void testGetRecommendedClubsByClubTypeWithMissingClubType() {
        when(clubService.getClubById(1L)).thenReturn(Optional.of(testClub));
        when(userService.getUserById(1L)).thenReturn(Optional.of(testUser));
        when(typeService.getClubTypeById(testClub.getClubType_id())).thenReturn(Optional.empty()); // clubType not found

        ResponseEntity<List<Club>> response = mainController.getRecommendedClubsByClubType(1L, 1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertTrue(Objects.requireNonNull(response.getBody()).isEmpty());
    }

    @Test
    public void testGetRecommendedClubsByClubTypeWithInvalidClubId() {
        when(clubService.getClubById(1L)).thenReturn(Optional.empty());

        ResponseEntity<List<Club>> response = mainController.getRecommendedClubsByClubType(1L, 1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(0, Objects.requireNonNull(response.getBody()).size());
    }


    @Test
    public void testGetClubsWithValidUser() {
        when(userService.getUserById(testUser.getId())).thenReturn(Optional.of(testUser));
        when(clubService.getAllClubs()).thenReturn(testClubs);
        when(userService.getPreferredClubTypes(testUser)).thenReturn(testClubTypes);
        List<Club> recommendedClubs = new ArrayList<>();
        Club recommendedClub1 = new Club();
        recommendedClub1.setId(3L);
        Club recommendedClub2 = new Club();
        recommendedClub2.setId(4L);
        recommendedClubs.add(recommendedClub1);
        recommendedClubs.add(recommendedClub2);
        when(clubService.getClubsByClubTypes(testClubTypes)).thenReturn(recommendedClubs);

        ResponseEntity<HomeResponse> response = mainController.getClubs(testUser.getId());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        HomeResponse homeResponse = response.getBody();
        assertNotNull(homeResponse);
        assertEquals(testClubs, homeResponse.getAllClubs());
        assertEquals(recommendedClubs, homeResponse.getPreferredClubs());
    }

    @Test
    public void testGetClubsWithInvalidUser() {
        when(userService.getUserById(testUser.getId())).thenReturn(Optional.empty());

        ResponseEntity<HomeResponse> response = mainController.getClubs(testUser.getId());

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        HomeResponse homeResponse = response.getBody();
        assertNotNull(homeResponse);
        assertEquals(new ArrayList<>(), homeResponse.getAllClubs());
        assertEquals(new ArrayList<>(), homeResponse.getPreferredClubs());
    }

    @Test
    public void testGetClubPage() {
        List<Club> recommendedClubs = new ArrayList<>();
        List<Rating> ratings = new ArrayList<>();
        double averageRating = 4.5;
        when(clubService.getClubById(testClub.getId())).thenReturn(Optional.of(testClub));
        when(userService.getUserById(testUser.getId())).thenReturn(Optional.of(testUser));
        when(typeService.getClubTypeById(testClub.getClubType_id())).thenReturn(Optional.of(testClubType));
        when(clubService.getClubsByClubTypes(testClubTypes)).thenReturn(recommendedClubs);
        when(ratingService.getRatingsByClubId(testClub.getId())).thenReturn(ratings);
        when(ratingService.getAverageRatingByClubId(testClub.getId())).thenReturn(averageRating);

        ResponseEntity<MainResponse> response = mainController.getClubPage(testClub.getId(), testUser.getId());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(testClub, response.getBody().getClub());
        assertEquals(testUser, response.getBody().getUser());
        assertEquals(ratings, response.getBody().getRatings());
        assertEquals(averageRating, response.getBody().getRatingAverage());
        assertEquals(recommendedClubs, response.getBody().getRecommendedClubs());
    }

    @Test
    public void testGetClubPageNotFound() {
        long nonExistentClubId = 1234L;
        when(clubService.getClubById(nonExistentClubId)).thenReturn(Optional.empty());
        when(userService.getUserById(testUser.getId())).thenReturn(Optional.of(new User()));

        ResponseEntity<MainResponse> responseEntity = mainController.getClubPage(nonExistentClubId, testUser.getId());

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void testGetClubPageWhenClubTypeNotFound() {
        when(clubService.getClubById(testClub.getId())).thenReturn(Optional.of(testClub));
        when(userService.getUserById(testUser.getId())).thenReturn(Optional.of(testUser));
        when(typeService.getClubTypeById(testClub.getClubType_id())).thenReturn(Optional.empty());

        ResponseEntity<MainResponse> responseEntity = mainController.getClubPage(testClub.getId(), testUser.getId());

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
    }

    @Test
    public void testGetAverageRatingOfClub() {
        when(clubService.getClubById(anyLong())).thenReturn(Optional.of(testClub));
        when(userService.getUserById(anyLong())).thenReturn(Optional.of(testUser));
        when(ratingService.getAverageRatingByClubId(anyLong())).thenReturn(4.0);

        ResponseEntity<Double> response = mainController.getAverageRatingOfClub(testClub.getId(), testUser.getId());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(4.0, response.getBody());
    }

    @Test
    void testGetAverageRatingOfClubWithInvalidClubId() {
        long invalidClubId = 1234L;
        when(clubService.getClubById(invalidClubId)).thenReturn(Optional.empty());

        ResponseEntity<Double> response = mainController.getAverageRatingOfClub(invalidClubId, testUser.getId());

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }


    @Test
    public void testGetRatingsAndCommentsOfClub() {
        List<Rating> expectedRatings = List.of(testRating);
        when(clubService.getClubById(testClub.getId())).thenReturn(Optional.of(testClub));
        when(userService.getUserById(testUser.getId())).thenReturn(Optional.of(testUser));
        when(ratingService.getRatingsByClubId(testClub.getId())).thenReturn(expectedRatings);
        ResponseEntity<List<Rating>> responseEntity = mainController.getRatingsAndCommentsOfClub(testClub.getId(), testUser.getId());

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedRatings, Objects.requireNonNull(responseEntity.getBody()));
    }

    @Test
    void testGetRatingAndCommentOfClubWithInvalidClubId() {
        when(userService.getUserById(testUser.getId())).thenReturn(Optional.of(testUser));
        when(clubService.getClubById(testClub.getId())).thenReturn(Optional.empty());

        ResponseEntity<List<Rating>> response = mainController.getRatingsAndCommentsOfClub(testClub.getId(), testUser.getId());

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(new ArrayList<>(), response.getBody());
    }





//    @Test
//    void testAddRatingWithValidInput() {
//        when(userService.getUserById(testUser.getId())).thenReturn(Optional.of(testUser));
//        when(clubService.getClubById(testClub.getId())).thenReturn(Optional.of(testClub));
//        Rating ratingForAdd = new Rating(testUser.getId(), testClub.getId(), 4, "Good club!");
//        when(ratingService.saveRating(ratingForAdd)).thenReturn(ratingForAdd);
//
//        ResponseEntity<Rating> responseEntity = mainController.addRating(testClub.getId(), testUser.getId(), testRatingData);
//
//        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
////        assertEquals(testRating, responseEntity.getBody());
//    }
@Test
public void testAddRatingWithInvalidUser() {
    long userId = 1L;
    long clubId = 2L;
    MainController.RatingData ratingData = new MainController.RatingData(4, "Good club!");
    when(userService.getUserById(userId)).thenReturn(Optional.empty());

    ResponseEntity<Rating> responseEntity = mainController.addRating(clubId, userId, ratingData);

    assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    assertNotNull(responseEntity.getBody());
}
//    @Test
//    public void testEditRatingClubNotFound() {
//        when(clubService.getClubById(1L)).thenReturn(Optional.empty());
//        MainController.RatingData ratingData = new MainController.RatingData();
//        ratingData.setRating(4);
//        ratingData.setComment("Great club!");
//
//        ResponseEntity<Rating> response = mainController.editRating(1L, 1L, ratingData, 1L);
//
//        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
//    }

    @Test
    public void testEditRatingBadRequest() {
        Rating rating = new Rating(testUser.getId(), testClub.getId(), 4, "Great club!");
        when(clubService.getClubById(1L)).thenReturn(Optional.of(testClub));
        when(ratingService.getRatingById(1L)).thenReturn(Optional.of(rating));

        MainController.RatingData ratingData = new MainController.RatingData();
        ratingData.setRating(5);
        ratingData.setComment("Excellent club!");

        ResponseEntity<Rating> response = mainController.editRating(1L, 2L, ratingData, 1L);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }



//    @Test
//    void testAddRatingWithInvalidClub() {
//        when(userService.getUserById(testUser.getId())).thenReturn(Optional.of(testUser));
//        when(clubService.getClubById(testClub.getId())).thenReturn(Optional.empty());
//
//        RuntimeException exception = assertThrows(RuntimeException.class, () -> mainController.addRating(testClub.getId(), testUser.getId(), testRatingData));
//
//        assertEquals("Club not found", exception.getMessage());
//
//    }

    @Test
    void testEditRatingWithValidInput() {
        when(clubService.getClubById(testClub.getId())).thenReturn(Optional.of(testClub));
        when(ratingService.getRatingById(testRating.getId())).thenReturn(Optional.of(testRating));
        when(ratingService.saveRating(testRating)).thenReturn(testRating);

        ResponseEntity<Rating> response = mainController.editRating(testClub.getId(), testUser.getId(), testRatingData, testRating.getId());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testRating, response.getBody());
        assertEquals(testRatingData.getRating(), testRating.getValue());
        assertEquals(testRatingData.getComment(), testRating.getComment());
    }




    @Test
    void testAddRatingWithInvalidUserId() {
        // Arrange
        long testClubId = 1L;
        long testUserId = 2L;
        MainController.RatingData testRatingData = new MainController.RatingData(5, "Great club!");
        when(userService.getUserById(testUserId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Rating> response = mainController.addRating(testClubId, testUserId, testRatingData);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
    @Test
    void testEditRatingWithInvalidInput() {
        when(clubService.getClubById(testClub.getId())).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> mainController.editRating(testClub.getId(), testUser.getId(), testRatingData, testRating.getId()));

        assertEquals("Club not found", exception.getMessage());
    }

    @Test
    public void testDeleteRatingWithValidInput() {
        when(clubService.getClubById(testClub.getId())).thenReturn(Optional.of(testClub));
        when(ratingService.deleteRatingById(testRating.getId())).thenReturn(1L);

        ResponseEntity<String> response = mainController.deleteRating(testClub.getId(), testUser.getId(), testRating.getId());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Deleted...", response.getBody());
    }

    @Test
    public void testDeleteRatingWithInvalidInput() {
        when(clubService.getClubById(testClub.getId())).thenReturn(Optional.of(testClub));
        when(ratingService.deleteRatingById(testRating.getId())).thenReturn(-1L);

        ResponseEntity<String> response = mainController.deleteRating(testClub.getId(), testUser.getId(), testRating.getId());

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Rating not found.", response.getBody());
    }










}
