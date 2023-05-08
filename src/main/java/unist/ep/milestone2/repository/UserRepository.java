package unist.ep.milestone2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import unist.ep.milestone2.models.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
