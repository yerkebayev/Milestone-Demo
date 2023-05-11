package unist.ep.milestone2.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import unist.ep.milestone2.job.CSVHelper;
import unist.ep.milestone2.model.ClubType;
import unist.ep.milestone2.model.User;
import unist.ep.milestone2.model.UserClubType;
import unist.ep.milestone2.repository.UserClubTypeRepository;
import unist.ep.milestone2.service.UserClubTypeService;

import java.io.IOException;
import java.util.List;

@Service
public class UserClubTypeServiceImpl implements UserClubTypeService {
    private final UserClubTypeRepository userClubTypeRepository;

    public UserClubTypeServiceImpl(UserClubTypeRepository userClubTypeRepository) {
        this.userClubTypeRepository = userClubTypeRepository;
    }

    @Override
    public UserClubType saveAllTypes(UserClubType uc) {
        return userClubTypeRepository.save(uc);
    }

    @Override
    public void importUserClubTypeCsv(MultipartFile file) {
        try {
            List<UserClubType> userTypes = CSVHelper.csvToUserClubTypes(file.getInputStream());
            userClubTypeRepository.saveAll(userTypes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
