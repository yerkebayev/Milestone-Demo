package unist.ep.milestone2.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unist.ep.milestone2.model.Club;
import unist.ep.milestone2.model.ClubType;
import unist.ep.milestone2.model.Rating;
import unist.ep.milestone2.model.User;
import unist.ep.milestone2.repository.HomeResponse;
import unist.ep.milestone2.repository.MainResponse;
import unist.ep.milestone2.service.ClubService;
import unist.ep.milestone2.service.RatingService;
import unist.ep.milestone2.service.TypeService;
import unist.ep.milestone2.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class IndexController {
    private final UserService userService;
    private final ClubService clubService;
    private final TypeService typeService;
    private final RatingService ratingService;



    public IndexController(UserService userService, ClubService clubService, TypeService typeService, RatingService ratingService) {
        this.userService = userService;
        this.clubService = clubService;
        this.typeService = typeService;
        this.ratingService = ratingService;
    }

//    @GetMapping({"/", "/login"})
//    public String home(){
//        return "club.html";
//    }
    @PostMapping("/login")
    public ResponseEntity<Long> login(@RequestParam(value = "email", defaultValue = "") String email, @RequestParam(value = "password", defaultValue = "") String password, HttpSession session) {
        User user = userService.getUserByEmail(email);
        if (user == null) {
            System.out.println("sout ketti");
            return new ResponseEntity<>(-1L, HttpStatus.UNAUTHORIZED);
        }
        if (user.getPassword().equals(password)) {
            System.out.println("Durs");
            session.setAttribute("userId", user.getId());
            return new ResponseEntity<>(user.getId(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(-1L, HttpStatus.BAD_REQUEST);
        }
    }

//    @GetMapping("/register")
//    public String getRegisterPage() {
//        return "register.html";
//    }

    @PostMapping(value = "/register", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> addUser(@RequestBody User user, HttpSession session) {
        if (user.getEmail().endsWith("@unist.ac.kr")){
            User u = userService.getUserByEmail(user.getEmail());
            if (u == null) {
                User newUser = userService.saveUser(user);
                newUser.setRole(0);
                session.setAttribute("userId", user.getId());
                System.out.println("Created");
                return new ResponseEntity<>(newUser, HttpStatus.CREATED);
            } else {
                System.out.println("The same");
                return ResponseEntity.badRequest().body("There is already a user with this email");
            }
        } else{
            System.out.println("unist");
            return ResponseEntity.badRequest().body("You can only register using your Unist account");
        }
    }

    @GetMapping(value = "/clubs")
    public HomeResponse getClubs(HttpSession session) {
        Long user_id = (Long) session.getAttribute("userId");
        if (user_id == null) {
            System.out.println("returned");
            return new HomeResponse();
        }
        System.out.println(user_id);
        Optional<User> userOptional = userService.getUserById(user_id);
        if (userOptional.isEmpty()) {
            System.out.println("returned 2");
            return new HomeResponse();
        }
        User user = userOptional.get();
        System.out.println(user);
        List<Club> clubs = clubService.getAllClubs();
        List<ClubType> clubTypes = userService.getPreferredClubTypes(user);
        System.out.println(clubTypes);
        List<Club> recommendedClubs = clubService.getClubsByClubTypes(clubTypes);
        System.out.println(recommendedClubs);
        System.out.println("HERE");
        HomeResponse hp = new HomeResponse(clubs, recommendedClubs);
        return hp;
    }
    @GetMapping(value = "/clubs/{id}", produces = "application/json")
    public ResponseEntity<MainResponse> getClubPage(@PathVariable long id, HttpSession session) {
        Optional<Club> optionalClub = clubService.getClubById(id);
        Long user_id = (Long) session.getAttribute("userId");
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
}
