package mx.primis.interaction_service.model.dto.responses;

import java.time.LocalDateTime;

public record FavoriteAlbumsResponse(
        Long favoritesAlbumId,
        Long userId,
        Long albumId,
        LocalDateTime addedAt
) {
}
