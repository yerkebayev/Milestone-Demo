package unist.ep.milestone2.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unist.ep.milestone2.model.*;
import unist.ep.milestone2.repository.ClubRequest;
import unist.ep.milestone2.repository.ClubTypeResponse;
import unist.ep.milestone2.repository.HomeResponse;
import unist.ep.milestone2.repository.MainResponse;
import unist.ep.milestone2.service.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class IndexController {
    private final UserService userService;
    private final ClubService clubService;
    private final TypeService typeService;
    private final RatingService ratingService;
    private final UserClubTypeService userClubTypeService;



    public IndexController(UserService userService, ClubService clubService, TypeService typeService, RatingService ratingService, UserClubTypeService userClubTypeService) {
        this.userService = userService;
        this.clubService = clubService;
        this.typeService = typeService;
        this.ratingService = ratingService;
        this.userClubTypeService = userClubTypeService;
    }


    @PostMapping("/login")
    public ResponseEntity<Long> login(@RequestParam(value = "email", defaultValue = "") String email, @RequestParam(value = "password", defaultValue = "") String password, HttpSession session) {
        User user = userService.getUserByEmail(email);
        if (user == null) {
            return new ResponseEntity<>(-1L, HttpStatus.UNAUTHORIZED);
        }
        if (user.getPassword().equals(password)) {
            session.setAttribute("userId", user.getId());
            return new ResponseEntity<>(user.getId(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(-1L, HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping(value = "/register", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> addUser(@RequestBody User user, HttpSession session) {
        if (user.getEmail().endsWith("@unist.ac.kr")){
            User u = userService.getUserByEmail(user.getEmail());
            if (u == null) {
                User newUser = userService.saveUser(user);
                newUser.setRole(0);
                session.setAttribute("userId", user.getId());
                return new ResponseEntity<>(newUser, HttpStatus.CREATED);
            } else {
                return ResponseEntity.badRequest().body("There is already a user with this email");
            }
        } else{
            return ResponseEntity.badRequest().body("You can only register using your Unist account");
        }
    }

    @GetMapping(value = "/clubs")
    public HomeResponse getClubs(HttpSession session) {
        Long user_id = (Long) session.getAttribute("userId");
        if (user_id == null) {
            return new HomeResponse();
        }
        Optional<User> userOptional = userService.getUserById(user_id);
        if (userOptional.isEmpty()) {
            return new HomeResponse();
        }
        User user = userOptional.get();
        List<Club> clubs = clubService.getAllClubs();
        List<ClubType> clubTypes = userService.getPreferredClubTypes(user);
        List<Club> recommendedClubs = clubService.getClubsByClubTypes(clubTypes);
        return new HomeResponse(clubs, recommendedClubs);
    }
    @GetMapping(value = "/clubs/{id}", produces = "application/json")
    public ResponseEntity<MainResponse> getClubPage(@PathVariable long id, HttpSession session) {
        Optional<Club> optionalClub = clubService.getClubById(id);
        Long user_id = (Long) session.getAttribute("userId");
        if (user_id == null) {
            return new ResponseEntity<>(new MainResponse(), HttpStatus.NOT_FOUND);
        }
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
    @GetMapping(value = "/user/{id}")
    public User getUserById(@PathVariable long id) {
        Optional<User> userOptional = userService.getUserById(id);
        if (userOptional.isEmpty()) {
            return null;
        }
        return userOptional.get();
    }
    @GetMapping(value = "/ratings/{id}")
    public Rating getRatingById(@PathVariable long id) {
        Optional<Rating> ratingOptional = ratingService.getRatingById(id);
        if (ratingOptional.isEmpty()) {
            return null;
        }
        return ratingOptional.get();
    }
    @GetMapping(value = "/clubTypes")
    public ClubTypeResponse getAllClubTypes(HttpSession session) {
        Long user_id = (Long) session.getAttribute("userId");
        if (user_id == null) {
            return new ClubTypeResponse(new ArrayList<>(), new ArrayList<>());
        }
        Optional<User> optional = userService.getUserById(user_id);
        if (optional.isEmpty()) {
            return null;
        }
        User user = optional.get();
        return new ClubTypeResponse(typeService.getAllClubTypes(), userService.getPreferredClubTypesInInteger(user));
    }
    @GetMapping(value = "/clubTypesForAdmin")
    public ClubTypeResponse getAllClubTypes() {
        return new ClubTypeResponse(typeService.getAllClubTypes(), null);
    }
    @PostMapping(value = "/clubTypes/add", consumes = "application/json", produces = "application/json")
    public Long addClubTypes(@RequestBody List<Integer> clubTypes, HttpSession session) {
        System.out.println("HERE");
        Long user_id = (Long) session.getAttribute("userId");
        if (user_id == null) {
            return -1L;
        }
        Optional<User> optional = userService.getUserById(user_id);
        if (optional.isEmpty()) {
            return -1L;
        }
        User user = optional.get();
        userClubTypeService.deleteUserClubType(user.getId());
        for (long clubType_id : clubTypes) {
            userClubTypeService.saveUserClubType(new UserClubType(user_id, clubType_id));
        }
        return 1L;
    }

    @PostMapping(value = "/clubs/{id}/ratings", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Rating> addRating(@PathVariable long id, @RequestBody RatingData ratingData, HttpSession session) {
        Long user_id = (Long) session.getAttribute("userId");
        if (user_id == null) {
            return new ResponseEntity<>(new Rating(), HttpStatus.NOT_FOUND);
        }
        Optional<User> userOptional = userService.getUserById(user_id);
        Optional<Club> optionalClub = clubService.getClubById(id);
        if (userOptional.isEmpty() || optionalClub.isEmpty()) {
            return null;
        }
        User user = userOptional.get();
        Club club = optionalClub.get();

        Rating newRating = new Rating(user.getId(), club.getId(), ratingData.getRating(), ratingData.getComment());
        ratingService.saveRating(newRating);
        return new ResponseEntity<>(newRating, HttpStatus.CREATED);
    }
    @GetMapping(value = "/clubs/{id}/ratings/avg", produces = "application/json")
    public ResponseEntity<Double> getAverageRatingOfClub(@PathVariable long id) {
        Optional<Club> optionalClub = clubService.getClubById(id);
        if (optionalClub.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        Double averageRating = ratingService.getAverageRatingByClubId(id);
        return new ResponseEntity<>(averageRating, HttpStatus.OK);
    }
    @GetMapping(value = "/admin/clubs")
    public ResponseEntity<List<Club>> getClubsForAdmin() {
        List<Club> clubs = clubService.getAllClubs();
        return new ResponseEntity<>(clubs, HttpStatus.OK);
    }
    @GetMapping(value = "/admin/clubs/{id}", produces = "application/json")
    public ResponseEntity<ClubRequest> getClubPageForAdmin(@PathVariable long id) {
        Optional<Club> optionalClub = clubService.getClubById(id);
        if (optionalClub.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        Club club = optionalClub.get();
        User head = userService.getUserById(club.getHead_id()).get();
        return new ResponseEntity<>(new ClubRequest(club.getId(), club.getName(), club.getClubType_id(), club.getEmail(), club.getMission(), club.getDescription(), head.getName() + " " + head.getSurname(), club.getContact(), club.getImage()), HttpStatus.OK);
    }
    @PostMapping(value = "/admin/clubs",consumes = "application/json", produces = "application/json")
    public ResponseEntity<Club> addClub(@RequestBody ClubRequest clubForm) {
        System.out.println(clubForm.getName() + " " + clubForm.getClubType() + " " + clubForm.getHeadEmail() + " " +clubForm.getEmail());
        User head = userService.getUserByEmail(clubForm.getHeadEmail());
        Club cl = clubService.getClubByEmail(clubForm.getEmail());
        if (head != null && cl == null) {
            Club newClub = clubService.saveClub(new Club(clubForm.getName(), clubForm.getEmail(), clubForm.getClubType(), clubForm.getDescription(), clubForm.getMission(), clubForm.getContact(), head.getId(), clubForm.getImage()));
            return new ResponseEntity<>(newClub, HttpStatus.CREATED);
        }
        String[] headNameAndSurname = (clubForm.getHeadEmail().split(" "));
        if (headNameAndSurname.length > 1) {
            User newHead = userService.getUserByNameAndSurname(headNameAndSurname[0], headNameAndSurname[1]);
            if (newHead != null) {
                System.out.println(newHead.getId() + " " + newHead.getName());
                System.out.println("MY ID " + cl.getId());
                cl.setName(clubForm.getName());
                cl.setClubType_id(clubForm.getClubType());
                cl.setEmail(clubForm.getEmail());
                cl.setDescription(clubForm.getDescription());
                cl.setMission(clubForm.getMission());
                cl.setHead_id(newHead.getId());
                cl.setContact(clubForm.getContact());
                cl.setImage(clubForm.getImage());
                clubService.saveClub(cl);
                return new ResponseEntity<>(cl, HttpStatus.OK);
            }
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }
    @DeleteMapping(value = "/admin/clubs/{id}", produces = "application/json")
    public ResponseEntity<Long> deleteClub(@PathVariable long id) {
        if (clubService.deleteClubById(id) > 0) {
            return new ResponseEntity<>(1L, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(-1L, HttpStatus.NOT_FOUND);
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

