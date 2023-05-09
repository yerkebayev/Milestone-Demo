package unist.ep.milestone2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import unist.ep.milestone2.model.Club;
import unist.ep.milestone2.service.ClubService;
import unist.ep.milestone2.service.RatingService;
import unist.ep.milestone2.service.UserService;

import java.util.List;
import java.util.Optional;

@Controller
public class IndexController {
    private final ClubService clubService;
    private final UserService userService;

    private final RatingService ratingService;
    public IndexController(ClubService clubService, UserService userService, RatingService ratingService) {
        this.clubService = clubService;
        this.userService = userService;
        this.ratingService = ratingService;
    }

    @GetMapping(value = {"/","/login"})
    public String login() {
        return "login";
    }
    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String password,
                        Model model) {
        if (userService.checkUser(email, password)) {
            System.out.println("Home");
            return "redirect:/clubs";
        } else {
            model.addAttribute("error", "Invalid email or password");
            return "login";
        }
    }

    @GetMapping("/clubs")
    public String showClubs(Model model) {
        List<Club> clubs = clubService.getAllClubs();
        model.addAttribute("clubs", clubs);
        System.out.println("here");
        return "clubs";
    }
    @GetMapping("/clubs/{id}")
    public String clubPage(@PathVariable Long id, Model model) {
        Optional<Club> club = clubService.getClubById(id);
        if (club.isEmpty()) {
            return "redirect:/clubs";
        } else {
            model.addAttribute("club", club.get());
            model.addAttribute("ratings", ratingService.getRatingsByClubId(id));
            return "club";
        }
    }

}
