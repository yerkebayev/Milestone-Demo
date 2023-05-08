package unist.ep.milestone2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import unist.ep.milestone2.models.Club;

public interface ClubRepository extends JpaRepository<Club, Long> {
}
