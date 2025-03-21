package takeoff.logistics_service.msa.auth.infrastructure.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import takeoff.logistics_service.msa.auth.application.client.UserServiceFeignClient;
import takeoff.logistics_service.msa.auth.application.dto.request.UserValidationRequestDto;
import takeoff.logistics_service.msa.auth.application.dto.response.UserValidationResponseDto;
import takeoff.logistics_service.msa.auth.domain.service.RedisTokenService;
import takeoff.logistics_service.msa.auth.presentation.dto.request.LoginRequestDto;
import takeoff.logistics_service.msa.auth.presentation.dto.response.LoginResponseDto;

import java.io.IOException;
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final UserServiceFeignClient userServiceFeignClient;
    private final JwtUtil jwtUtil;
    private final RedisTokenService redisTokenService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        if (!request.getRequestURI().equals("/api/v1/auth/login")) {
            chain.doFilter(request, response);
            return;
        }
        try {
            LoginRequestDto loginRequest = objectMapper.readValue(request.getInputStream(), LoginRequestDto.class);

            UserValidationResponseDto userValidationResponse = userServiceFeignClient.validateUser(
                    new UserValidationRequestDto(loginRequest.username(), loginRequest.password()));

            if (userValidationResponse == null) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "사용자 검증 실패했습니다!");
                return;
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
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "사용자 검증 실패했습니다!");
        } catch (IOException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "로그인 요청을 읽을 수 없습니다.");
        }
    }
}
