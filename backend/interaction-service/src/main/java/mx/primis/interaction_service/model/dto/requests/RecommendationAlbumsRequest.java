package mx.primis.interaction_service.model.dto.requests;

public record RecommendationAlbumsRequest(
        Long userId,
        Long albumId,
        float relevanceScore
) {
}
