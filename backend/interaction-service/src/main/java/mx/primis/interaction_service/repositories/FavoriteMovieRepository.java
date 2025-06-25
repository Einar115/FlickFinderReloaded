package mx.primis.interaction_service.repositories;

import mx.primis.interaction_service.model.entities.FavoriteMoviesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavoriteMovieRepository extends JpaRepository<FavoriteMoviesEntity, Long> {
    List<FavoriteMoviesEntity> findByUserId(Long userId);
    Optional<FavoriteMoviesEntity> findByUserIdAndMovieId(Long userId, Long movieId);
    void deleteByUserIdAndMovieId(Long userId, Long movieId);
}
