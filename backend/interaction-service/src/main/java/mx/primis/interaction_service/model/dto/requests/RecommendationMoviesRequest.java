package mx.primis.interaction_service.model.dto.requests;

public record RecommendationMoviesRequest(
        Long userId,
        Long movieId,
        float relevanceScore
) {
}
