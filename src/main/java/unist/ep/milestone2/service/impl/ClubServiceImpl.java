package unist.ep.milestone2.service.impl;


import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import unist.ep.milestone2.job.CSVHelper;
import unist.ep.milestone2.model.Club;
import unist.ep.milestone2.model.ClubType;
import unist.ep.milestone2.repository.ClubRepository;
import unist.ep.milestone2.service.ClubService;

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
        return clubRepository.findAll();
    }

    @Override
    public Optional<Club> getClubById(Long id) {
        return clubRepository.findById(id);
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
