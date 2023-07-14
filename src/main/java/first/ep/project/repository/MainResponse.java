package first.ep.project.repository;

import first.ep.project.model.Club;
import first.ep.project.model.Rating;
import first.ep.project.model.User;

import java.util.List;

public class MainResponse {
    private Club club;
    private User user;
    private List<Rating> ratings;
    private Double ratingAverage;
    private List<Club> recommendedClubs;


    public MainResponse() {
    }

    public MainResponse(Club club, User user, List<Rating> ratings, Double ratingAverage, List<Club> recommendedClubs) {
        this.club = club;
        this.user = user;
        this.ratings = ratings;
        this.ratingAverage = ratingAverage;
        this.recommendedClubs = recommendedClubs;
    }

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }
    public Double getRatingAverage() {
        return ratingAverage;
    }

    public void setRatingAverage(Double ratingAverage) {
        this.ratingAverage = ratingAverage;
    }

    public List<Club> getRecommendedClubs() {
        return recommendedClubs;
    }

    public void setRecommendedClubs(List<Club> recommendedClubs) {
        this.recommendedClubs = recommendedClubs;
    }
}
