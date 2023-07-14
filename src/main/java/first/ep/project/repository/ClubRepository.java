package first.ep.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import first.ep.project.model.Club;

import java.util.List;

@Repository
public interface ClubRepository extends JpaRepository<Club, Long> {
    @Query("""
          SELECT c FROM Club c WHERE c.clubType_id = :clubTypeId
          """)
    List<Club> getClubsByClubTypes(@Param("clubTypeId") Long clubTypeId);

    @Query("""
           SELECT DISTINCT c FROM Club c
           WHERE c.email = :email
           """)
    Club getClubByEmail(@Param("email") String email);
}
