package com.example.thenext.controllers;

import com.example.thenext.job.CSVHelper;
import com.example.thenext.models.Movie;
import com.example.thenext.service.Service;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/ratings")
public class RatingController {
    private final Service ratingService;

    public RatingController(Service ratingService) {
        this.ratingService = ratingService;
    }

    @GetMapping("{rate}")
    public ResponseEntity<List<Movie>> getByThisRating(@PathVariable("rate") int rate) {
        if (rate < 1 || rate > 5) {
            return ResponseEntity.badRequest().build();
        }

        List<Movie> movies = ratingService.getMoviesWithRatingMoreThan(rate);
        return ResponseEntity.ok(movies);
    }

    @PostMapping("/upload")
    public String uploadFile() throws IOException {
        FileSystemResource csvResource = new FileSystemResource("src/main/resources/ratings.csv");
        MultipartFile file = new MockMultipartFile(
                Objects.requireNonNull(csvResource.getFilename()),
                csvResource.getFile().getAbsolutePath(),
                "text/csv",
                csvResource.getInputStream()
        );
        String message;

        if (CSVHelper.hasCSVFormat(file)) {
            try {
                ratingService.importRatingCsv(file);

                message = "Uploaded the file successfully: " + file.getOriginalFilename();
                return message;
            } catch (Exception e) {
                message = "Could not upload the file: " + file.getOriginalFilename() + "!";
                return message;
            }
        }

        message = "Please upload a csv file!";
        return message;
    }
}
