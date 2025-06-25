package mx.primis.content_service.mappers;

import mx.primis.content_service.model.dto.responses.MovieResponse;
import mx.primis.content_service.model.dto.resquests.MovieRequest;
import mx.primis.content_service.model.entities.MovieEntity;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MovieMapper {

    public static MovieEntity toEntity(MovieRequest dto) {
        MovieEntity entity = new MovieEntity();
        entity.setTitle(dto.title());
        entity.setReleaseDate(dto.releaseDate() != null ? new Date(dto.releaseDate().getTime()) : null);
        entity.setVoteAverage(dto.voteAverage());
        entity.setAdult(dto.adult());
        entity.setDescription(dto.description());
        entity.setImg(dto.img());
        entity.setHomepage(dto.homepage());
        return entity;
    }

    public static MovieResponse toResponse(MovieEntity entity) {
        return new MovieResponse(
                entity.getId(),
                entity.getTitle(),
                entity.getReleaseDate() != null ? new Date(entity.getReleaseDate().getTime()) : null,
                entity.getVoteAverage(),
                entity.isAdult(),
                entity.getDescription(),
                entity.getImg(),
                entity.getHomepage(),
                entity.getGenres().stream()
                        .map(GenreMapper::toResponse)
                        .collect(Collectors.toSet())

        );
    }
    public static List<MovieEntity> toEntityList(List<MovieRequest> dtos) {
        return dtos.stream()
                .map(MovieMapper::toEntity)
                .collect(Collectors.toList());
    }

    public static List<MovieResponse> toResponseList(List<MovieEntity> entities) {
        return entities.stream()
                .map(MovieMapper::toResponse)
                .collect(Collectors.toList());
    }
}
