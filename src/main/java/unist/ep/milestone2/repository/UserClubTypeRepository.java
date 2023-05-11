package unist.ep.milestone2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import unist.ep.milestone2.model.UserClubType;

@Repository
public interface UserClubTypeRepository extends JpaRepository<UserClubType, Long> {
}
