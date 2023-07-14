package first.ep.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import first.ep.project.model.Rating;

import java.util.List;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {
    @Query("""
           SELECT r FROM Rating r
           WHERE r.club_id = :clubId
           """)
    List<Rating> getRatingsByClubId(@Param("clubId") Long clubId);
    @Query("""
          SELECT AVG(r.value) FROM Rating r WHERE r.club_id = :clubId
          """)
    Double getAverageRatingByClubId(@Param("clubId") Long clubId);
}
