package takeoff.logistics_service.msa.gateway.security;

import jakarta.annotation.PostConstruct;
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

    @PostConstruct
    public void init() {
        log.info("bean 초기화");
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();
        log.debug("Request path: {}", path);

        if (path.startsWith("/api/v1/users/signup") || path.startsWith("/api/v1/auth/login")
            || path.startsWith("/api/v1/auth/token/refresh") || path.startsWith("/api/v1/app/users/validate") || path.startsWith("/springdoc/")) {
            log.debug("인증 예외 경로 → 필터 통과");
            return chain.filter(exchange);
        }
        String token = request.getHeaders().getFirst("Authorization");

        if (token == null || !token.startsWith("Bearer ")) {
            log.warn("JWT 토큰 누락 또는 형식 오류 → 401 Unauthorized");
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        token = token.substring(7);

        if (!jwtUtil.validateToken(token)) {
            log.warn("유효하지 않은 JWT 토큰 → 요청 차단");
            return exchange.getResponse().setComplete();
        }

        String userId = jwtUtil.getUserIdFromToken(token);
        String role = jwtUtil.getUserRoleFromToken(token);

        log.info("인증 완료: userId={}, role={}", userId, role);

        // 요청에 헤더 추가
        ServerHttpRequest modifiedRequest = request.mutate()
                .header("X-User-Id", userId)
                .header("X-User-Role", role)
                .build();
        return chain.filter(exchange.mutate().request(modifiedRequest).build());
    }
    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}
