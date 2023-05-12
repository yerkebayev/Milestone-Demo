package unist.ep.milestone2.repository;

import unist.ep.milestone2.model.Club;

import java.util.List;

public class HomeResponse {
    List<Club> allClubs;
    List<Club> preferredClubs;

    public HomeResponse() {
    }

    public HomeResponse(List<Club> allClubs, List<Club> preferredClubs) {
        this.allClubs = allClubs;
        this.preferredClubs = preferredClubs;
    }
}
