package com.example.thenext.models;

import jakarta.persistence.Column;
import jakarta.persistence.Table;

import java.sql.Timestamp;

@Table(name = "Ratings")
public class Rating {
    @Column
    private Long UserID;
    @Column
    private Long MovieID;
    @Column
    private Integer Ratings;
    @Column
    private Timestamp Timestamp;
}
