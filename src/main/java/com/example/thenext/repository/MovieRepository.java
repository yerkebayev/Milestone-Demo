package com.example.thenext.repository;

import com.example.thenext.models.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    @Query("""
            SELECT m.id, AVG(r.Ratings) AS avg_rating
            FROM Movie m
            JOIN Rating r ON m.id = r.MovieID
            GROUP BY m.id
            HAVING AVG(r.Ratings) >= :rate""")
    List<Long> findMoviesWithAverageRatingGreaterThan(@Param("rate") Integer rate);
}
