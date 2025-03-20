package takeoff.logistics_service.msa.auth.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import takeoff.logistics_service.msa.auth.domain.service.RedisTokenService;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final RedisTokenService redisTokenService;

    @Transactional
    public void logout(String userId) {
        redisTokenService.deleteToken(userId);
        log.info("사용자 {}의 RefreshToken 삭제 완료 (로그아웃)", userId);
    }
}
