package unist.ep.milestone2.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import unist.ep.milestone2.model.Club;
import unist.ep.milestone2.model.User;
import unist.ep.milestone2.service.ClubService;
import unist.ep.milestone2.service.UserService;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final ClubService clubService;
    private final UserService userService;
    public AdminController(ClubService clubService, UserService userService) {
        this.clubService = clubService;
        this.userService = userService;
    }
    @PostMapping("/login")
    public ResponseEntity<Long> adminLogin(@RequestParam String email,
                                             @RequestParam String password,
                                             HttpSession session) {
        User user = userService.getUserByEmail(email);
        if (user != null && user.getPassword().equals(password) && user.getRole() == 1) {
            System.out.println("IN ADMIN LOGIN");
            session.setAttribute("user", user);
            return new ResponseEntity<>(user.getId(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(-1L, HttpStatus.UNAUTHORIZED);
        }
    }
//    @PostMapping(value = "/clubs",consumes = "application/json", produces = "application/json")
//    public ResponseEntity<Club> addClub(@RequestBody Club club) {
//        if (club.getName() == null && club.getEmail() == null) {
//            return new ResponseEntity<>(club, HttpStatus.BAD_REQUEST);
//        }
//        Club clubNew = clubService.saveClub(club);
//        return new ResponseEntity<>(clubNew, HttpStatus.CREATED);
//    }
    @PutMapping(value = "/clubs/{id}", consumes = "application/json", produces = "application/json")
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
    @DeleteMapping(value = "/clubs/{id}", produces = "application/json")
    public ResponseEntity<Long> deleteClub(@PathVariable long id) {
        if (clubService.deleteClubById(id) > 0) {
            return new ResponseEntity<>(1L, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(-1L, HttpStatus.NOT_FOUND);
        }
    }

}
