package unist.ep.milestone2.service;

import org.springframework.web.multipart.MultipartFile;
import unist.ep.milestone2.model.User;
import unist.ep.milestone2.model.UserClubType;

import java.util.List;

public interface UserClubTypeService {
    UserClubType saveAllTypes(UserClubType uc);
    void importUserClubTypeCsv(MultipartFile file);

}
