package takeoff.logistics_service.msa.auth.domain.repository;

import java.util.Optional;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RedisTokenRepositoryImpl implements RedisTokenRepository {

    private final RedisTemplate<String, Object> redisTemplate;
    private static final long REFRESH_TOKEN_EXPIRATION = 7; // 7일

    @Override
    public void saveToken(String userId, String refreshToken) {
        redisTemplate.opsForValue().set("RT:" + userId, refreshToken, REFRESH_TOKEN_EXPIRATION, TimeUnit.DAYS);
    }
    @Override
    public Optional<String> findTokenByUserId(String userId) {
        String refreshToken = (String) redisTemplate.opsForValue().get("RT:" + userId);
        return Optional.ofNullable(refreshToken);
    }
    @Override
    public void deleteToken(String userId) {
        redisTemplate.delete("RT:" + userId);
    }
}
