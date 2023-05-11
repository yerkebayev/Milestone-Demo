package unist.ep.milestone2.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import unist.ep.milestone2.model.Club;
import unist.ep.milestone2.model.User;
import unist.ep.milestone2.service.ClubService;
import unist.ep.milestone2.service.TypeService;
import unist.ep.milestone2.service.UserService;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final ClubService clubService;
    private final UserService userService;
    private final TypeService typeService;
    public AdminController(ClubService clubService, UserService userService, TypeService typeService) {
        this.clubService = clubService;
        this.userService = userService;
        this.typeService = typeService;
    }
    @GetMapping()
    public String loginHome(HttpServletResponse response) {
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
        response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
        response.setDateHeader("Expires", 0); // Proxies.
        return "admin_login";
    }
    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String password,
                        Model model) {
        User user = userService.getUserByEmail(email);
        if (user != null && user.getPassword().equals(password) && user.getRole() == 1) {
            return "redirect:/admin/clubs";
        } else {
            model.addAttribute("error", "Invalid email or password");
            return "login";
        }
    }
    @GetMapping("/clubs")
    public String showClubs(Model model) {
        List<Club> clubs = clubService.getAllClubs();
        System.out.println(clubs.size() + " SIZE");
        model.addAttribute("clubs", clubs);
        return "admin_clubs";
    }
    @PostMapping("/clubs/save")
    public String saveClub(@ModelAttribute Club club) {
        clubService.saveClub(club);
        return "redirect:/admin/clubs";
    }
    @GetMapping("/clubs/new")
    public String addClub(Model model) {
        model.addAttribute("club", new Club());
        System.out.println(typeService.getAllClubTypes());
        model.addAttribute("clubTypes", typeService.getAllClubTypes());
        return "admin_club_form";
    }

    @GetMapping("/clubs/{id}")
    public String clubPage(@PathVariable Long id, Model model) {
        Optional<Club> club = clubService.getClubById(id);
        if (club.isEmpty()) {
            return "redirect:/admin/clubs";
        } else {
            model.addAttribute("club", club.get());
            return "club";
        }
    }

    @GetMapping("/clubs/{id}/edit")
    public String editClub(@PathVariable Long id, Model model) {
        Optional<Club> club = clubService.getClubById(id);
        if (club.isEmpty()) {
            throw new RuntimeException("Club not found");
        }
        model.addAttribute("club", club.get());
        model.addAttribute("clubTypes", typeService.getAllClubTypes());
        return "admin_club_form";
    }
    @PostMapping("/clubs/{id}")
    public String updateClub(@ModelAttribute Club club) {
        clubService.saveClub(club);
        return "redirect:/admin/clubs";
    }
    @GetMapping("/clubs/{id}/delete")
    public String deleteClub(@PathVariable Long id) {
        clubService.deleteClubById(id);
        return "redirect:/admin/clubs";
    }
}
