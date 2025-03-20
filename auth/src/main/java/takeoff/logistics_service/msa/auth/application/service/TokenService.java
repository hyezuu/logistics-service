package takeoff.logistics_service.msa.auth.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import takeoff.logistics_service.msa.auth.domain.service.RedisTokenService;
import takeoff.logistics_service.msa.auth.infrastructure.security.JwtUtil;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final JwtUtil jwtUtil;
    private final RedisTokenService redisTokenService;

    public String createAccessToken(String userId, String role) {
        return jwtUtil.createAccessToken(userId, role);
    }

    public String createRefreshToken(String userId) {
        return jwtUtil.createRefreshToken(userId);
    }

    public boolean validateToken(String token) {
        return jwtUtil.validateToken(token);
    }

    public String getUserIdFromToken(String token) {
        return jwtUtil.getUserIdFromToken(token);
    }

    public String getUserRoleFromToken(String token) {
        return jwtUtil.getUserRoleFromToken(token);
    }

    public Optional<String> refreshAccessToken(String refreshToken) {
        if (validateToken(refreshToken)) {
            String userId = getUserIdFromToken(refreshToken);
            String role = getUserRoleFromToken(refreshToken);

            Optional<String> storedRefreshToken = redisTokenService.findTokenByUserId(userId);
            if (storedRefreshToken.isPresent() && storedRefreshToken.get().equals(refreshToken)) {
                return Optional.of(createAccessToken(userId, role));
            }
        }
        return Optional.empty();
    }
}
