package unist.ep.milestone2.service;

import unist.ep.milestone2.model.User;
import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getAllUsers();
    Optional<User> getUserById(Long id);
    User saveUser(User user);
    Long deleteUserById(Long id);
    Boolean checkUser(String email, String password);
}
