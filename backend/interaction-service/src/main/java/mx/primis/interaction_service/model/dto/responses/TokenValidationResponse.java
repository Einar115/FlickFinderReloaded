package mx.primis.interaction_service.model.dto.responses;

public record TokenValidationResponse(
        boolean valid,
        int userId,
        String username
) {
}
