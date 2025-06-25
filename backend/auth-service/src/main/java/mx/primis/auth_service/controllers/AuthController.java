package mx.primis.auth_service.controllers;

import mx.primis.auth_service.model.dto.requests.AuthRequest;
import mx.primis.auth_service.model.dto.requests.TokenRequest;
import mx.primis.auth_service.model.dto.requests.UserRequest;
import mx.primis.auth_service.model.dto.responses.AuthResponse;
import mx.primis.auth_service.model.dto.responses.GeneralResponse;
import mx.primis.auth_service.model.dto.responses.TokenValidationResponse;
import mx.primis.auth_service.services.AuthService;
import mx.primis.auth_service.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired private AuthService authService;
    @Autowired private UserService userService;

    //endpoint para registrarse
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> registerUser(@RequestBody UserRequest userRequest) {
        return ResponseEntity.ok(userService.register(userRequest));
    }

    //endpoint para iniciar sesion
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authRequest) {
        return ResponseEntity.ok(authService.authenticate(authRequest));
    }

    //endpoint para validar access token de jwt
    @PostMapping("/validate")
    public ResponseEntity<TokenValidationResponse> validateToken(@RequestBody TokenRequest request) {
        return ResponseEntity.ok(authService.validate(request));
    }

    //endpoint para actualizar el access token y refresh token
    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refreshToken(@RequestBody TokenRequest refreshToken) {
        return ResponseEntity.ok(authService.refreshToken(refreshToken));
    }

    //endpoint para cerrar sesion
    @PostMapping("/logout")
    public ResponseEntity<GeneralResponse> logout(@RequestBody TokenRequest refreshToken) {
        authService.logout(refreshToken.token());
        return ResponseEntity.ok().build();
    }

}
