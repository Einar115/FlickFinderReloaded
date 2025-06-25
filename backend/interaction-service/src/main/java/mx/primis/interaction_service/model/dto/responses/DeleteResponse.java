package mx.primis.interaction_service.model.dto.responses;

public record DeleteResponse(
        boolean success,
        String message,
        Long id
) {
}
