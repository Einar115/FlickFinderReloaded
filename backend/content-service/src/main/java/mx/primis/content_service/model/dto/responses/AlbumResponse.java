package mx.primis.content_service.model.dto.responses;

import java.util.Set;

public record AlbumResponse(
        Long id,
        String name,
        Long artistId,
        String artistName,
        Integer year,
        String spotifyId,
        String image,
        Set<TrackResponse> tracks
) {
}