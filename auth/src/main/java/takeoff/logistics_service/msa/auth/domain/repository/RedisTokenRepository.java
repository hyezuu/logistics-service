package takeoff.logistics_service.msa.auth.domain.repository;

import java.util.Optional;

public interface RedisTokenRepository {
    void saveToken(String userId, String refreshToken);
    Optional<String> findTokenByUserId(String userId);
    void deleteToken(String userId);
}
