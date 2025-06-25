package mx.primis.content_service.repository;

import mx.primis.content_service.model.entities.AlbumEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlbumRepository extends JpaRepository<AlbumEntity, Long> {
    List<AlbumEntity> findByNameContainingIgnoreCase(String name, Pageable pageable);

    @Query("SELECT DISTINCT a FROM AlbumEntity a LEFT JOIN FETCH a.tracks WHERE a.id IN :ids")
    List<AlbumEntity> findAllByIdWithTracks(@Param("ids") List<Long> ids);

    @EntityGraph(attributePaths = {"tracks", "artist"})
    @Query("SELECT a FROM AlbumEntity a WHERE a.id = :id")
    Optional<AlbumEntity> findByIdWithTracks(@Param("id") Long id);
}
