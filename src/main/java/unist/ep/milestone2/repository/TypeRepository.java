package unist.ep.milestone2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import unist.ep.milestone2.model.ClubType;

@Repository
public interface TypeRepository extends JpaRepository<ClubType, Long> {

    @Query("""
           SELECT t FROM ClubType t
           WHERE t.name = :name
           """)
    ClubType getTypeByName(@Param("name") String name);
}
