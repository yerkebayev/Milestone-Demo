package unist.ep.milestone2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import unist.ep.milestone2.model.Club;
import unist.ep.milestone2.model.Type;

@Repository
public interface ClubRepository extends JpaRepository<Club, Long> {
    @Query("""
           SELECT c FROM Club c
           WHERE c.name = :name
           """)
    Club getClubByName(@Param("name") String name);
}
