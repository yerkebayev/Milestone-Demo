package com.example.thenext.service;

import com.example.thenext.models.Movie;
import com.example.thenext.models.Rating;
import com.example.thenext.models.User;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface Service {
    List<Movie> allMovies();
    Optional<Movie> getMovie(Long id);
    void saveMovie(Movie movie);
    void deleteMovie(Long id);

    List<User> allUsers();
    Optional<User> getUser(Long id);
    void saveUser(User user);
    void deleteUser(Long id);

    List<Movie> getMoviesWithRatingMoreThan(Integer rate);

}
