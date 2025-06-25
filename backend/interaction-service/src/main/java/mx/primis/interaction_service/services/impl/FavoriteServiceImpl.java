package mx.primis.interaction_service.services.impl;

import mx.primis.interaction_service.client.AuthServiceClient;
import mx.primis.interaction_service.client.ContentServiceClient;
import mx.primis.interaction_service.exceptions.ForbiddenException;
import mx.primis.interaction_service.exceptions.UnauthorizedException;
import mx.primis.interaction_service.mappers.FavoriteAlbumMapper;
import mx.primis.interaction_service.mappers.FavoriteMovieMapper;
import mx.primis.interaction_service.model.dto.requests.FavoriteAlbumsRequest;
import mx.primis.interaction_service.model.dto.requests.FavoriteMoviesRequest;
import mx.primis.interaction_service.model.dto.requests.TokenRequest;
import mx.primis.interaction_service.model.dto.responses.*;
import mx.primis.interaction_service.model.entities.FavoriteAlbumsEntity;
import mx.primis.interaction_service.model.entities.FavoriteMoviesEntity;
import mx.primis.interaction_service.repositories.FavoriteAlbumRepository;
import mx.primis.interaction_service.repositories.FavoriteMovieRepository;
import mx.primis.interaction_service.services.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FavoriteServiceImpl implements FavoriteService {
    @Autowired private AuthServiceClient authServiceClient;
    @Autowired private ContentServiceClient contentServiceClient;
    @Autowired private FavoriteMovieRepository favoriteMovieRepository;
    @Autowired private FavoriteAlbumRepository favoriteAlbumRepository;

    @Override
    public FavoriteMoviesResponse addFavoriteMovie(String authToken, FavoriteMoviesRequest request) {
        // Validar token
        TokenValidationResponse validation = authServiceClient.validateToken(new TokenRequest(authToken));

        if (!validation.valid()) {
            throw new UnauthorizedException("Token inválido o expirado");
        }

        // Verificar que el userId del token coincide con la solicitud
        if (!(validation.userId()==request.userId())) {
            throw new ForbiddenException("No tienes permiso para realizar esta acción");
        }

        // Convertir y guardar
        FavoriteMoviesEntity entity = FavoriteMovieMapper.toEntity(request);
        FavoriteMoviesEntity saved = favoriteMovieRepository.save(entity);

        return FavoriteMovieMapper.toResponse(saved);
    }

    @Override
    public FavoriteAlbumsResponse addFavoriteAlbum(String authToken, FavoriteAlbumsRequest request) {
        // Validar token
        TokenValidationResponse validation = authServiceClient.validateToken(new TokenRequest(authToken));

        if (!validation.valid()) {
            throw new UnauthorizedException("Token inválido o expirado");
        }

        // Verificar que el userId del token coincide con la solicitud
        if (!(validation.userId()==request.userId())) {
            throw new ForbiddenException("No tienes permiso para realizar esta acción");
        }

        // Convertir y guardar
        FavoriteAlbumsEntity entity = FavoriteAlbumMapper.toEntity(request);
        FavoriteAlbumsEntity saved = favoriteAlbumRepository.save(entity);

        return FavoriteAlbumMapper.toResponse(saved);
    }

    @Override
    public List<MovieResponse> getUserFavoriteMovies(String authToken, Long userId) {
        TokenValidationResponse validation = authServiceClient.validateToken(new TokenRequest(authToken));
        if (!validation.valid() || !(validation.userId()==userId)) {
            throw new UnauthorizedException("Acceso no autorizado");
        }
        List<Long> movieIds = favoriteMovieRepository.findByUserId(userId)
                .stream()
                .map(FavoriteMoviesEntity::getMovieId)
                .collect(Collectors.toList());

        return contentServiceClient.searchMoviesByIds(movieIds);
    }

    @Override
    public List<AlbumResponse> getUserFavoriteAlbums(String authToken, Long userId) {
        // Validar token
        TokenValidationResponse validation = authServiceClient.validateToken(new TokenRequest(authToken));
        if (!validation.valid() || !(validation.userId()==userId)) {
            throw new UnauthorizedException("Acceso no autorizado");
        }

        // Obtener IDs de favoritos
        List<Long> albumIds = favoriteAlbumRepository.findByUserId(userId)
                .stream()
                .map(FavoriteAlbumsEntity::getAlbumId)
                .collect(Collectors.toList());

        // Obtener detalles del content-service
        return contentServiceClient.searchAlbumsByIds(albumIds);
    }

    @Override
    public DeleteResponse removeFavoriteMovie(String authToken, Long userId, Long movieId) {
        // Validar token
        TokenValidationResponse validation = authServiceClient.validateToken(new TokenRequest(authToken));
        if (!validation.valid() || !(validation.userId()==userId)) {
            throw new UnauthorizedException("Acceso no autorizado");
        }

        // Buscar y eliminar el favorito
        return favoriteMovieRepository.findByUserIdAndMovieId(userId, movieId)
                .map(favoriteMovie -> {
                    favoriteMovieRepository.delete(favoriteMovie);
                    return new DeleteResponse(true, "Película eliminada de favoritos", movieId);
                })
                .orElse(new DeleteResponse(false, "Película no encontrada en favoritos", movieId));
    }

    @Override
    public DeleteResponse removeFavoriteAlbum(String authToken, Long userId, Long albumId) {
        // Validar token
        TokenValidationResponse validation = authServiceClient.validateToken(new TokenRequest(authToken));
        if (!validation.valid() || !(validation.userId()==userId)) {
            throw new UnauthorizedException("Acceso no autorizado");
        }

        return favoriteAlbumRepository.findByUserIdAndAlbumId(userId, albumId)
                .map(favoriteAlbum -> {
                    favoriteAlbumRepository.delete(favoriteAlbum);
                    return new DeleteResponse(true, "Álbum eliminado de favoritos", albumId);
                })
                .orElse(new DeleteResponse(false, "Álbum no encontrado en favoritos", albumId));
    }
}
