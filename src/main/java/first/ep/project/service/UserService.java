package first.ep.project.service;

import org.springframework.web.multipart.MultipartFile;
import first.ep.project.model.ClubType;
import first.ep.project.model.User;
import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getAllUsers();
    Optional<User> getUserById(Long id);
    User getUserByEmail(String email);
    User getUserByNameAndSurname(String name, String surname);

    User saveUser(User user);
    User addUser(User user);
    Long deleteUserById(Long id);
    List<ClubType> getPreferredClubTypes(User user);
    List<Integer> getPreferredClubTypesInInteger(User user);
    void importUserCsv(MultipartFile file);
    User updatePassword(String newPassword, String oldPassword);
    boolean verifyPassword(String rawPassword, String encodedPassword);

}
