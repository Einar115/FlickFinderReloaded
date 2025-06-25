package mx.primis.auth_service.model.dto.requests;

public record AuthRequest(
        String email,
        String password
) {
}
