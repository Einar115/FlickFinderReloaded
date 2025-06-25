package mx.primis.interaction_service.mappers;

import mx.primis.interaction_service.model.dto.requests.FavoriteAlbumsRequest;
import mx.primis.interaction_service.model.dto.responses.FavoriteAlbumsResponse;
import mx.primis.interaction_service.model.entities.FavoriteAlbumsEntity;

import java.util.List;
import java.util.stream.Collectors;

public class FavoriteAlbumMapper {
    public static FavoriteAlbumsEntity toEntity(FavoriteAlbumsRequest request) {
        FavoriteAlbumsEntity entity = new FavoriteAlbumsEntity();
        entity.setUserId(request.userId());
        entity.setAlbumId(request.albumId());
        return entity;
    }

    public static FavoriteAlbumsResponse toResponse(FavoriteAlbumsEntity entity) {
        return new FavoriteAlbumsResponse(
                entity.getId(),
                entity.getUserId(),
                entity.getAlbumId(),
                entity.getAddedAt()
        );
    }

    public static List<FavoriteAlbumsResponse> toResponseList(List<FavoriteAlbumsEntity> entities) {
        return entities.stream()
                .map(FavoriteAlbumMapper::toResponse)
                .collect(Collectors.toList());
    }
}
