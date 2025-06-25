package mx.primis.auth_service.services;

import mx.primis.auth_service.model.dto.requests.UserRequest;
import mx.primis.auth_service.model.dto.responses.AuthResponse;
import mx.primis.auth_service.model.dto.responses.UserResponse;

public interface UserService {
    AuthResponse register(UserRequest userRequest);
    UserResponse getUserById(Long userId);
}