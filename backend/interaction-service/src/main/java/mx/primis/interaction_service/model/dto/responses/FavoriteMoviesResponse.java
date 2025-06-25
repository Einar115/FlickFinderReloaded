package mx.primis.interaction_service.model.dto.responses;

import java.time.LocalDateTime;

public record FavoriteMoviesResponse(
        Long favoritesMoviesId,
        Long userId,
        Long movieId,
        LocalDateTime addedAt
) {
}
