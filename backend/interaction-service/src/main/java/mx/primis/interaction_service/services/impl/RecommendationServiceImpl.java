package mx.primis.interaction_service.services.impl;

import jakarta.transaction.Transactional;
import mx.primis.interaction_service.client.ContentServiceClient;
import mx.primis.interaction_service.model.dto.responses.AlbumResponse;
import mx.primis.interaction_service.model.dto.responses.MovieResponse;
import mx.primis.interaction_service.model.entities.FavoriteAlbumsEntity;
import mx.primis.interaction_service.model.entities.FavoriteMoviesEntity;
import mx.primis.interaction_service.model.entities.RecommendationAlbumsEntity;
import mx.primis.interaction_service.model.entities.RecommendationMoviesEntity;
import mx.primis.interaction_service.repositories.FavoriteAlbumRepository;
import mx.primis.interaction_service.repositories.FavoriteMovieRepository;
import mx.primis.interaction_service.repositories.RecommendationAlbumRepository;
import mx.primis.interaction_service.repositories.RecommendationMovieRepository;
import mx.primis.interaction_service.services.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecommendationServiceImpl implements RecommendationService {
    @Autowired private FavoriteMovieRepository favoriteMovieRepository;
    @Autowired private FavoriteAlbumRepository favoriteAlbumRepository;
    @Autowired private RecommendationMovieRepository recommendationMoviesRepository;
    @Autowired private RecommendationAlbumRepository recommendationAlbumsRepository;
    @Autowired private ContentServiceClient contentServiceClient;

    private static final int MAX_RECOMMENDATIONS = 10;
    private static final float BASE_RELEVANCE = 0.7f;


    @Override
    @Transactional
    public void generateRecommendations(Long userId) {
        // Limpiar recomendaciones previas
        recommendationMoviesRepository.deleteByUserId(userId);
        recommendationAlbumsRepository.deleteByUserId(userId);

        // Generar recomendaciones de películas
        generateMovieRecommendations(userId);

        // Generar recomendaciones de álbumes
        generateAlbumRecommendations(userId);
    }

    @Override
    @Transactional
    public void generateMovieRecommendations(Long userId) {
        // Obtener películas favoritas del usuario
        List<Long> favoriteMovieIds = favoriteMovieRepository.findByUserId(userId)
                .stream()
                .map(FavoriteMoviesEntity::getMovieId)
                .collect(Collectors.toList());

        if (favoriteMovieIds.isEmpty()) {
            return;
        }

        // Obtener detalles de las películas favoritas
        List<MovieResponse> favoriteMovies = contentServiceClient.searchMoviesByIds(favoriteMovieIds);

        // Simular lógica de recomendación (en un sistema real usarías un algoritmo más sofisticado)
        List<MovieResponse> recommendedMovies = contentServiceClient.getRecentMovies(MAX_RECOMMENDATIONS * 2)
                .stream()
                .filter(movie -> !favoriteMovieIds.contains(movie.id())) // Excluir favoritos
                .sorted(Comparator.comparingDouble(movie ->
                        calculateMovieRelevanceScore(movie, favoriteMovies)))
                .limit(MAX_RECOMMENDATIONS)
                .toList();

        // Guardar recomendaciones
        recommendedMovies.forEach(movie -> {
            RecommendationMoviesEntity recommendation = new RecommendationMoviesEntity();
            recommendation.setUserId(userId);
            recommendation.setMovieId(movie.id());
            recommendation.setRelevanceScore(BASE_RELEVANCE + (float) Math.random() * 0.3f); // Score aleatorio entre 0.7-1.0
            recommendationMoviesRepository.save(recommendation);
        });
    }

    @Override
    @Transactional
    public void generateAlbumRecommendations(Long userId) {
        List<Long> favoriteAlbumIds = favoriteAlbumRepository.findByUserId(userId)
                .stream()
                .map(FavoriteAlbumsEntity::getAlbumId)
                .collect(Collectors.toList());

        if (favoriteAlbumIds.isEmpty()) {
            return;
        }

        // Obtener detalles de los álbumes favoritos
        List<AlbumResponse> favoriteAlbums = contentServiceClient.searchAlbumsByIds(favoriteAlbumIds);

        // Simular lógica de recomendación
        List<AlbumResponse> recommendedAlbums = contentServiceClient.getRecentAlbums(MAX_RECOMMENDATIONS * 2)
                .stream()
                .filter(album -> !favoriteAlbumIds.contains(album.id())) // Excluir favoritos
                .sorted(Comparator.comparingDouble(album ->
                        calculateAlbumRelevanceScore(album, favoriteAlbums)))
                .limit(MAX_RECOMMENDATIONS)
                .toList();

        // Guardar recomendaciones
        recommendedAlbums.forEach(album -> {
            RecommendationAlbumsEntity recommendation = new RecommendationAlbumsEntity();
            recommendation.setUserId(userId);
            recommendation.setAlbumId(album.id());
            recommendation.setRelevanceScore(BASE_RELEVANCE + (float) Math.random() * 0.3f); // Score aleatorio entre 0.7-1.0
            recommendationAlbumsRepository.save(recommendation);
        });
    }

    @Override
    @Transactional
    public double calculateMovieRelevanceScore(MovieResponse movie, List<MovieResponse> favorites) {
        // En un sistema real, esto podría considerar géneros, año, rating, etc.
        return favorites.stream()
                .mapToDouble(fav -> {
                    double score = 0;
                    // Mismo género aumenta relevancia
                    if (movie.genres().stream().anyMatch(g -> fav.genres().contains(g))) {
                        score += 0.5;
                    }
                    // Año cercano aumenta relevancia
                    score += 1.0 / (1.0 + Math.abs(movie.releaseDate().getYear() - fav.releaseDate().getYear()));
                    return score;
                })
                .average()
                .orElse(0);
    }

    @Override
    @Transactional
    public double calculateAlbumRelevanceScore(AlbumResponse album, List<AlbumResponse> favorites) {
        // Lógica similar para álbumes
        return favorites.stream()
                .mapToDouble(fav -> {
                    double score = 0;
                    // Mismo artista aumenta relevancia
                    if (album.artistId().equals(fav.artistId())) {
                        score += 0.7;
                    }
                    // Año cercano aumenta relevancia
                    score += 1.0 / (1.0 + Math.abs(album.year() - fav.year()));
                    return score;
                })
                .average()
                .orElse(0);
    }
}