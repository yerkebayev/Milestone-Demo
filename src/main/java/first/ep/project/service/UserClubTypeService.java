package first.ep.project.service;

import org.springframework.web.multipart.MultipartFile;
import first.ep.project.model.UserClubType;

public interface UserClubTypeService{
    UserClubType saveUserClubType(UserClubType uc);
    Long deleteUserClubType(Long userId);
    void importUserClubTypeCsv(MultipartFile file);

}
