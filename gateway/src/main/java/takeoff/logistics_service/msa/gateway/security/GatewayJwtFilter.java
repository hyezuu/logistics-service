package takeoff.logistics_service.msa.gateway.security;

import jakarta.annotation.PostConstruct;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class GatewayJwtFilter implements GlobalFilter, Ordered {

	private final JwtUtil jwtUtil;
	private static final List<String> EXCLUDED_PATHS = List.of(
		"/api/v1/users/signup",
		"/api/v1/auth/login",
		"/api/v1/auth/token/refresh",
		"/api/v1/app/users/validate",
		"/springdoc/"
	);

	@PostConstruct
	public void init() {
		log.info("JWT Filter 초기화 완료");
	}

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		ServerHttpRequest request = exchange.getRequest();
		String path = request.getURI().getPath();
		log.debug("Request path: {}", path);

		if (isExcludedPath(path)) {
			log.debug("인증 예외 경로 → 필터 통과: {}", path);
			return chain.filter(exchange);
		}

		TokenInfo tokenInfo = validateToken(request);

		if (tokenInfo == null) {
			return rejectRequest(exchange);
		}

		log.info("Id = {}, Role = {}", tokenInfo.userId, tokenInfo.role);

		return chain.filter(exchange.mutate()
				.request(addUserContext(request, tokenInfo))
				.build());
	}

	// 토큰 추출 및 검증 메서드 수정
	private TokenInfo validateToken(ServerHttpRequest request) {
		String authHeader = request.getHeaders().getFirst("Authorization");

		if (isNotBearer(authHeader) || isNotValid(authHeader)) {
			log.warn("JWT 토큰 누락 또는 형식 오류");
			return null;
		}

		return new TokenInfo(jwtUtil.getUserId(authHeader), jwtUtil.getUserRole(authHeader));
	}

	private boolean isNotValid(String authHeader) {
		return !jwtUtil.isValid(authHeader);
	}

	private static boolean isNotBearer(String authHeader) {
		return authHeader == null || !authHeader.startsWith("Bearer ");
	}


	// 인증 예외 경로 확인 메서드
	private boolean isExcludedPath(String path) {
		return EXCLUDED_PATHS.stream()
			.anyMatch(path::startsWith);
	}

	// 요청 헤더 추가 메서드
	private ServerHttpRequest addUserContext(ServerHttpRequest request, TokenInfo tokenInfo) {
		return request.mutate()
			.header("X-User-Id", tokenInfo.userId())
			.header("X-User-Role", tokenInfo.role())
			.build();
	}

	// 인증 실패 응답 생성 메서드
	private Mono<Void> rejectRequest(ServerWebExchange exchange) {
		exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
		return exchange.getResponse().setComplete();
	}

	private record TokenInfo(String userId, String role) {}

	@Override
	public int getOrder() {
		return Ordered.LOWEST_PRECEDENCE;
	}
}