package first.ep.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import first.ep.project.model.ClubType;

@Repository
public interface TypeRepository extends JpaRepository<ClubType, Long> {

    @Query("""
           SELECT t FROM ClubType t
           WHERE t.name = :name
           """)
    ClubType getTypeByName(@Param("name") String name);
}
