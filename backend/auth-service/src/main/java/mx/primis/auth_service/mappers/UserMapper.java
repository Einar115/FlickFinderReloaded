package mx.primis.auth_service.mappers;

import mx.primis.auth_service.model.dto.requests.UserRequest;
import mx.primis.auth_service.model.dto.responses.UserResponse;
import mx.primis.auth_service.model.entities.UserEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserMapper {
    public UserEntity toEntity(UserRequest dto) {
        UserEntity entity = new UserEntity();
        entity.setUsername(dto.username());
        entity.setEmail(dto.email());
        entity.setBirthDay(dto.birthDay());
        return entity;
    }

    public UserResponse toDTO(UserEntity entity) {
        return new UserResponse(
                entity.getId(),
                entity.getUsername(),
                entity.getEmail(),
                entity.getBirthDay(),
                entity.getEnable(),
                entity.getRole() != null ? entity.getRole().getName() : null
        );
    }

    public List<UserResponse> toListDTO(List<UserEntity> users){
        return users.stream()
                .map(this::toDTO)
                .toList();
    }

}
