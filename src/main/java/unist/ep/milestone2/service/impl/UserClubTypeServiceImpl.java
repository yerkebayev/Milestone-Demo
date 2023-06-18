package unist.ep.milestone2.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import unist.ep.milestone2.job.CSVHelper;
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
    public UserClubType saveUserClubType(UserClubType uc) {
        return userClubTypeRepository.save(uc);
    }

    @Override
    @Transactional
    public Long deleteUserClubType(Long id) {
        userClubTypeRepository.deleteUserClubTypeByUserId(id);
        return 1L;
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
