package first.ep.project.service.impl;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import first.ep.project.job.CSVHelper;
import first.ep.project.model.ClubType;
import first.ep.project.repository.TypeRepository;
import first.ep.project.service.TypeService;

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
        Sort sortById = Sort.by(Sort.Direction.ASC, "id");
        return typeRepository.findAll(sortById);
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
