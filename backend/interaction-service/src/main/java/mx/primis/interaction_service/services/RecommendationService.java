package mx.primis.interaction_service.services;

import mx.primis.interaction_service.model.dto.responses.AlbumResponse;
import mx.primis.interaction_service.model.dto.responses.MovieResponse;

import java.util.List;

public interface RecommendationService {
    void generateRecommendations(Long userId);
    void generateMovieRecommendations(Long userId);
    void generateAlbumRecommendations(Long userId);
    double calculateMovieRelevanceScore(MovieResponse movie, List<MovieResponse> favorites);
    double calculateAlbumRelevanceScore(AlbumResponse album, List<AlbumResponse> favorites);
}
