package mx.primis.content_service.repository;

import mx.primis.content_service.model.entities.MovieEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<MovieEntity, Long> {
    List<MovieEntity> findByTitleContainingIgnoreCase(String title, Pageable pageable);

    @Query("SELECT DISTINCT m FROM MovieEntity m LEFT JOIN FETCH m.genres WHERE m.id IN :ids ORDER BY m.id")
    List<MovieEntity> findAllByIdWithGenres(@Param("ids") List<Long> ids);

    @EntityGraph(attributePaths = {"genres"})
    @Query("SELECT m FROM MovieEntity m WHERE m.id = :id")
    Optional<MovieEntity> findByIdWithGenres(@Param("id") Long id);
}
