package mx.primis.interaction_service.repositories;

import mx.primis.interaction_service.model.entities.RecommendationMoviesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecommendationMovieRepository extends JpaRepository<RecommendationMoviesEntity, Long> {
    List<RecommendationMoviesEntity> findByUserIdOrderByRelevanceScoreDesc(Long userId);
    void deleteByUserId(Long userId);
}
