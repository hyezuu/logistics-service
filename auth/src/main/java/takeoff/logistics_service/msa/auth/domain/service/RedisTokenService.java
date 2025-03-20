package takeoff.logistics_service.msa.auth.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import takeoff.logistics_service.msa.auth.domain.repository.RedisTokenRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RedisTokenService {

    private final RedisTokenRepository redisTokenRepository;

    @Transactional
    public void saveOrUpdateToken(String userId, String refreshToken) {
        Optional<String> existingToken = redisTokenRepository.findTokenByUserId(userId);

        if (existingToken.isPresent()) {
            redisTokenRepository.saveToken(userId, refreshToken);
        } else {
            redisTokenRepository.saveToken(userId, refreshToken);
        }
    }
    @Transactional(readOnly = true)
    public Optional<String> findTokenByUserId(String userId) {
        return redisTokenRepository.findTokenByUserId(userId);
    }
    @Transactional
    public void deleteToken(String userId) {
        redisTokenRepository.deleteToken(userId);
    }
}
