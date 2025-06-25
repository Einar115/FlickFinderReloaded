package mx.primis.interaction_service.client;

import mx.primis.interaction_service.model.dto.responses.AlbumResponse;
import mx.primis.interaction_service.model.dto.responses.MovieResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "content-service", url = "${content.service.url}")
public interface ContentServiceClient {
    @GetMapping("/content/movies/recent")
    List<MovieResponse> getRecentMovies(@RequestParam int count);

    @GetMapping("/content/albums/recent")
    List<AlbumResponse> getRecentAlbums(@RequestParam int count);

    @GetMapping("/content/movies/details/{id}")
    MovieResponse getMovieDetails(@PathVariable Long id);

    @GetMapping("/content/albums/details/{id}")
    AlbumResponse getAlbumDetails(@PathVariable Long id);

    @GetMapping("/content/movies/getById")
    List<MovieResponse> searchMoviesByIds(@RequestParam List<Long> ids);

    @GetMapping("/content/albums/getById")
    List<AlbumResponse> searchAlbumsByIds(@RequestParam List<Long> ids);
}
