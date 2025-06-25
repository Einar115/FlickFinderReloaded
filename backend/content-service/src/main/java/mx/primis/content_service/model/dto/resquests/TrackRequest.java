package mx.primis.content_service.model.dto.resquests;

public record TrackRequest(
        String name,
        Long albumId,
        Integer trackNumber,
        Integer discNumber,
        Boolean explicit,
        Integer durationMs,
        String spotifyId
) {
}
