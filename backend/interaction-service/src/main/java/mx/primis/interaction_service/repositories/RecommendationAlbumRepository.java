package mx.primis.interaction_service.repositories;

import mx.primis.interaction_service.model.entities.RecommendationAlbumsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecommendationAlbumRepository extends JpaRepository<RecommendationAlbumsEntity, Long> {
    List<RecommendationAlbumsEntity> findByUserIdOrderByRelevanceScoreDesc(Long userId);
    void deleteByUserId(Long userId);
}
