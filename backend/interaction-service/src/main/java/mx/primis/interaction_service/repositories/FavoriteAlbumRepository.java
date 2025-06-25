package mx.primis.interaction_service.repositories;

import mx.primis.interaction_service.model.entities.FavoriteAlbumsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavoriteAlbumRepository extends JpaRepository<FavoriteAlbumsEntity, Long> {
    List<FavoriteAlbumsEntity> findByUserId(Long userId);
    Optional<FavoriteAlbumsEntity> findByUserIdAndAlbumId(Long userId, Long albumId);
    void deleteByUserIdAndAlbumId(Long userId, Long albumId);
}
