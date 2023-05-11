package unist.ep.milestone2.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import unist.ep.milestone2.job.CSVHelper;
import unist.ep.milestone2.model.ClubType;
import unist.ep.milestone2.model.Rating;
import unist.ep.milestone2.repository.TypeRepository;
import unist.ep.milestone2.service.TypeService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class TypeServiceImpl implements TypeService {
    private final TypeRepository typeRepository;
    TypeServiceImpl(TypeRepository typeRepository) {
        this.typeRepository = typeRepository;
    }
    @Override
    public List<ClubType> getAllClubTypes() {
        return typeRepository.findAll();
    }

    @Override
    public Optional<ClubType> getClubTypeById(Long id) {
        return typeRepository.findById(id);
    }

    @Override
    public ClubType saveClubType(ClubType clubType) {
        return null;
    }

    @Override
    public Long deleteClubTypeById(Long id) {
        return null;
    }

    @Override
    public void importTypeCsv(MultipartFile file) {
        try {
            List<ClubType> types = CSVHelper.csvToClubTypes(file.getInputStream());
            typeRepository.saveAll(types);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
