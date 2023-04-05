package com.example.thenext.controllers;

import com.example.thenext.job.CSVHelper;
import com.example.thenext.models.Movie;
import com.example.thenext.service.Service;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/movies")
public class MovieController {
    private final Service movieService;

    public MovieController(Service movieService) {
        this.movieService = movieService;
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<Movie>> getMovies() {
        List<Movie> movies = movieService.allMovies();
        return new ResponseEntity<>(movies, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<Movie> getMovie(@PathVariable long id) {
        Optional<Movie> optionalMovie = movieService.getMovie(id);
        return optionalMovie.map(movie -> new ResponseEntity<>(movie, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Movie> saveMovie(@RequestBody Movie movie) {
        movieService.saveMovie(movie);
        return new ResponseEntity<>(movie, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Movie> updateMovie(@PathVariable long id, @RequestBody Movie movie){
        Optional<Movie> optionalMovie = movieService.getMovie(id);
        if (optionalMovie.isPresent()) {
            Movie mv = optionalMovie.get();
            mv.setTitle(movie.getTitle());
            mv.setGenre(movie.getGenre());
            movieService.saveMovie(mv);
            return new ResponseEntity<>(mv, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<String> deleteMovie(@PathVariable long id) {
        if (movieService.getMovie(id).isPresent()) {
            movieService.deleteMovie(id);
            return new ResponseEntity<>("Deleted...", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Movie not found.", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/upload")
    public String uploadFile() throws IOException {
        FileSystemResource csvResource = new FileSystemResource("src/main/resources/movies.csv");
        MultipartFile file = new MockMultipartFile(
                Objects.requireNonNull(csvResource.getFilename()),
                csvResource.getFile().getAbsolutePath(),
                "text/csv",
                csvResource.getInputStream()
        );
        String message;
        if (CSVHelper.hasCSVFormat(file)) {
            try {
                movieService.importMovieCsv(file);

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
