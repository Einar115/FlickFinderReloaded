package mx.primis.content_service.mappers;

import mx.primis.content_service.model.dto.responses.GenreResponse;
import mx.primis.content_service.model.dto.resquests.GenreRequest;
import mx.primis.content_service.model.entities.GenreEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GenreMapper {
    public static GenreEntity toEntity(GenreRequest dto) {
        GenreEntity entity = new GenreEntity();
        entity.setName(dto.name());
        return entity;
    }

    public static GenreResponse toResponse(GenreEntity entity) {
        return new GenreResponse(
                entity.getId(),
                entity.getName()
        );
    }

    public static List<GenreEntity> toEntityList(List<GenreRequest> dtos) {
        return dtos.stream()
                .map(GenreMapper::toEntity)
                .collect(Collectors.toList());
    }

    public static List<GenreResponse> toResponseList(List<GenreEntity> entities) {
        return entities.stream()
                .map(GenreMapper::toResponse)
                .collect(Collectors.toList());
    }
}
