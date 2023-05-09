package unist.ep.milestone2.service;

import unist.ep.milestone2.model.Club;
import java.util.List;
import java.util.Optional;

public interface ClubService {
    List<Club> getAllClubs();
    Optional<Club> getClubById(Long id);
    Optional<Club> getClubByName(String name);
    Club saveClub(Club club);
    Long deleteClubById(Long id);
}
