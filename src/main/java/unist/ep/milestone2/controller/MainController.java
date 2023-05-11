package unist.ep.milestone2.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unist.ep.milestone2.model.*;
import unist.ep.milestone2.repository.MainResponse;
import unist.ep.milestone2.service.ClubService;
import unist.ep.milestone2.service.RatingService;
import unist.ep.milestone2.service.UserClubTypeService;
import unist.ep.milestone2.service.UserService;

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

    public MainController(ClubService clubService, UserService userService, RatingService ratingService, UserClubTypeService userClubTypeService) {
        this.clubService = clubService;
        this.userService = userService;
        this.ratingService = ratingService;
        this.userClubTypeService = userClubTypeService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String email,
                                        @RequestParam String password,
                                        HttpSession session) {
        User user = userService.getUserByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            session.setAttribute("user", user);
            return new ResponseEntity<>("Logged in successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Incorrect email or password", HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping(value = "/register", consumes = "application/json", produces = "application/json")
    public ResponseEntity<User> addUser(@RequestBody User user) {
        User newUser = userService.saveUser(user);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }
    @PostMapping(value = "{user_id}/choose_types", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> addClubTypes(@PathVariable long user_id, @RequestBody List<Integer> clubTypes, HttpSession session) {
        Optional<User> optional = userService.getUserById(user_id);
        if (optional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
        }
        for (int clubType_id : clubTypes) {
            UserClubType uc = userClubTypeService.saveAllTypes(new UserClubType(user_id, clubType_id));
            System.out.println(uc);
        }
        return ResponseEntity.ok("Club types added successfully");
    }

    @GetMapping(value = "/clubs", produces = "application/json")
    public ResponseEntity<List<Club>> getClubs() {
        List<Club> clubs = clubService.getAllClubs();
        return new ResponseEntity<>(clubs, HttpStatus.OK);
    }
    @GetMapping(value = "/clubs/{id}", produces = "application/json")
    public ResponseEntity<MainResponse> getClubPage(@PathVariable long id, HttpSession session) {
        Optional<Club> optionalClub = clubService.getClubById(id);
        if (optionalClub.isEmpty()) {
            return new ResponseEntity<>(new MainResponse(), HttpStatus.NOT_FOUND);
        }
        Club club = optionalClub.get();
        User user = (User) session.getAttribute("user");

        List<Rating> ratings = ratingService.getRatingsByClubId(id);//comm

        Double averageRating = ratingService.getAverageRatingByClubId(id);//

        List<ClubType> clubTypeList = new ArrayList<>();
        clubTypeList.add(club.getClubType_id());
        List<Club> recommendedClubs = clubService.getClubsByClubTypes(clubTypeList);//
        MainResponse mainResponse = new MainResponse(club, user, ratings, averageRating, recommendedClubs);
        return new ResponseEntity<>(mainResponse, HttpStatus.OK);
    }

    @PostMapping(value = "/clubs/{id}/ratings", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Rating> addRating(@PathVariable long id, @RequestBody RatingData ratingData) {
        Optional<User> optionalUser = userService.getUserById(ratingData.user_id);
        if(optionalUser.isEmpty()){
            return new ResponseEntity<>(new Rating(), HttpStatus.NOT_FOUND);
        }
        User user = optionalUser.get();
        Club club = clubService.getClubById(id).orElseThrow(() -> new RuntimeException("Club not found"));
        Rating newRating = new Rating(user.getId(), club.getId(), ratingData.rating, ratingData.comment);
        ratingService.saveRating(newRating);
        return new ResponseEntity<>(newRating, HttpStatus.CREATED);
    }

    @PutMapping("/clubs/{id}/ratings/{ratingId}")
    public ResponseEntity<Rating> editRating(@PathVariable Long id,
                             @RequestBody RatingData ratingData,
                             @PathVariable Long ratingId) {

        clubService.getClubById(id).orElseThrow(() -> new RuntimeException("Club not found"));
        Optional<Rating> optionalRating = ratingService.getRatingById(ratingId);
        if (optionalRating.isPresent()) {
            Rating ratingToUpdate = optionalRating.get();
            ratingToUpdate.setValue(ratingData.rating);
            ratingToUpdate.setComment(ratingData.comment);
            ratingService.saveRating(ratingToUpdate);
            return new ResponseEntity<>(ratingToUpdate, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping(value = "/clubs/{id}/ratings/{ratingId}", produces = "application/json")
    public ResponseEntity<String> deleteRating(@PathVariable long id, @PathVariable Long ratingId) {
        clubService.getClubById(id).orElseThrow(() -> new RuntimeException("Club not found"));
        if (ratingService.deleteRatingById(ratingId) > 0) {
            return new ResponseEntity<>("Deleted...", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Rating not found.", HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/admin/login")
    public ResponseEntity<String> adminLogin(@RequestParam String email,
                                        @RequestParam String password,
                                        HttpSession session) {
        User user = userService.getUserByEmail(email);
        if (user != null && user.getPassword().equals(password) && user.getRole() == 1) {
            session.setAttribute("user", user);
            return new ResponseEntity<>("Logged in successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Incorrect email or password", HttpStatus.UNAUTHORIZED);
        }
    }
    @GetMapping(value = "/admin/clubs", produces = "application/json")
    public ResponseEntity<List<Club>> getClubsForAdmin() {
        List<Club> clubs = clubService.getAllClubs();
        return new ResponseEntity<>(clubs, HttpStatus.OK);
    }
    @PostMapping(value = "/admin/clubs",consumes = "application/json", produces = "application/json")
    public ResponseEntity<Club> addClub(@RequestBody Club club) {
        Club clubNew = clubService.saveClub(club);
        return new ResponseEntity<>(clubNew, HttpStatus.CREATED);
    }
    @PutMapping(value = "/admin/clubs/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Club> updateClub(@PathVariable long id, @RequestBody Club club){
        Optional<Club> optionalClub = clubService.getClubById(id);
        if (optionalClub.isPresent()) {
            Club c = optionalClub.get();
            c.setName(club.getName());
            c.setClubType_id(club.getClubType_id());
            c.setEmail(club.getEmail());
            c.setDescription(club.getDescription());
            c.setMission(club.getMission());
            c.setHead_id(club.getHead_id());
            c.setContact(club.getContact());
            clubService.saveClub(club);
            return new ResponseEntity<>(c, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping(value = "/admin/clubs/{id}", produces = "application/json")
    public ResponseEntity<String> deleteClub(@PathVariable long id) {
        if (clubService.deleteClubById(id) > 0) {
            return new ResponseEntity<>("Deleted...", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Club not found.", HttpStatus.NOT_FOUND);
        }
    }

    public static class RatingData{
        Integer rating;
        String comment;
        Long user_id;


    }




}
