package unist.ep.milestone2.service;

import org.springframework.web.multipart.MultipartFile;
import unist.ep.milestone2.model.ClubType;

import java.util.List;

public interface TypeService {
    List<ClubType> getAllClubTypes();
    ClubType getClubTypeById(Integer id);
    ClubType saveClubType(ClubType clubType);
    Long deleteClubTypeById(Long id);
    void importTypeCsv(MultipartFile file);

}
