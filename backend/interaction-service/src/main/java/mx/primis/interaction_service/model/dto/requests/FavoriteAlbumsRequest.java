package mx.primis.interaction_service.model.dto.requests;

public record FavoriteAlbumsRequest(
        Long userId,
        Long albumId
) {
}
