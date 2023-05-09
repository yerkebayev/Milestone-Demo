package unist.ep.milestone2.repository;

import unist.ep.milestone2.model.Club;
import unist.ep.milestone2.model.Rating;
import unist.ep.milestone2.model.User;

public class MainResponse {
    private Club club;
    private User user;
    private Rating rating;

    public MainResponse() {
    }

    public MainResponse(Club club, User user, Rating rating) {
        this.club = club;
        this.user = user;
        this.rating = rating;
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

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }
}
