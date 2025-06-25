package mx.primis.interaction_service.model.dto.requests;

public record FavoriteMoviesRequest(
        Long userId,
        Long movieId
) {
}
