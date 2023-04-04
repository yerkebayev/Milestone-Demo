package com.example.thenext.controllers;

import com.example.thenext.models.Movie;
import com.example.thenext.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/movies")
public class MovieController {
    @Autowired
    private Service movieService;

    @GetMapping("/")
    public String getPage() {
        return "Welcome";
    }
    @GetMapping
    public List<Movie> getMovies() {
        return movieService.allMovies();
    }
    @GetMapping("/{id}")
    public Movie getMovie(@PathVariable long id) {
        return movieService.getMovie(id).get();
    }
    @PostMapping
    public String saveMovie(@RequestBody Movie movie) {
        movieService.saveMovie(movie);
        System.out.println(movie);
        return "Saved...";
    }
    @PutMapping("/{id}")
    public String updateMovie(@PathVariable long id, @RequestBody Movie movie){
        Optional<Movie> optionalMovie = movieService.getMovie(id);
        Movie mv = optionalMovie.get();
        mv.setTitle(movie.getTitle());
        mv.setGenre(movie.getGenre());
        movieService.saveMovie(mv);
        return "Updated...";
    }
    @DeleteMapping("/{id}")
    public String deleteMovie(@PathVariable long id) {
        movieService.deleteMovie(id);
        return "Deleted...";
    }
}
