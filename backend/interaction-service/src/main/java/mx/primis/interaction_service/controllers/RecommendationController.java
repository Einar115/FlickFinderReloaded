package mx.primis.interaction_service.controllers;

import mx.primis.interaction_service.client.AuthServiceClient;
import mx.primis.interaction_service.client.ContentServiceClient;
import mx.primis.interaction_service.exceptions.UnauthorizedException;
import mx.primis.interaction_service.model.dto.requests.TokenRequest;
import mx.primis.interaction_service.model.dto.responses.AlbumResponse;
import mx.primis.interaction_service.model.dto.responses.MovieResponse;
import mx.primis.interaction_service.model.dto.responses.TokenValidationResponse;
import mx.primis.interaction_service.model.entities.RecommendationAlbumsEntity;
import mx.primis.interaction_service.model.entities.RecommendationMoviesEntity;
import mx.primis.interaction_service.repositories.RecommendationAlbumRepository;
import mx.primis.interaction_service.repositories.RecommendationMovieRepository;
import mx.primis.interaction_service.services.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/interaction/recommendation")
public class RecommendationController {
    @Autowired private AuthServiceClient authServiceClient;
    @Autowired private ContentServiceClient contentServiceClient;
    @Autowired private RecommendationService recommendationService;
    @Autowired private RecommendationAlbumRepository recommendationAlbumRepository;
    @Autowired private RecommendationMovieRepository recommendationMovieRepository;

    //generar recomendaciones dependiendo del contenido en favoritos en
    @PostMapping("/generate/{userId}")
    public ResponseEntity<Void> generateRecommendations(@RequestHeader("Authorization") String authToken, @PathVariable Long userId) {
        // Validar token
        String token = authToken.startsWith("Bearer ") ? authToken.substring(7) : authToken;
        TokenValidationResponse validation = authServiceClient.validateToken(new TokenRequest(token));

        if (!validation.valid() || !(validation.userId()==userId)) {
            throw new UnauthorizedException("Acceso no autorizado");
        }

        recommendationService.generateRecommendations(userId);
        return ResponseEntity.accepted().build();
    }

    //obtener recomendaciones de peliculas verificando al usuario por jwt
    @GetMapping("/movies/{userId}")
    public ResponseEntity<List<MovieResponse>> getMovieRecommendations(@RequestHeader("Authorization") String authToken, @PathVariable Long userId) {
        String token = authToken.startsWith("Bearer ") ? authToken.substring(7) : authToken;
        TokenValidationResponse validation = authServiceClient.validateToken(new TokenRequest(token));

        if (!validation.valid() || !(validation.userId()==userId)) {
            throw new UnauthorizedException("Acceso no autorizado");
        }

        List<Long> movieIds = recommendationMovieRepository.findByUserIdOrderByRelevanceScoreDesc(userId)
                .stream()
                .map(RecommendationMoviesEntity::getMovieId)
                .collect(Collectors.toList());

        List<MovieResponse> recommendations = contentServiceClient.searchMoviesByIds(movieIds);
        return ResponseEntity.ok(recommendations);
    }
    //obtener recomendaciones de albumes verificando al usuario por jwt
    @GetMapping("/albums/{userId}")
    public ResponseEntity<List<AlbumResponse>> getAlbumRecommendations(@RequestHeader("Authorization") String authToken, @PathVariable Long userId) {
        String token = authToken.startsWith("Bearer ") ? authToken.substring(7) : authToken;
        TokenValidationResponse validation = authServiceClient.validateToken(new TokenRequest(token));

        if (!validation.valid() || !(validation.userId()==userId)) {
            throw new UnauthorizedException("Acceso no autorizado");
        }

        List<Long> albumIds = recommendationAlbumRepository.findByUserIdOrderByRelevanceScoreDesc(userId)
                .stream()
                .map(RecommendationAlbumsEntity::getAlbumId)
                .collect(Collectors.toList());

        List<AlbumResponse> recommendations = contentServiceClient.searchAlbumsByIds(albumIds);
        return ResponseEntity.ok(recommendations);
    }
}
