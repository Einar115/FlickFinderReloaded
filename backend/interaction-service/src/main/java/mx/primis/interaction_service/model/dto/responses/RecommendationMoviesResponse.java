package mx.primis.interaction_service.model.dto.responses;

public record RecommendationMoviesResponse(
        Long recommendationId,
        Long userId,
        Long movieId,
        float relevanceScore
) {
}
