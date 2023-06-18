package unist.ep.milestone2.service.impl;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import unist.ep.milestone2.job.CSVHelper;
import unist.ep.milestone2.model.Rating;
import unist.ep.milestone2.repository.RatingRepository;
import unist.ep.milestone2.service.RatingService;

import java.io.IOException;
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
        Sort sortById = Sort.by(Sort.Direction.ASC, "id");
        return ratingRepository.findAll(sortById);
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
        Double avg = ratingRepository.getAverageRatingByClubId(clubId);
        return Math.round(avg * 10.0) / 10.0;
    }

    @Override
    public void importRatingCsv(MultipartFile file) {
        try {
            List<Rating> ratings = CSVHelper.csvToRatings(file.getInputStream());
            ratingRepository.saveAll(ratings);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
