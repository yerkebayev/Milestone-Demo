package com.example.thenext.service;

import com.example.thenext.models.Movie;
import com.example.thenext.models.User;
import com.example.thenext.models.Rating;
import com.example.thenext.repository.MovieRepository;
import com.example.thenext.repository.UserRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
public class ServiceImpl implements Service {

    private final MovieRepository movieRepository;
    private final UserRepository userRepository;

    public ServiceImpl(MovieRepository movieRepository, UserRepository userRepository) {
        this.movieRepository = movieRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Movie> allMovies() {
        return movieRepository.findAll();
    }

    @Override
    @Transactional
    public Optional<Movie> getMovie(Long id) {
        return movieRepository.findById(id);
    }

    @Override
    @Transactional
    public void saveMovie(Movie movie) {
        movieRepository.save(movie);
    }

    @Override
    public void deleteMovie(Long id) {
        movieRepository.deleteById(id);
    }

    @Override
    public List<User> allUsers() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public Optional<User> getUser(Long id) {
        return userRepository.findById(id);
    }

    @Override
    @Transactional
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    @Query("SELECT r.movie FROM Rating r WHERE r.rating >= :rating")
    public List<Movie> getMoviesWithRatingMoreThan(Integer rate) {
        return null;
    }
}
