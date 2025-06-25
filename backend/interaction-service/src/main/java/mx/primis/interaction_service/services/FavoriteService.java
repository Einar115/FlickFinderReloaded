package mx.primis.interaction_service.services;

import mx.primis.interaction_service.model.dto.requests.FavoriteAlbumsRequest;
import mx.primis.interaction_service.model.dto.requests.FavoriteMoviesRequest;
import mx.primis.interaction_service.model.dto.responses.*;

import java.util.List;

public interface FavoriteService {
    FavoriteMoviesResponse addFavoriteMovie(String authToken, FavoriteMoviesRequest request) ;
    FavoriteAlbumsResponse addFavoriteAlbum(String authToken, FavoriteAlbumsRequest request);
    List<MovieResponse> getUserFavoriteMovies(String authToken, Long userId);
    List<AlbumResponse> getUserFavoriteAlbums(String authToken, Long userId);
    DeleteResponse removeFavoriteMovie(String authToken, Long userId, Long movieId);
    DeleteResponse removeFavoriteAlbum(String authToken, Long userId, Long albumId);
}
