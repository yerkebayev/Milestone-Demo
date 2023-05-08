package unist.ep.milestone2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import unist.ep.milestone2.models.Type;

public interface TypeRepository extends JpaRepository<Type, Long> {
}
