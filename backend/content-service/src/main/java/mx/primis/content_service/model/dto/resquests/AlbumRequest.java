package mx.primis.content_service.model.dto.resquests;

public record AlbumRequest(
        String name,
        Long artistId,
        Integer year,
        String spotifyId,
        String image
) {
}
