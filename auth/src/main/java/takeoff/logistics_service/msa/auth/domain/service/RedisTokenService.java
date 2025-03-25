package takeoff.logistics_service.msa.auth.domain.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import takeoff.logistics_service.msa.auth.domain.repository.RedisTokenRepository;

@Service
@RequiredArgsConstructor
public class RedisTokenService {

    private final RedisTokenRepository redisTokenRepository;
    @Transactional
    public void saveOrUpdateToken(String userId, String refreshToken) {
        redisTokenRepository.saveToken(userId, refreshToken);
    }
    public Optional<String> findTokenByUserId(String userId) {
        return redisTokenRepository.findTokenByUserId(userId);
    }
    public void deleteToken(String userId) {
        redisTokenRepository.deleteToken(userId);
    }
}
