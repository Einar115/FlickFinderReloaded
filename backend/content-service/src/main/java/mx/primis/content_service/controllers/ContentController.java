package mx.primis.content_service.controllers;

import mx.primis.content_service.Services.MovieService;
import mx.primis.content_service.Services.MusicService;
import mx.primis.content_service.Services.SearchService;
import mx.primis.content_service.model.dto.responses.AlbumResponse;
import mx.primis.content_service.model.dto.responses.MovieResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/content")
public class ContentController {
    @Autowired private MusicService musicService;
    @Autowired private MovieService movieService;
    @Autowired private SearchService searchService;

    //realizar busquedas de peliculas por id
    @GetMapping("/movies/getById")
    public ResponseEntity<List<MovieResponse>> searchMoviesByIds(@RequestParam List<Long> ids) {
        List<MovieResponse> response = movieService.getMoviesByIds(ids);
        return ResponseEntity.ok(response);
    }

    //realizar busquedas de peliculas por nombre
    @GetMapping("/movies/search")
    public ResponseEntity<List<MovieResponse>> searchMovies(@RequestParam String query, @RequestParam(defaultValue = "6") int limit) {
        List<MovieResponse> response = searchService.searchMovies(query, limit);
        return ResponseEntity.ok(response);
    }

    //obtener peliculas estrenadas mas recientes
    @GetMapping("/movies/recent")
    public ResponseEntity<List<MovieResponse>> getRecentMovies(@RequestParam(defaultValue = "6") int count){
        List<MovieResponse> response = movieService.getRecentMovies(count);
        return ResponseEntity.ok(response);
    }

    //obtener detalles de peliculas
    @GetMapping("/movies/details/{id}")
    public ResponseEntity<MovieResponse> getMovieDetails(@PathVariable Long id) {
        MovieResponse response = movieService.getMovieDetails(id);
        return ResponseEntity.ok(response);
    }

    //realizar busquedas de albumes por id
    @GetMapping("/albums/getById")
    public ResponseEntity<List<AlbumResponse>> searchAlbumsByIds(@RequestParam List<Long> ids) {
        List<AlbumResponse> response = musicService.getAlbumsByIds(ids);
        return ResponseEntity.ok(response);
    }

    //realizar busquedas de albumas por nombre
    @GetMapping("/albums/search")
    public ResponseEntity<List<AlbumResponse>> searchAlbums(@RequestParam String query, @RequestParam(defaultValue = "6") int limit) {
        List<AlbumResponse> response = searchService.searchAlbums(query, limit);
        return ResponseEntity.ok(response);
    }

    //obtener albumes lanzados recientemente
    @GetMapping("/albums/recent")
    public ResponseEntity<List<AlbumResponse>> getRecentAlbums(@RequestParam(defaultValue = "6") int count){
        List<AlbumResponse> response = musicService.getRecentAlbums(count);
        return ResponseEntity.ok(response);
    }

    //obtener detalles de albumes
    @GetMapping("/albums/details/{id}")
    public ResponseEntity<AlbumResponse> getAlbumDetails(@PathVariable Long id) {
        AlbumResponse response = musicService.getAlbumDetails(id);
        return ResponseEntity.ok(response);
    }


}
