package mx.primis.auth_service.utils.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import mx.primis.auth_service.model.entities.UserEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtils {
    @Value("${app.jwt.secret}")
    private String jwtSecret;

    @Value("${app.jwt.issuer}")
    private String issuer;

    @Value("${app.jwt.access-token.expiration-ms}")
    private long accessTokenExpirationInMS;

    @Value("${app.jwt.refresh-token.expiration-ms}")
    private long refreshTokenExpirationInMs;

    @PostConstruct
    public void validateSecret() {
        if (jwtSecret == null || jwtSecret.getBytes().length < 32) {
            throw new IllegalStateException("JWT secret must be at least 256 bits (32 characters)");
        }
    }

    public SecretKey getSigningKey(){
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    public String generateToken(UserEntity user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("name", user.getUsername());
        claims.put("role", user.getRole().getName());
        return buildToken(claims, user.getId().toString(), accessTokenExpirationInMS);
    }

    private String buildToken(Map<String, Object> claims, String subject, long expiration) {
        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .issuer(issuer)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigningKey())
                .compact();
    }

    public String generateRefreshToken(UserEntity user) {
        return buildToken(new HashMap<>(), user.getId().toString(), refreshTokenExpirationInMs);
    }

    public Claims extractClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractClaims(token);
        return claimsResolver.apply(claims);
    }

    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (JwtException ex) {
            return false;
        }
    }

    public Date getExpirationDateFromToken(String token) {
        return extractClaims(token).getExpiration();
    }

    public boolean isTokenExpired(String token) {
        if (!validateToken(token)) return true;
        return getExpirationDateFromToken(token).before(new Date());
    }

    public long getRefreshTokenExpirationInMs() {
        return refreshTokenExpirationInMs;
    }
}
