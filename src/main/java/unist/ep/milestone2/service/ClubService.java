package unist.ep.milestone2.service;

import org.springframework.web.multipart.MultipartFile;
import unist.ep.milestone2.model.Club;
import unist.ep.milestone2.model.ClubType;

import java.util.List;
import java.util.Optional;

public interface ClubService {
    List<Club> getAllClubs();
    Optional<Club> getClubById(Long id);
    Club getClubByEmail(String email);
    Club saveClub(Club club);
    Long deleteClubById(Long id);
    List<Club> getClubsByClubTypes(List<ClubType> list);
    void importClubCsv(MultipartFile file);
}
