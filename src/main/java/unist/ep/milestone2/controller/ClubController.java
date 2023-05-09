package unist.ep.milestone2.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unist.ep.milestone2.model.Club;
import unist.ep.milestone2.service.ClubService;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clubs")
public class ClubController {
    private final ClubService clubService;

    public ClubController(ClubService clubService) {
        this.clubService = clubService;
    }
    @GetMapping(produces = "application/json")
    public ResponseEntity<List<Club>> getClubs() {
        List<Club> clubs = clubService.getAllClubs();
        return new ResponseEntity<>(clubs, HttpStatus.OK);
    }
    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<Club> getClubById(@PathVariable long id) {
        Optional<Club> optionalClub = clubService.getClubById(id);
        return optionalClub.map(club -> new ResponseEntity<>(club, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @GetMapping(value = "/{name}", produces = "application/json")
    public ResponseEntity<Club> getClubByName(@PathVariable String name) {
        Optional<Club> optionalClub = clubService.getClubByName(name);
        return optionalClub.map(club -> new ResponseEntity<>(club, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Club> addClub(@RequestBody Club club) {
        Club clubNew = clubService.saveClub(club);
        return new ResponseEntity<>(clubNew, HttpStatus.CREATED);
    }
    @PutMapping(value = "/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Club> updateClub(@PathVariable long id, @RequestBody Club club){
        Optional<Club> optionalClub = clubService.getClubById(id);
        if (optionalClub.isPresent()) {
            Club c = optionalClub.get();
            c.setName(club.getName());
            c.setTypeId(club.getTypeId());
            c.setDescription(club.getDescription());
            c.setMission(club.getMission());
            c.setHeadId(club.getHeadId());
            c.setContact(club.getContact());
            clubService.saveClub(club);
            return new ResponseEntity<>(c, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<String> deleteClub(@PathVariable long id) {
        if (clubService.deleteClubById(id) > 0) {
            return new ResponseEntity<>("Deleted...", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Club not found.", HttpStatus.NOT_FOUND);
        }
    }




}
