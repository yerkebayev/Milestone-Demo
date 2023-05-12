package unist.ep.milestone2.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unist.ep.milestone2.model.*;
import unist.ep.milestone2.repository.HomeResponse;
import unist.ep.milestone2.repository.MainResponse;
import unist.ep.milestone2.service.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class MainController {
    private final ClubService clubService;
    private final UserService userService;
    private final RatingService ratingService;
    private final UserClubTypeService userClubTypeService;
    private final TypeService typeService;

    public MainController(ClubService clubService, UserService userService, RatingService ratingService, UserClubTypeService userClubTypeService, TypeService typeService) {
        this.clubService = clubService;
        this.userService = userService;
        this.ratingService = ratingService;
        this.userClubTypeService = userClubTypeService;
        this.typeService = typeService;
    }
    @PostMapping(value = "/{user_id}/choose_types", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> addClubTypes(@PathVariable long user_id, @RequestBody List<Integer> clubTypes) {
        Optional<User> optional = userService.getUserById(user_id);
        if (optional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
        }
        for (long clubType_id : clubTypes) {
            UserClubType uc = userClubTypeService.saveAllTypes(new UserClubType(user_id, clubType_id));
        }
        return ResponseEntity.ok("Club types added successfully");
    }

    @GetMapping(value = "/{user_id}/clubs", produces = "application/json")
    public ResponseEntity<HomeResponse> getClubs(@PathVariable Long user_id) {
        Optional<User> userOptional = userService.getUserById(user_id);
        if (userOptional.isEmpty()) {
            return new ResponseEntity<>(new HomeResponse(), HttpStatus.NOT_FOUND);
        }
        User user = userOptional.get();
        List<Club> clubs = clubService.getAllClubs();
        List<ClubType> clubTypes = userService.getPreferredClubTypes(user);
        List<Club> recommendedClubs = clubService.getClubsByClubTypes(clubTypes);


        HomeResponse homeResponse = new HomeResponse(clubs, recommendedClubs);
        return new ResponseEntity<>(homeResponse, HttpStatus.OK);
    }
    @GetMapping(value = "/{user_id}/clubs/{id}", produces = "application/json")
    public ResponseEntity<MainResponse> getClubPage(@PathVariable long id, @PathVariable Long user_id) {
        Optional<Club> optionalClub = clubService.getClubById(id);
        Optional<User> userOptional = userService.getUserById(user_id);
        if (optionalClub.isEmpty() || userOptional.isEmpty()) {
            return new ResponseEntity<>(new MainResponse(), HttpStatus.NOT_FOUND);
        }
        Club club = optionalClub.get();
        User user = userOptional.get();

        List<ClubType> clubTypeList = new ArrayList<>();
        Optional<ClubType> clubTypeOptional = typeService.getClubTypeById(club.getClubType_id());
        if (clubTypeOptional.isEmpty()) {
            return new ResponseEntity<>(new MainResponse(), HttpStatus.NOT_FOUND);
        }
        clubTypeList.add(clubTypeOptional.get());

        List<Club> recommendedClubs = clubService.getClubsByClubTypes(clubTypeList);
        List<Rating> ratings = ratingService.getRatingsByClubId(id);
        Double averageRating = ratingService.getAverageRatingByClubId(id);

        MainResponse mainResponse = new MainResponse(club, user, ratings, averageRating, recommendedClubs);
        return new ResponseEntity<>(mainResponse, HttpStatus.OK);
    }

    @PostMapping(value = "/{user_id}/clubs/{id}/ratings", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Rating> addRating(@PathVariable long id, @PathVariable long user_id, @RequestBody RatingData ratingData) {
        Optional<User> optionalUser = userService.getUserById(user_id);
        if(optionalUser.isEmpty()){
            return new ResponseEntity<>(new Rating(), HttpStatus.NOT_FOUND);
        }
        User user = optionalUser.get();
        Club club = clubService.getClubById(id).orElseThrow(() -> new RuntimeException("Club not found"));
        Rating newRating = new Rating(user.getId(), club.getId(), ratingData.getRating(), ratingData.getComment());
        ratingService.saveRating(newRating);
        return new ResponseEntity<>(newRating, HttpStatus.CREATED);
    }

    @PutMapping("/{user_id}/clubs/{id}/ratings/{ratingId}")
    public ResponseEntity<Rating> editRating(@PathVariable Long id, @PathVariable long user_id,
                             @RequestBody RatingData ratingData,
                             @PathVariable Long ratingId) {

        clubService.getClubById(id).orElseThrow(() -> new RuntimeException("Club not found"));
        Optional<Rating> optionalRating = ratingService.getRatingById(ratingId);
        if (optionalRating.isPresent()) {
            Rating ratingToUpdate = optionalRating.get();
            if (ratingToUpdate.getUser_id() != user_id) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            ratingToUpdate.setValue(ratingData.getRating());
            ratingToUpdate.setComment(ratingData.getComment());
            ratingService.saveRating(ratingToUpdate);
            return new ResponseEntity<>(ratingToUpdate, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping(value = "/{user_id}/clubs/{id}/ratings/{ratingId}", produces = "application/json")
    public ResponseEntity<String> deleteRating(@PathVariable long id, @PathVariable long user_id, @PathVariable Long ratingId) {
        clubService.getClubById(id).orElseThrow(() -> new RuntimeException("Club not found"));
        if (ratingService.deleteRatingById(ratingId) > 0) {
            return new ResponseEntity<>("Deleted...", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Rating not found.", HttpStatus.NOT_FOUND);
        }
    }

    public static class RatingData{
        private Integer rating;
        private String comment;

        public Integer getRating() {
            return rating;
        }

        public void setRating(Integer rating) {
            this.rating = rating;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public RatingData() {
        }

        public RatingData(Integer rating, String comment) {
            this.rating = rating;
            this.comment = comment;
        }
    }
}
