package first.ep.project.repository;

import first.ep.project.model.Club;
import first.ep.project.model.User;
import java.util.ArrayList;
import java.util.List;

public class HomeResponse {
    private List<Club> allClubs = new ArrayList<>();
    private List<Club> preferredClubs = new ArrayList<>();
    private User user;

    public HomeResponse() {}

    public HomeResponse(List<Club> allClubs, List<Club> preferredClubs, User user) {
        this.allClubs = allClubs;
        this.preferredClubs = preferredClubs;
        this.user = user;
    }

    public List<Club> getAllClubs() {
        return allClubs;
    }

    public void setAllClubs(List<Club> allClubs) {
        this.allClubs = allClubs;
    }

    public List<Club> getPreferredClubs() {
        return preferredClubs;
    }

    public void setPreferredClubs(List<Club> preferredClubs) {
        this.preferredClubs = preferredClubs;
    }

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
}
