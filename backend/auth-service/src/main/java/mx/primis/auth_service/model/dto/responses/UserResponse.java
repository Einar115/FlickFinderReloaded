package mx.primis.auth_service.model.dto.responses;

import java.time.LocalDate;

public record UserResponse(
        Long id,
        String username,
        String email,
        LocalDate birthDay,
        Boolean isEnable,
        String role
) {

}
