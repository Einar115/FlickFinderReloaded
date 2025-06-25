package mx.primis.interaction_service.mappers;

import mx.primis.interaction_service.model.dto.requests.FavoriteMoviesRequest;
import mx.primis.interaction_service.model.dto.responses.FavoriteMoviesResponse;
import mx.primis.interaction_service.model.entities.FavoriteMoviesEntity;

import java.util.List;
import java.util.stream.Collectors;

public class FavoriteMovieMapper {
    public static FavoriteMoviesEntity toEntity(FavoriteMoviesRequest request) {
        FavoriteMoviesEntity  entity = new FavoriteMoviesEntity ();
        entity.setUserId(request.userId());
        entity.setMovieId(request.movieId());
        return entity;
    }

    public static FavoriteMoviesResponse toResponse(FavoriteMoviesEntity  entity) {
        return new FavoriteMoviesResponse(
                entity.getId(),
                entity.getUserId(),
                entity.getMovieId(),
                entity.getAddedAt()
        );
    }

    // Convertir Lista de Entidades a Lista de Response DTOs
    public static List<FavoriteMoviesResponse> toResponseList(List<FavoriteMoviesEntity> entities) {
        return entities.stream()
                .map(FavoriteMovieMapper::toResponse)
                .collect(Collectors.toList());
    }
}
