package unist.ep.milestone2.repository;

import unist.ep.milestone2.model.Club;

import java.util.List;

public class HomeResponse {
    private List<Club> allClubs;
    private List<Club> preferredClubs;

    public HomeResponse() {
    }

    public HomeResponse(List<Club> allClubs, List<Club> preferredClubs) {
        this.allClubs = allClubs;
        this.preferredClubs = preferredClubs;
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
}
