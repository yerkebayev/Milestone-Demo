package unist.ep.milestone2.controller;

import jakarta.servlet.http.HttpSession;
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

    @GetMapping({"/", "/login"})
    public String home(){
        return "index.html";
    }
    @PostMapping("/login")
    public ResponseEntity<Long> login(@RequestParam(value = "email", defaultValue = "") String email, @RequestParam(value = "password", defaultValue = "") String password, HttpSession session) {
        User user = userService.getUserByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            session.setAttribute("userId", user.getId());
            return new ResponseEntity<>(user.getId(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(-1L, HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/register")
    public String getRegisterPage() {
        return "register.html";
    }

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
}
