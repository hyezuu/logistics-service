package takeoff.logistics_service.msa.auth.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import takeoff.logistics_service.msa.auth.application.client.UserServiceFeignClient;
import takeoff.logistics_service.msa.auth.application.dto.request.UserValidationRequestDto;
import takeoff.logistics_service.msa.auth.application.dto.response.UserValidationResponseDto;
import takeoff.logistics_service.msa.auth.domain.service.RedisTokenService;
import takeoff.logistics_service.msa.auth.presentation.dto.request.LoginRequestDto;
import takeoff.logistics_service.msa.auth.presentation.dto.response.LoginResponseDto;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final TokenService tokenService;
    private final RedisTokenService redisTokenService;
    private final UserServiceFeignClient userServiceFeignClient;

    @Transactional
    public LoginResponseDto login(LoginRequestDto requestDto) {
        log.info("로그인 요청: username={}", requestDto.username());

        UserValidationResponseDto userValidationResponse =
                userServiceFeignClient.validateUser(UserValidationRequestDto.builder()
                        .username(requestDto.username())
                        .password(requestDto.password())
                        .build());

        String userId = userValidationResponse.userId();
        String role = userValidationResponse.role();

        // JWT 토큰 생성
        String accessToken = tokenService.createAccessToken(userId, role);
        String refreshToken = tokenService.createRefreshToken(userId);
        redisTokenService.saveOrUpdateToken(userId, refreshToken);

        return new LoginResponseDto(accessToken, refreshToken, "로그인 성공");
    }

    @Transactional
    public void logout(String userId) {
        redisTokenService.deleteToken(userId);
        log.info("사용자 {}의 RefreshToken 삭제 완료 (로그아웃)", userId);
    }
}
