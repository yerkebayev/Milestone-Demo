package first.ep.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import first.ep.project.model.ClubType;
import first.ep.project.model.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("""
           SELECT DISTINCT u FROM User u
           WHERE u.email = :email
           """)
    User getUserByEmail(@Param("email") String email);
    @Query("""
           SELECT DISTINCT u FROM User u
           WHERE u.name = :name and u.surname = :surname
           """)
    User getUserByNameAndSurname(@Param("name") String name, @Param("surname") String surname);

    @Query("""
           SELECT ct FROM UserClubType uc
           JOIN ClubType ct ON ct.id = uc.clubType_id
           WHERE uc.user_id = :user_id
           """)
    List<ClubType> getPreferredClubTypes(@Param("user_id") Long user_id);

    @Query("""
           SELECT ct.id FROM UserClubType uc
           JOIN ClubType ct ON ct.id = uc.clubType_id
           WHERE uc.user_id = :user_id
           """)
    List<Integer> getPreferredClubTypesInteger(@Param("user_id") Long user_id);

}
