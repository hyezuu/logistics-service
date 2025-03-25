package takeoff.logistics_service.msa.auth.infrastructure.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.function.Function;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import takeoff.logistics_service.msa.auth.application.exception.AuthBusinessException;
import takeoff.logistics_service.msa.auth.application.exception.AuthErrorCode;

@Slf4j
@Component
public class JwtUtil {

    private static final String BEARER_PREFIX = "Bearer ";
    private final Key key;
    private final long accessTokenExpiration;
    private final long refreshTokenExpiration;

    public JwtUtil(@Value("${jwt.secret}") String secretKey,
                   @Value("${jwt.access-expiration}") long accessTokenExpiration,
                   @Value("${jwt.refresh-expiration}") long refreshTokenExpiration) {
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes());
        this.accessTokenExpiration = accessTokenExpiration;
        this.refreshTokenExpiration = refreshTokenExpiration;
    }

    public String createAccessToken(String userId, String role) {
        String token = createToken(userId, role, accessTokenExpiration);
        return addBearerPrefix(token);
    }

    public String createRefreshToken(String userId) {
        return createToken(userId, "REFRESH", refreshTokenExpiration);
    }

    private String createToken(String userId, String role, long expiration) {
        return Jwts.builder()
                .subject(userId)
                .claim("role", role)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(removeBearerPrefix(token));
            return true;
        } catch (ExpiredJwtException e) {
            throw AuthBusinessException.from(AuthErrorCode.TOKEN_EXPIRED);
        } catch (JwtException e) {
            throw AuthBusinessException.from(AuthErrorCode.TOKEN_INVALID);
        }
    }

    public String getUserIdFromToken(String token) {
        return getClaimFromToken(removeBearerPrefix(token), Claims::getSubject);
    }

    public String getUserRoleFromToken(String token) {
        return getClaimFromToken(removeBearerPrefix(token), claims -> claims.get("role", String.class));
    }

    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        Claims claims = Jwts.parser()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(removeBearerPrefix(token))
                .getBody();
        return claimsResolver.apply(claims);
    }

    private String addBearerPrefix(String token) {
        return BEARER_PREFIX + token;
    }

    private String removeBearerPrefix(String token) {
        if (token != null && token.startsWith(BEARER_PREFIX)) {
            return token.substring(BEARER_PREFIX.length());
        }
        return token;
    }
}
