package mx.primis.interaction_service.model.dto.responses;

import java.util.Date;
import java.util.Set;

public record MovieResponse(
        Long id,
        String title,
        Date releaseDate,
        float voteAverage,
        boolean adult,
        String description,
        String img,
        String homepage,
        Set<GenreResponse> genres
) {
}
