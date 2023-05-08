package unist.ep.milestone2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import unist.ep.milestone2.model.Type;

@Repository
public interface TypeRepository extends JpaRepository<Type, Long> {

    @Query("""
           SELECT t FROM Type t
           WHERE t.name = :name
           """)
    Type getTypeByName(@Param("name") String name);
}
