package first.ep.project.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import first.ep.project.model.*;
import first.ep.project.repository.ClubRequest;
import first.ep.project.repository.ClubTypeResponse;
import first.ep.project.repository.HomeResponse;
import first.ep.project.repository.MainResponse;
import first.ep.project.service.*;

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
        if (userService.verifyPassword(password, user.getPassword())) {
            session.setAttribute("userId", user.getId());
            return new ResponseEntity<>(user.getId(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(-1L, HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping(value = "/register", produces = "application/json")
    public ResponseEntity<?> addUser(@RequestParam(value = "name", defaultValue = "") String name, @RequestParam(value = "surname", defaultValue = "") String surname, @RequestParam(value = "email", defaultValue = "") String email,@RequestParam(value = "password", defaultValue = "") String password, HttpSession session) {
        User u = userService.getUserByEmail(email);
        if (u == null) {
            User newUser = userService.addUser(new User(name, surname, email, password, 0));
            newUser.setRole(0);
            session.setAttribute("userId", newUser.getId());
            return new ResponseEntity<>(newUser, HttpStatus.CREATED);
        } else {
            return ResponseEntity.badRequest().body("There is already a user with this email");
        }
    }

    @GetMapping(value = "/user/{id}")
    public User getUserById(@PathVariable long id) {
        Optional<User> userOptional = userService.getUserById(id);
        if (userOptional.isEmpty()) {
            return null;
        }
        return userOptional.get();
    }
}

