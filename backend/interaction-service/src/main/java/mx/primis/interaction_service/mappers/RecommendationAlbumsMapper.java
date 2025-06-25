package mx.primis.interaction_service.mappers;

import mx.primis.interaction_service.model.dto.requests.RecommendationAlbumsRequest;
import mx.primis.interaction_service.model.dto.responses.RecommendationAlbumsResponse;
import mx.primis.interaction_service.model.entities.RecommendationAlbumsEntity;

import java.util.List;
import java.util.stream.Collectors;

public class RecommendationAlbumsMapper {
    public static RecommendationAlbumsEntity toEntity(RecommendationAlbumsRequest request) {
        RecommendationAlbumsEntity entity = new RecommendationAlbumsEntity();
        entity.setUserId(request.userId());
        entity.setAlbumId(request.albumId());
        entity.setRelevanceScore(request.relevanceScore());
        return entity;
    }

    public static RecommendationAlbumsResponse toResponse(RecommendationAlbumsEntity entity) {
        return new RecommendationAlbumsResponse(
                entity.getId(),
                entity.getUserId(),
                entity.getAlbumId(),
                entity.getRelevanceScore()
        );
    }

    public static List<RecommendationAlbumsResponse> toResponseList(List<RecommendationAlbumsEntity> entities) {
        return entities.stream()
                .map(RecommendationAlbumsMapper::toResponse)
                .collect(Collectors.toList());
    }
}
