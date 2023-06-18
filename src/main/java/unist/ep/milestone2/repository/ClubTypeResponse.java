package unist.ep.milestone2.repository;

import unist.ep.milestone2.model.ClubType;

import java.util.List;

public class ClubTypeResponse {
    private List<ClubType> clubTypes;
    private List<Integer> preferredClubTypes;

    public List<ClubType> getClubTypes() {
        return clubTypes;
    }

    public void setClubTypes(List<ClubType> clubTypes) {
        this.clubTypes = clubTypes;
    }

    public List<Integer> getPreferredClubTypes() {
        return preferredClubTypes;
    }

    public void setPreferredClubTypes(List<Integer> preferredClubTypes) {
        this.preferredClubTypes = preferredClubTypes;
    }

    public ClubTypeResponse() {
    }

    public ClubTypeResponse(List<ClubType> clubTypes, List<Integer> preferredClubTypes) {
        this.clubTypes = clubTypes;
        this.preferredClubTypes = preferredClubTypes;
    }
}
