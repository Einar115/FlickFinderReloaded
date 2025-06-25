package mx.primis.auth_service.utils.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mx.primis.auth_service.model.entities.UserEntity;
import mx.primis.auth_service.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private static final String BEARER_PREFIX = "Bearer ";
    private static final String AUTH_HEADER = "Authorization";
    private static final String ROLE_PREFIX = "role_";

    @Autowired private JwtUtils jwtUtil;
    @Autowired private UserRepository userRepository;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        try {
            final String authHeader = request.getHeader(AUTH_HEADER);
            if (authHeader != null && authHeader.startsWith(BEARER_PREFIX)) {
                final String jwt = authHeader.substring(BEARER_PREFIX.length());
                final String email = jwtUtil.extractUsername(jwt);
                if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    userRepository.findByEmail(email)
                            .ifPresent(user -> authenticateUser(request, user));
                }
            }
        } catch (Exception e) {
            logger.error("Error en el procesamiento del JWT", e);
        }
        filterChain.doFilter(request, response);
    }

    private void authenticateUser(HttpServletRequest request, UserEntity user) {
        GrantedAuthority authority = new SimpleGrantedAuthority(ROLE_PREFIX + user.getRole().getName());
        var authToken = new UsernamePasswordAuthenticationToken(
                user,
                null,
                List.of(authority)
        );
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);
    }
}
