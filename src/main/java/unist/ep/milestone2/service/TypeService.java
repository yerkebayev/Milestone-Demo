package unist.ep.milestone2.service;

import org.springframework.web.multipart.MultipartFile;
import unist.ep.milestone2.model.ClubType;

import java.util.List;
import java.util.Optional;

public interface TypeService {
    List<ClubType> getAllClubTypes();
    Optional<ClubType> getClubTypeById(Long id);
    ClubType saveClubType(ClubType clubType);
    Long deleteClubTypeById(Long id);
    void importTypeCsv(MultipartFile file);

}
