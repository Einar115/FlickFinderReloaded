package mx.primis.content_service.Services;

import mx.primis.content_service.model.dto.responses.MovieResponse;

import java.util.List;

public interface MovieService {
    List<MovieResponse> getRecentMovies(int count);
    MovieResponse getMovieDetails(Long id);
    List<MovieResponse> getMoviesByIds(List<Long> ids);
}
