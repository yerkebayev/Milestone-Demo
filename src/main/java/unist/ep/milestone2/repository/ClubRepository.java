package unist.ep.milestone2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import unist.ep.milestone2.model.Club;

@Repository
public interface ClubRepository extends JpaRepository<Club, Long> {
}
