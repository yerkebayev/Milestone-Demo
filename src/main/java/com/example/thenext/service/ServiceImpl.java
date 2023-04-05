package com.example.thenext.service;

import com.example.thenext.job.CSVHelper;
import com.example.thenext.models.Employee;
import com.example.thenext.models.Movie;
import com.example.thenext.models.User;
import com.example.thenext.models.Rating;
import com.example.thenext.repository.EmployeeRepository;
import com.example.thenext.repository.MovieRepository;
import com.example.thenext.repository.RatingRepository;
import com.example.thenext.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
public class ServiceImpl implements Service {

    private final MovieRepository movieRepository;
    private final UserRepository userRepository;
    private final RatingRepository ratingRepository;
    private final EmployeeRepository employeeRepository;


    public ServiceImpl(MovieRepository movieRepository, UserRepository userRepository, RatingRepository ratingRepository, EmployeeRepository employeeRepository) {
        this.movieRepository = movieRepository;
        this.userRepository = userRepository;
        this.ratingRepository = ratingRepository;
        this.employeeRepository = employeeRepository;
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
    public List<Movie> getMoviesWithRatingMoreThan(@Param("rate")Integer rate) {
        List<Long> idOfMovies =  movieRepository.findMoviesWithAverageRatingGreaterThan(rate);
        List<Movie> res = new ArrayList<>();
        idOfMovies.forEach(i -> res.add(getMovie(i).get()));
        return res;
    }
    @Override
    public void importMovieCsv(MultipartFile file) {
        try {
            List<Movie> movies = CSVHelper.csvToMovies(file.getInputStream());
            movieRepository.saveAll(movies);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void importUserCsv(MultipartFile file) {
        try {
            List<User> users = CSVHelper.csvToUsers(file.getInputStream());
            userRepository.saveAll(users);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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

    @Override
    public List<Employee> allEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public Optional<Employee> getEmployee(Long id) {
        return employeeRepository.findById(id);
    }

    @Override
    public void saveEmployee(Employee employee) {
        employeeRepository.save(employee);
    }

    @Override
    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }
}
