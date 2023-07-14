package first.ep.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import first.ep.project.model.UserClubType;

@Repository
public interface UserClubTypeRepository extends JpaRepository<UserClubType, Long> {
    @Modifying
    @Query("DELETE FROM UserClubType u WHERE u.user_id = :userId")
    void deleteUserClubTypeByUserId(@Param("userId") Long userId);

}