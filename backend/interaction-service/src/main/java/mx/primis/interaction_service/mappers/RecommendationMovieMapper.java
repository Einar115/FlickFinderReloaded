package mx.primis.interaction_service.mappers;

import mx.primis.interaction_service.model.dto.requests.RecommendationMoviesRequest;
import mx.primis.interaction_service.model.dto.responses.RecommendationMoviesResponse;
import mx.primis.interaction_service.model.entities.RecommendationMoviesEntity;

import java.util.List;
import java.util.stream.Collectors;

public class RecommendationMovieMapper {
    public static RecommendationMoviesEntity toEntity(RecommendationMoviesRequest request) {
        RecommendationMoviesEntity entity = new RecommendationMoviesEntity();
        entity.setUserId(request.userId());
        entity.setMovieId(request.movieId());
        entity.setRelevanceScore(request.relevanceScore());
        return entity;
    }

    public static RecommendationMoviesResponse toResponse(RecommendationMoviesEntity entity) {
        return new RecommendationMoviesResponse(
                entity.getId(),
                entity.getUserId(),
                entity.getMovieId(),
                entity.getRelevanceScore()
        );
    }

    public static List<RecommendationMoviesResponse> toResponseList(List<RecommendationMoviesEntity> entities) {
        return entities.stream()
                .map(RecommendationMovieMapper::toResponse)
                .collect(Collectors.toList());
    }
}
