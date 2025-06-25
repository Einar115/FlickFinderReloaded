package mx.primis.auth_service.services;

import mx.primis.auth_service.model.dto.requests.AuthRequest;
import mx.primis.auth_service.model.dto.requests.TokenRequest;
import mx.primis.auth_service.model.dto.responses.AuthResponse;
import mx.primis.auth_service.model.dto.responses.TokenValidationResponse;
import mx.primis.auth_service.model.entities.RefreshTokenEntity;

public interface AuthService {
    AuthResponse authenticate(AuthRequest authRequest);
    AuthResponse refreshToken(TokenRequest tokenRequest);
    void logout(String refreshToken);
    RefreshTokenEntity verifyExpiration(RefreshTokenEntity token);
    TokenValidationResponse validate(TokenRequest request);
}
