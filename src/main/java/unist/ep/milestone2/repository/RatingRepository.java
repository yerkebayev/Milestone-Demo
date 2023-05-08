package unist.ep.milestone2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import unist.ep.milestone2.models.Rating;

public interface RatingRepository extends JpaRepository<Rating, Long> {
}
