package mx.primis.auth_service.services.impl;

import io.jsonwebtoken.Claims;
import jakarta.transaction.Transactional;
import mx.primis.auth_service.mappers.AuthMapper;
import mx.primis.auth_service.mappers.RefreshTokenMapper;
import mx.primis.auth_service.mappers.UserMapper;
import mx.primis.auth_service.model.dto.requests.AuthRequest;
import mx.primis.auth_service.model.dto.requests.TokenRequest;
import mx.primis.auth_service.model.dto.responses.AuthResponse;
import mx.primis.auth_service.model.dto.responses.TokenValidationResponse;
import mx.primis.auth_service.model.entities.RefreshTokenEntity;
import mx.primis.auth_service.model.entities.UserEntity;
import mx.primis.auth_service.repositories.RefreshTokenRepository;
import mx.primis.auth_service.repositories.UserRepository;
import mx.primis.auth_service.services.AuthService;
import mx.primis.auth_service.utils.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired private AuthenticationManager authenticationManager;
    @Autowired private JwtUtils jwtUtils;
    @Autowired private RefreshTokenRepository refreshTokenRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private UserMapper userMapper;
    @Autowired private RefreshTokenMapper refreshTokenMapper;
    @Autowired private AuthMapper authMapper;

    @Override
    @Transactional
    public AuthResponse authenticate(AuthRequest authRequest) throws UsernameNotFoundException{
        // 1. Autenticar credenciales
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.email(),
                        authRequest.password()
                )
        );

        // 2. Obtener usuario
        UserEntity user = userRepository.findByEmail(authRequest.email())
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        // 3. Generar tokens
        String jwtToken = jwtUtils.generateToken(user);
        String refreshToken = jwtUtils.generateRefreshToken(user);

        // 4. Buscar y actualizar token existente o crear uno nuevo
        RefreshTokenEntity refreshTokenEntity = refreshTokenRepository.findByUser(user)
                .orElseGet(RefreshTokenEntity::new); // Si no existe, crea uno nuevo

        // Actualizar campos del token
        refreshTokenEntity.setToken(refreshToken);
        refreshTokenEntity.setUser(user);
        refreshTokenEntity.setExpiryDate(Instant.now().plusMillis(jwtUtils.getRefreshTokenExpirationInMs()));

        // 5. Guardar (actualiza si ya existe, inserta si es nuevo)
        refreshTokenRepository.save(refreshTokenEntity);

        // 6. Retornar respuesta
        return authMapper.toDTO(user, jwtToken, refreshToken);
    }


    @Override
    public TokenValidationResponse validate(TokenRequest request) {
        String token = request.token();
        try {
            if (!jwtUtils.validateToken(token) || jwtUtils.isTokenExpired(token)) {
                return TokenValidationResponse.invalid();
            }
            Claims claims = jwtUtils.extractClaims(token);
            return userRepository.findByUsername(claims.get("name", String.class))
                    .map(user -> new TokenValidationResponse(true, Integer.parseInt(claims.getSubject()), user.getUsername()))
                    .orElse(TokenValidationResponse.invalid());
        } catch (Exception e) {
            return TokenValidationResponse.invalid();
        }
    }

    @Override
    public AuthResponse refreshToken(TokenRequest tokenRequest) throws RuntimeException {
        String refreshToken = tokenRequest.token();
        // Validar el refresh token
        if (!jwtUtils.validateToken(refreshToken)) {
            throw new RuntimeException("Refresh token inválido");
        }

        // Buscar el refresh token en la base de datos
        RefreshTokenEntity refreshTokenEntity = refreshTokenRepository.findByToken(refreshToken)
                .orElseThrow(() -> new RuntimeException("Refresh token no encontrado"));

        // Verificar si el refresh token ha expirado
        if (refreshTokenEntity.getExpiryDate().isBefore(Instant.now())) {
            refreshTokenRepository.delete(refreshTokenEntity);
            throw new RuntimeException("Refresh token ha expirado");
        }

        // Obtener el usuario asociado
        UserEntity user = refreshTokenEntity.getUser();

        // Generar nuevo JWT
        String newJwtToken = jwtUtils.generateToken(user);

        // Generar un nuevo refresh token y eliminar el antiguo
        String newRefreshToken = jwtUtils.generateRefreshToken(user);
        refreshTokenRepository.delete(refreshTokenEntity);

        // Crear y guardar el nuevo refresh token usando el mapper
        RefreshTokenEntity newRefreshTokenEntity = refreshTokenMapper.toEntity(
                new TokenRequest(newRefreshToken),
                user,
                Instant.now().plusMillis(jwtUtils.getRefreshTokenExpirationInMs())
        );
        refreshTokenRepository.save(newRefreshTokenEntity);

        // Usar AuthMapper para crear la respuesta
        return authMapper.toDTO(user, newJwtToken, newRefreshToken);
    }

    @Override
    public RefreshTokenEntity verifyExpiration(RefreshTokenEntity token) throws RuntimeException{
        if (token.getExpiryDate().compareTo(Instant.now())<0){
            refreshTokenRepository.deleteByToken(token.getToken());
            throw new RuntimeException("Refresh token was expired. Please make a new signin request");
        }
        return token;
    }

    @Override
    public void logout(String refreshToken) {
        refreshTokenRepository.findByToken(refreshToken)
                .ifPresent(refreshTokenRepository::delete);
    }
}
