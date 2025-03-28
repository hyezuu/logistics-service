package takeoff.logistics_service.msa.gateway.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.util.function.Function;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

    private static final String BEARER_PREFIX = "Bearer ";
    private final SecretKey key;


    public JwtUtil(@Value("${jwt.secret}") String secretKey) {
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    // JWT에서 userId 추출
    public String getUserId(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    // JWT에서 role 추출
    public String getUserRole(String token) {
        return getClaimFromToken(token, claims -> claims.get("role", String.class));
    }

    // JWT 토큰 유효성 검사
    public boolean isValid(String token) {
        try {
            Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(removePrefix(token));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        Claims claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(removePrefix(token))
                .getPayload();
        return claimsResolver.apply(claims);
    }

    private String removePrefix(String token) {
        if (token != null && token.startsWith(BEARER_PREFIX)) {
            return token.substring(BEARER_PREFIX.length());
        }
        return token;
    }
}
