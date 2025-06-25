package mx.primis.auth_service.model.dto.responses;

public record AuthResponse(
        String accessToken,
        String refreshToken,
        Long userId,
        String username,
        String role
) {
}
