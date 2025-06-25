package mx.primis.auth_service.mappers;

import mx.primis.auth_service.model.dto.responses.AuthResponse;
import mx.primis.auth_service.model.entities.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class AuthMapper {
    public AuthResponse toDTO(UserEntity user, String token, String refreshToken) {
        return new AuthResponse(
                token,
                refreshToken,
                user.getId(),
                user.getUsername(),
                user.getRole() != null ? user.getRole().getName() : null
        );
    }
}
