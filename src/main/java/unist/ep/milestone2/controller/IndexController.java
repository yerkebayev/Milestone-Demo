package unist.ep.milestone2.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import unist.ep.milestone2.model.User;
import unist.ep.milestone2.service.UserService;

@Controller
public class IndexController {
    private final UserService userService;
    public IndexController(UserService userService) {
        this.userService = userService;
    }
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String email,
                                        @RequestParam String password) {
        User user = userService.getUserByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            return new ResponseEntity<>("Logged in successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Incorrect email or password", HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping(value = "/register", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> addUser(@RequestBody User user) {
        if (user.getEmail().endsWith("@unist.ac.kr")){
            User u = userService.getUserByEmail(user.getEmail());
            if (u == null) {
                User newUser = userService.saveUser(user);
                return new ResponseEntity<>(newUser, HttpStatus.CREATED);
            } else {
                return ResponseEntity.badRequest().body("There is already a user with this email");
            }
        } else{
            return ResponseEntity.badRequest().body("You can only register using your Unist account");
        }

    }


}
