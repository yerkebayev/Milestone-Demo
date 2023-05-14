package unist.ep.milestone2.service;

import org.springframework.web.multipart.MultipartFile;
import unist.ep.milestone2.model.UserClubType;

public interface UserClubTypeService{
    UserClubType saveUserClubType(UserClubType uc);
    Long deleteUserClubType(Long id);
    void importUserClubTypeCsv(MultipartFile file);

}
