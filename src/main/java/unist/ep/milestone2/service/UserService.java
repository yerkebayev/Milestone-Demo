package unist.ep.milestone2.service;

import org.springframework.web.multipart.MultipartFile;
import unist.ep.milestone2.model.ClubType;
import unist.ep.milestone2.model.User;
import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getAllUsers();
    Optional<User> getUserById(Long id);
    User getUserByEmail(String email);

    User saveUser(User user);
    Long deleteUserById(Long id);
    List<ClubType> getPreferredClubTypes(User user);
    void importUserCsv(MultipartFile file);

}
