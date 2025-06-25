package mx.primis.content_service.mappers;

import mx.primis.content_service.model.dto.responses.AlbumResponse;
import mx.primis.content_service.model.dto.resquests.AlbumRequest;
import mx.primis.content_service.model.entities.AlbumEntity;
import mx.primis.content_service.model.entities.ArtistEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AlbumMapper {
    public static AlbumEntity toEntity(AlbumRequest dto) {
        AlbumEntity entity = new AlbumEntity();
        entity.setName(dto.name());

        ArtistEntity artist = new ArtistEntity();
        artist.setId(dto.artistId());
        entity.setArtist(artist);

        entity.setYear(dto.year());
        entity.setSpotifyId(dto.spotifyId());
        entity.setImage(dto.image());
        return entity;
    }

    public static AlbumResponse toResponse(AlbumEntity entity) {
        return new AlbumResponse(
                entity.getId(),
                entity.getName(),
                entity.getArtist().getId(),
                entity.getArtist().getName(),
                entity.getYear(),
                entity.getSpotifyId(),
                entity.getImage(),
                entity.getTracks().stream()
                        .map(TrackMapper::toResponse)
                        .collect(Collectors.toSet())
        );
    }

    public static List<AlbumEntity> toEntityList(List<AlbumRequest> dtos) {
        return dtos.stream()
                .map(AlbumMapper::toEntity)
                .collect(Collectors.toList());
    }

    public static List<AlbumResponse> toResponseList(List<AlbumEntity> entities) {
        return entities.stream()
                .map(AlbumMapper::toResponse)
                .collect(Collectors.toList());
    }
}
