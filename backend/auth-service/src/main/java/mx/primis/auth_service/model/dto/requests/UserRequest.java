package mx.primis.auth_service.model.dto.requests;

import java.time.LocalDate;

public record UserRequest(
        String username,
        String email,
        String password,
        LocalDate birthDay
) {
}
