package mx.primis.interaction_service.model.dto.responses;

public record TrackResponse(
        Long id,
        String name,
        Long albumId,
        String albumName,
        Integer trackNumber,
        Integer discNumber,
        Boolean explicit,
        Integer durationMs,
        String spotifyId
) {
}
