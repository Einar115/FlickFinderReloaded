package mx.primis.auth_service.repositories;

import mx.primis.auth_service.model.entities.RefreshTokenEntity;
import mx.primis.auth_service.model.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, Long> {
    Optional<RefreshTokenEntity> findByUser(UserEntity userEntity);
    Optional<RefreshTokenEntity> findByToken(String token);
    void deleteByUserId(Long userId);
    void deleteByToken(String token);
}
