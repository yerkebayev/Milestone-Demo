package unist.ep.milestone2.service;

import unist.ep.milestone2.model.Rating;
import java.util.List;
import java.util.Optional;

public interface RatingService {
    List<Rating> getAllRatings();
    Optional<Rating> getRatingById(Long id);
    List<Rating> getRatingsByClubId(Long clubId);
    Rating addRating(Rating rating);
    Optional<Rating> updateRating(Rating rating);
    Long deleteRatingById(Long id);
    Double getAverageRatingByClubId(Long clubId);
}
