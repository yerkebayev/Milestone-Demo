package com.example.thenext.models;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Table(name="ratings")
@Entity
@Data
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private Long UserID;
    @Column
    private Long MovieID;
    @Column
    private Integer Ratings;
    @Column
    private String Timestamp;

    public Rating(Long userID, Long movieID, Integer ratings, String timestamp) {
        UserID = userID;
        MovieID = movieID;
        Ratings = ratings;
        Timestamp = timestamp;
    }

    public Rating() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserID() {
        return UserID;
    }

    public void setUserID(Long userID) {
        UserID = userID;
    }

    public Long getMovieID() {
        return MovieID;
    }

    public void setMovieID(Long movieID) {
        MovieID = movieID;
    }

    public Integer getRatings() {
        return Ratings;
    }

    public void setRatings(Integer ratings) {
        Ratings = ratings;
    }

    public String getTimestamp() {
        return Timestamp;
    }

    public void setTimestamp(String timestamp) {
        Timestamp = timestamp;
    }
}
