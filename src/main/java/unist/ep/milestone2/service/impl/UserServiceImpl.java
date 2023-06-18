package unist.ep.milestone2.service.impl;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import unist.ep.milestone2.job.CSVHelper;
import unist.ep.milestone2.model.ClubType;
import unist.ep.milestone2.model.User;
import unist.ep.milestone2.repository.UserRepository;
import unist.ep.milestone2.service.UserService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public List<User> getAllUsers() {
        Sort sortById = Sort.by(Sort.Direction.ASC, "id");
        return userRepository.findAll(sortById);
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public Long deleteUserById(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return 1L;
        }
        return -1L;
    }

    @Override
    public List<ClubType> getPreferredClubTypes(User user) {
        return userRepository.getPreferredClubTypes(user.getId());
    }

    @Override
    public List<Integer> getPreferredClubTypesInInteger(User user) {
        return userRepository.getPreferredClubTypesInteger(user.getId());
    }

    @Override
    public void importUserCsv(MultipartFile file) {
        try {
            List<User> users = CSVHelper.csvToUsers(file.getInputStream());
            userRepository.saveAll(users);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.getUserByEmail(email);
    }

    @Override
    public User getUserByNameAndSurname(String name, String surname) {
        return userRepository.getUserByNameAndSurname(name, surname);
    }
}
