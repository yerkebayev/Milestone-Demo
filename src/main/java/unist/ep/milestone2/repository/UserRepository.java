package unist.ep.milestone2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import unist.ep.milestone2.model.ClubType;
import unist.ep.milestone2.model.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("""
           SELECT DISTINCT u FROM User u
           WHERE u.email = :email
           """)
    User getUserByEmail(@Param("email") String email);

    @Query("""
           SELECT uc.clubType_id FROM UserClubType uc
           WHERE uc.user_id = :user_id
           """)
    List<Long> getPreferredClubTypes(@Param("user_id") Long user_id);

}
