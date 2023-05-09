package unist.ep.milestone2.service.impl;


import org.springframework.stereotype.Service;
import unist.ep.milestone2.model.Club;
import unist.ep.milestone2.repository.ClubRepository;
import unist.ep.milestone2.service.ClubService;
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


}
