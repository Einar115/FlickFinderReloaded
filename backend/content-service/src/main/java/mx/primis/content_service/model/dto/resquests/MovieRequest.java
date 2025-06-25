package mx.primis.content_service.model.dto.resquests;

import java.util.Date;
import java.util.Set;

public record MovieRequest(
        String title,
        Date releaseDate,
        float voteAverage,
        boolean adult,
        String description,
        String img,
        String homepage,
        Set<Long> genreIds
) {
}