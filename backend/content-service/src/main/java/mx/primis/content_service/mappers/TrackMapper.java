package mx.primis.content_service.mappers;

import mx.primis.content_service.model.dto.responses.TrackResponse;
import mx.primis.content_service.model.dto.resquests.TrackRequest;
import mx.primis.content_service.model.entities.AlbumEntity;
import mx.primis.content_service.model.entities.TrackEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TrackMapper {
    public static TrackEntity toEntity(TrackRequest dto) {
        TrackEntity entity = new TrackEntity();
        entity.setName(dto.name());

        AlbumEntity album = new AlbumEntity();
        album.setId(dto.albumId());
        entity.setAlbum(album);

        entity.setTrackNumber(dto.trackNumber());
        entity.setDiscNumber(dto.discNumber());
        entity.setExplicit(dto.explicit());
        entity.setDurationMs(dto.durationMs());
        entity.setSpotifyId(dto.spotifyId());
        return entity;
    }

    public static TrackResponse toResponse(TrackEntity entity) {
        return new TrackResponse(
                entity.getId(),
                entity.getName(),
                entity.getAlbum().getId(),
                entity.getAlbum().getName(),
                entity.getTrackNumber(),
                entity.getDiscNumber(),
                entity.getExplicit(),
                entity.getDurationMs(),
                entity.getSpotifyId()
        );
    }

    public static List<TrackEntity> toEntityList(List<TrackRequest> dtos) {
        return dtos.stream()
                .map(TrackMapper::toEntity)
                .collect(Collectors.toList());
    }

    public static List<TrackResponse> toResponseList(List<TrackEntity> entities) {
        return entities.stream()
                .map(TrackMapper::toResponse)
                .collect(Collectors.toList());
    }
}
