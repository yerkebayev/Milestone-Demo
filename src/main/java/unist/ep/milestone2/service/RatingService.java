package unist.ep.milestone2.service;

import org.springframework.web.multipart.MultipartFile;
import unist.ep.milestone2.model.Rating;
import java.util.List;
import java.util.Optional;

public interface RatingService {
    List<Rating> getAllRatings();
    Optional<Rating> getRatingById(Long id);
    List<Rating> getRatingsByClubId(Long clubId);
    Rating saveRating(Rating rating);
    Long deleteRatingById(Long id);
    Double getAverageRatingByClubId(Long clubId);
    void importRatingCsv(MultipartFile file);

}
