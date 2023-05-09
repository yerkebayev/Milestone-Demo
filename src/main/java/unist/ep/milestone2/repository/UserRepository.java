package unist.ep.milestone2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import unist.ep.milestone2.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("""
           SELECT DISTINCT u FROM User u
           WHERE u.email = :email
           """)
    User getUserByEmail(@Param("email") String email);

}
