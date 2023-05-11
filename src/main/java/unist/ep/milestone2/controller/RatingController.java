package unist.ep.milestone2.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unist.ep.milestone2.model.Rating;
import unist.ep.milestone2.service.RatingService;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/ratings")
public class RatingController {
    private final RatingService ratingService;

    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<Rating>> getAllRatings() {
        List<Rating> ratings = ratingService.getAllRatings();
        return new ResponseEntity<>(ratings, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<Rating> getRatingById(@PathVariable long id) {
        Optional<Rating> optionalRating = ratingService.getRatingById(id);
        return optionalRating.map(rating -> new ResponseEntity<>(rating, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Rating> addRating(@RequestBody Rating rating) {
        Rating newRating = ratingService.saveRating(rating);
        return new ResponseEntity<>(newRating, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Rating> updateRating(@PathVariable long id, @RequestBody Rating rating) {
        Optional<Rating> optionalRating = ratingService.getRatingById(id);
        if (optionalRating.isPresent()) {
            Rating r = optionalRating.get();
            r.setClub_id(rating.getClub_id());
            r.setUser_id(rating.getUser_id());
            r.setValue(rating.getValue());
            r.setComment(rating.getComment());
            ratingService.saveRating(r);
            return new ResponseEntity<>(r, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<String> deleteRating(@PathVariable long id) {
        if (ratingService.deleteRatingById(id) > 0) {
            return new ResponseEntity<>("Deleted...", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Rating not found.", HttpStatus.NOT_FOUND);
        }
    }
}
