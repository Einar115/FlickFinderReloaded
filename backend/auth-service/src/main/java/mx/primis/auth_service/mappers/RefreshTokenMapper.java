package mx.primis.auth_service.mappers;

import mx.primis.auth_service.model.dto.requests.TokenRequest;
import mx.primis.auth_service.model.entities.RefreshTokenEntity;
import mx.primis.auth_service.model.entities.UserEntity;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class RefreshTokenMapper {
    public RefreshTokenEntity toEntity(TokenRequest dto, UserEntity user, Instant expiryDate) {
        RefreshTokenEntity entity = new RefreshTokenEntity();
        entity.setUser(user);
        entity.setToken(dto.token());
        entity.setExpiryDate(expiryDate);
        return entity;
    }
}
