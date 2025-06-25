package mx.primis.interaction_service.controllers;

import mx.primis.interaction_service.model.dto.requests.FavoriteAlbumsRequest;
import mx.primis.interaction_service.model.dto.requests.FavoriteMoviesRequest;
import mx.primis.interaction_service.model.dto.responses.*;
import mx.primis.interaction_service.services.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/interaction/favorites")
public class FavoriteController {
    @Autowired private FavoriteService favoriteService;

    //añadir pelicula a favoritos verificando el usuario por medio del jwt
    @PostMapping("/movies")
    public ResponseEntity<FavoriteMoviesResponse> addFavoriteMovie(@RequestHeader("Authorization") String authToken, @RequestBody FavoriteMoviesRequest request) {
        //extraer el token del header
        String token = authToken.startsWith("Bearer ") ? authToken.substring(7) : authToken;
        FavoriteMoviesResponse response = favoriteService.addFavoriteMovie(token, request);
        return ResponseEntity.ok(response);
    }

    //añadir album a favoritos verificando el usuario por medio del jwt
    @PostMapping("/albums")
    public ResponseEntity<FavoriteAlbumsResponse> addFavoriteAlbum(@RequestHeader("Authorization") String authToken, @RequestBody FavoriteAlbumsRequest request) {
        String token = authToken.startsWith("Bearer ") ? authToken.substring(7) : authToken;
        FavoriteAlbumsResponse response = favoriteService.addFavoriteAlbum(token, request);
        return ResponseEntity.ok(response);
    }

    //obtener las peliculas favoritas del usuario por medio del jwt
    @GetMapping("/movies/user/{userId}")
    public ResponseEntity<List<MovieResponse>> getUserFavoriteMovies(@RequestHeader("Authorization") String authToken, @PathVariable Long userId) {
        String token = authToken.startsWith("Bearer ") ? authToken.substring(7) : authToken;
        List<MovieResponse> response = favoriteService.getUserFavoriteMovies(token, userId);
        return ResponseEntity.ok(response);
    }

    //obtener las albumes favoritos del usuario por medio del jwt
    @GetMapping("/albums/user/{userId}")
    public ResponseEntity<List<AlbumResponse>> getUserFavoriteAlbums(@RequestHeader("Authorization") String authToken, @PathVariable Long userId) {
        String token = authToken.startsWith("Bearer ") ? authToken.substring(7) : authToken;
        List<AlbumResponse> response = favoriteService.getUserFavoriteAlbums(token, userId);
        return ResponseEntity.ok(response);
    }

    //eliminar pelicula de favoritos del usuario por medio del jwt
    @DeleteMapping("/movies/delete/{movieId}")
    public ResponseEntity<DeleteResponse> removeFavoriteMovie(@RequestHeader("Authorization") String authToken, @RequestParam Long userId, @PathVariable Long movieId) {
        String token = authToken.startsWith("Bearer ") ? authToken.substring(7) : authToken;
        DeleteResponse response =favoriteService.removeFavoriteMovie(token, userId, movieId);
        HttpStatus status = response.success() ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(response, status);
    }

    //eliminar album de favoritos del usuario por medio del jwt
    @DeleteMapping("/albums/delete/{albumId}")
    public ResponseEntity<DeleteResponse> removeFavoriteAlbum(@RequestHeader("Authorization") String authToken, @RequestParam Long userId, @PathVariable Long albumId) {
        String token = authToken.startsWith("Bearer ") ? authToken.substring(7) : authToken;
        DeleteResponse response = favoriteService.removeFavoriteAlbum(token, userId, albumId);
        HttpStatus status = response.success() ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(response, status);
    }
}
