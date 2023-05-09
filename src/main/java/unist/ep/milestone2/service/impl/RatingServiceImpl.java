package unist.ep.milestone2.service.impl;

import org.springframework.stereotype.Service;
import unist.ep.milestone2.model.Rating;
import unist.ep.milestone2.repository.RatingRepository;
import unist.ep.milestone2.service.RatingService;
import java.util.List;
import java.util.Optional;

@Service
public class RatingServiceImpl implements RatingService {
    private final RatingRepository ratingRepository;
    RatingServiceImpl(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }
    @Override
    public List<Rating> getAllRatings() {
        return ratingRepository.findAll();
    }

    @Override
    public Optional<Rating> getRatingById(Long id) {
        return ratingRepository.findById(id);
    }

    @Override
    public List<Rating> getRatingsByClubId(Long clubId) {
        return ratingRepository.getRatingsByClubId(clubId);
    }

    @Override
    public Rating saveRating(Rating rating) {
        return ratingRepository.save(rating);
    }

    @Override
    public Long deleteRatingById(Long id) {
        if (ratingRepository.existsById(id)) {
            ratingRepository.deleteById(id);
            return 1L;
        }
        return -1L;
    }

    @Override
    public Double getAverageRatingByClubId(Long clubId) {
        return ratingRepository.getAverageRatingByClubId(clubId);
    }
}
