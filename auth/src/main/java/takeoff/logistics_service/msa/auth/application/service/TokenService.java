package takeoff.logistics_service.msa.auth.application.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import takeoff.logistics_service.msa.auth.domain.service.RedisTokenService;
import takeoff.logistics_service.msa.auth.infrastructure.security.JwtUtil;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final JwtUtil jwtUtil;
    private final RedisTokenService redisTokenService;

    public Optional<String> refreshAccessToken(String refreshToken) {
        if (!jwtUtil.validateToken(refreshToken)) {
            return Optional.empty();
        }
        String userId = jwtUtil.getUserIdFromToken(refreshToken);
        String role = jwtUtil.getUserRoleFromToken(refreshToken);
        Optional<String> storedRefreshToken = redisTokenService.findTokenByUserId(userId);
        if (storedRefreshToken.isPresent() && storedRefreshToken.get().equals(refreshToken)) {
            return Optional.of(jwtUtil.createAccessToken(userId, role));
        }
        return Optional.empty();
    }
}
