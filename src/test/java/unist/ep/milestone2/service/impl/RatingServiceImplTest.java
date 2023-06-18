package unist.ep.milestone2.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import unist.ep.milestone2.job.CSVHelper;
import unist.ep.milestone2.model.Rating;
import unist.ep.milestone2.repository.RatingRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class RatingServiceImplTest {
    @InjectMocks
    private RatingServiceImpl ratingService;

    @Mock
    private RatingRepository ratingRepository;

    private Rating rating;
    private List<Rating> ratingList;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        rating = new Rating(1L, 1L, 3, "Good");
        ratingList = new ArrayList<>();
        ratingList.add(rating);
    }

    @Test
    public void testGetAllRatings() {
        when(ratingRepository.findAll()).thenReturn(ratingList);

        List<Rating> found = ratingService.getAllRatings();

        verify(ratingRepository, times(1)).findAll();
        assertEquals(ratingList, found);
    }

    @Test
    public void testGetRatingById() {
        when(ratingRepository.findById(1L)).thenReturn(Optional.of(rating));

        Optional<Rating> found = ratingService.getRatingById(1L);

        verify(ratingRepository, times(1)).findById(1L);
        assertTrue(found.isPresent());
        assertEquals(rating, found.get());
    }

    @Test
    public void testGetRatingsByClubId() {
        when(ratingRepository.getRatingsByClubId(1L)).thenReturn(ratingList);

        List<Rating> found = ratingService.getRatingsByClubId(1L);

        verify(ratingRepository, times(1)).getRatingsByClubId(1L);
        assertEquals(ratingList, found);
    }

    @Test
    public void testSaveRating() {
        when(ratingRepository.save(any(Rating.class))).thenReturn(rating);

        Rating saved = ratingService.saveRating(rating);

        verify(ratingRepository, times(1)).save(rating);
        assertEquals(rating, saved);
    }

    @Test
    public void testDeleteRatingById() {
        when(ratingRepository.existsById(1L)).thenReturn(true);

        Long deleted = ratingService.deleteRatingById(1L);

        verify(ratingRepository, times(1)).existsById(1L);
        verify(ratingRepository, times(1)).deleteById(1L);
        assertEquals(1L, deleted);
    }

    @Test
    public void testDeleteRatingByIdNotFound() {
        when(ratingRepository.existsById(1L)).thenReturn(false);

        Long deleted = ratingService.deleteRatingById(1L);

        verify(ratingRepository, times(1)).existsById(1L);
        verify(ratingRepository, never()).deleteById(anyLong());
        assertEquals(-1L, deleted);
    }

    @Test
    public void testGetAverageRatingByClubId() {
        when(ratingRepository.getAverageRatingByClubId(1L)).thenReturn(3.5);

        Double avg = ratingService.getAverageRatingByClubId(1L);

        verify(ratingRepository, times(1)).getAverageRatingByClubId(1L);
        assertEquals(3.5, avg);
    }

//    @Test
//    public void testSaveRatingsFromCsvFile() throws IOException {
//        String fileName = "ratings.csv";
//        MockMultipartFile file = new MockMultipartFile(fileName, getClass().getClassLoader().getResourceAsStream(fileName));
//
//        List<Rating> ratingList = CSVHelper.csvToRatings(file.getInputStream());
//        when(ratingRepository.saveAll(ratingList)).thenReturn(ratingList);
//
//        List<Rating> savedList = ratingService.saveRatingsFromCsvFile(file);
//
//        verify(ratingRepository, times(1)).saveAll(ratingList);
//        assertEquals(ratingList.size(), savedList.size());
//    }
}
