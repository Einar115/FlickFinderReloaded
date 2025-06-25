package mx.primis.content_service.Services.Impl;

import mx.primis.content_service.Services.MovieService;
import mx.primis.content_service.mappers.MovieMapper;
import mx.primis.content_service.model.dto.responses.MovieResponse;
import mx.primis.content_service.model.entities.MovieEntity;
import mx.primis.content_service.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {

    @Autowired private MovieRepository movieRepository;

    @Override
    public List<MovieResponse> getRecentMovies(int count) {
        List<MovieEntity> entities = movieRepository.findAll(
                PageRequest.of(0, count, Sort.by(Sort.Direction.DESC, "releaseDate"))
        ).getContent();

        return MovieMapper.toResponseList(entities);
    }

    @Override
    public MovieResponse getMovieDetails(Long id) {
        MovieEntity movie = movieRepository.findByIdWithGenres(id)
                .orElseThrow(() -> new RuntimeException("Movie not found with id: " + id));

        return MovieMapper.toResponse(movie);
    }

    @Override
    public List<MovieResponse> getMoviesByIds(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return List.of();
        }
        List<MovieEntity> entities = movieRepository.findAllByIdWithGenres(ids);
        if (entities.size() != ids.size()) {
            // Opcional: Loggear IDs no encontrados
            List<Long> foundIds = entities.stream()
                    .map(MovieEntity::getId)
                    .toList();
            List<Long> missingIds = ids.stream()
                    .filter(id -> !foundIds.contains(id))
                    .toList();
        }

        return MovieMapper.toResponseList(entities);
    }
}
