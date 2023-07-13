package first.ep.project.service.impl;


import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import first.ep.project.job.CSVHelper;
import first.ep.project.model.Club;
import first.ep.project.model.ClubType;
import first.ep.project.repository.ClubRepository;
import first.ep.project.service.ClubService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ClubServiceImpl implements ClubService {
    private final ClubRepository clubRepository;

    public ClubServiceImpl(ClubRepository clubRepository) {
        this.clubRepository = clubRepository;
    }
    @Override
    public List<Club> getAllClubs() {
        Sort sortById = Sort.by(Sort.Direction.ASC, "id");
        return clubRepository.findAll(sortById);
    }

    @Override
    public Optional<Club> getClubById(Long id) {
        return clubRepository.findById(id);
    }

    @Override
    public Club getClubByEmail(String email) {
        return clubRepository.getClubByEmail(email);
    }

    @Override
    public Club saveClub(Club club) {
        return clubRepository.save(club);
    }

    @Override
    public Long deleteClubById(Long id) {
        if (clubRepository.existsById(id)){
            clubRepository.deleteById(id);
            return 1L;
        }
        return -1L;
    }

    @Override
    public List<Club> getClubsByClubTypes(List<ClubType> list) {
        List<Club> listOfClubs = new ArrayList<>();
        for(ClubType ct : list){
            listOfClubs.addAll(clubRepository.getClubsByClubTypes(ct.getId()));
        }
        return listOfClubs;
    }

    @Override
    public void importClubCsv(MultipartFile file) {
        try {
            List<Club> clubs = CSVHelper.csvToClubs(file.getInputStream());
            clubRepository.saveAll(clubs);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
