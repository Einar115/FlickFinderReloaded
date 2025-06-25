package mx.primis.auth_service.model.dto.responses;

public record TokenValidationResponse(
        boolean valid,
        int userId,
        String username
) {
    public static TokenValidationResponse invalid() {
        return new TokenValidationResponse(false, 0, null);
    }
}
