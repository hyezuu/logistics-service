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
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain chain)
		throws ServletException, IOException {
		//로그인 경로가 아닌경우는 해당 필터를 거치지 않고 넘긴다.
		if (!request.getRequestURI().equals("/api/v1/auth/login")) {
			log.info(request.getRequestURI());
			chain.doFilter(request, response);
			return;
		}
		//로그인 경로의 요청인 경우
		try {
			//로그인 요청을 직렬화 (자바객체로)
			LoginRequestDto loginRequest = objectMapper.readValue(request.getInputStream(),
				LoginRequestDto.class);

			//평문으로 전달..? 아이디만 보내야지 응답으로 아이디랑, 암호화된 패스워드를 가져와야죠?
			UserValidationResponseDto userValidationResponse = userServiceFeignClient.validateUser(
				new UserValidationRequestDto(loginRequest.username(), loginRequest.password()));

			//회원가입시에도 얘가 post 요청을 보내야함
			if (userValidationResponse == null) {
				//필턴데 이거 던지면 어떡함 누가받음
				throw AuthBusinessException.from(AuthErrorCode.LOGIN_FAILED);
			}

			//로그인성공했으면
			String userId = userValidationResponse.userId();
			String role = userValidationResponse.role();

			String accessToken = jwtUtil.createAccessToken(userId, role);
			String refreshToken = jwtUtil.createRefreshToken(userId);

			redisTokenService.saveOrUpdateToken(userId, refreshToken);

			response.setHeader("Authorization", accessToken);
			response.setHeader("Refresh-Token", refreshToken);
			response.setContentType("application/json");

			LoginResponseDto responseDto = new LoginResponseDto(accessToken, refreshToken,
				"로그인 성공!");
			objectMapper.writeValue(response.getOutputStream(), responseDto);

		} catch (FeignException.Unauthorized e) {
			throw AuthBusinessException.from(AuthErrorCode.LOGIN_FAILED);
		} catch (IOException e) {
			throw AuthBusinessException.from(AuthErrorCode.BAD_LOGIN_REQUEST);
		}
	}
}
