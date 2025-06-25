package mx.primis.interaction_service.model.dto.responses;

public record RecommendationAlbumsResponse(
        Long recommendationId,
        Long userId,
        Long albumId,
        float relevanceScore
) {
}
