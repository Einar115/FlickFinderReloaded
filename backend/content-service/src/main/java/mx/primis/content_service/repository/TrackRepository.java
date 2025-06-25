package mx.primis.content_service.repository;

import mx.primis.content_service.model.entities.TrackEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrackRepository extends JpaRepository<TrackEntity, Long> {
    List<TrackEntity> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
