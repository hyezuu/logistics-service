package takeoff.logistics_service.msa.auth.infrastructure.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import takeoff.logistics_service.msa.auth.application.client.UserServiceFeignClient;
import takeoff.logistics_service.msa.auth.application.dto.request.UserValidationRequestDto;
import takeoff.logistics_service.msa.auth.application.dto.response.UserValidationResponseDto;
import takeoff.logistics_service.msa.auth.application.exception.AuthBusinessException;
import takeoff.logistics_service.msa.auth.application.exception.AuthErrorCode;
import takeoff.logistics_service.msa.auth.domain.service.RedisTokenService;
import takeoff.logistics_service.msa.auth.presentation.dto.request.LoginRequestDto;
import takeoff.logistics_service.msa.auth.presentation.dto.response.LoginResponseDto;
@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final UserServiceFeignClient userServiceFeignClient;
    private final JwtUtil jwtUtil;
    private final RedisTokenService redisTokenService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        if (!request.getRequestURI().equals("/api/v1/auth/login")) {
            log.info(request.getRequestURI());
            chain.doFilter(request, response);
            return;
        }
        try {
            LoginRequestDto loginRequest = objectMapper.readValue(request.getInputStream(), LoginRequestDto.class);

            UserValidationResponseDto userValidationResponse = userServiceFeignClient.validateUser(
                    new UserValidationRequestDto(loginRequest.username(), loginRequest.password()));

            if (userValidationResponse == null) {
                throw AuthBusinessException.from(AuthErrorCode.LOGIN_FAILED);
            }

            String userId = userValidationResponse.userId();
            String role = userValidationResponse.role();

            String accessToken = jwtUtil.createAccessToken(userId, role);
            String refreshToken = jwtUtil.createRefreshToken(userId);

            redisTokenService.saveOrUpdateToken(userId, refreshToken);

            response.setHeader("Authorization", accessToken);
            response.setHeader("Refresh-Token", refreshToken);
            response.setContentType("application/json");

            LoginResponseDto responseDto = new LoginResponseDto(accessToken, refreshToken, "로그인 성공!");
            objectMapper.writeValue(response.getOutputStream(), responseDto);

        } catch (FeignException.Unauthorized e) {
            throw AuthBusinessException.from(AuthErrorCode.LOGIN_FAILED);
        } catch (IOException e) {
            throw AuthBusinessException.from(AuthErrorCode.BAD_LOGIN_REQUEST);
        }
    }
}
