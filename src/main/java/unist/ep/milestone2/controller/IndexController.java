package unist.ep.milestone2.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import unist.ep.milestone2.model.Club;
import unist.ep.milestone2.model.Rating;
import unist.ep.milestone2.model.User;
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
    public String loginHome(HttpServletResponse response) {
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
        response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
        response.setDateHeader("Expires", 0); // Proxies.
        return "login";
    }
    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String password,
                        Model model, HttpSession session) {
        User user = userService.getUserByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            session.setAttribute("user", user);
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
    public String clubPage(@PathVariable Long id, Model model, HttpSession session) {
        Optional<Club> club = clubService.getClubById(id);
        if (club.isEmpty()) {
            return "redirect:/clubs";
        } else {
            User user = (User) session.getAttribute("user");
            model.addAttribute("club", club.get());
            model.addAttribute("ratings", ratingService.getRatingsByClubId(id));
            model.addAttribute("user", user);
            return "club";
        }
    }
    @PostMapping("/clubs/{id}")
    public String addRating(@PathVariable Long id,
                            @RequestParam Integer rating,
                            @RequestParam String comment,
                            HttpSession session) {
        User user = (User) session.getAttribute("user");
        Club club = clubService.getClubById(id).orElseThrow(() -> new RuntimeException("Club not found"));
        Rating newRating = new Rating(user.getId(), club.getId(), rating, comment);
        ratingService.saveRating(newRating);
        return "redirect:/clubs/" + id;
    }
    @PutMapping("/clubs/{id}")
    public String editRating(@PathVariable Long id,
                            @RequestParam Integer rating,
                            @RequestParam String comment,
                            @RequestParam Long ratingId,
                            HttpSession session) {
        User user = (User) session.getAttribute("user");
        clubService.getClubById(id).orElseThrow(() -> new RuntimeException("Club not found"));
        Optional<Rating> optionalRating = ratingService.getRatingById(ratingId);
        Rating ratingToUpdate = optionalRating.orElseThrow(() -> new RuntimeException("Rating not found"));
        if (!ratingToUpdate.getUser_id().equals(user.getId())) {
            throw new RuntimeException("User is not authorized to edit this rating");
        }
        ratingToUpdate.setValue(rating);
        ratingToUpdate.setComment(comment);
        ratingService.saveRating(ratingToUpdate);
        return "redirect:/clubs/" + id;
    }
    @DeleteMapping("/clubs/{id}")
    public String deleteRating(@PathVariable Long id,
                             @RequestParam Long ratingId) {
        if(ratingService.deleteRatingById(ratingId) < 0) {
            throw new RuntimeException("Rating not found");
        }
        return "redirect:/clubs/" + id;
    }

}
