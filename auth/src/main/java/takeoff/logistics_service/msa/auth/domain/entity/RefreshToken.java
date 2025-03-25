package takeoff.logistics_service.msa.auth.domain.entity;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@RedisHash(value = "refreshToken", timeToLive = 604800) // 7일 TTL
public class RefreshToken {
    @Id
    private String id;
    private String refreshToken;
    private LocalDateTime expiryDate;

    public static RefreshToken create(String userId, String token) {
        return RefreshToken.builder()
                .id(userId)
                .refreshToken(token)
                .expiryDate(LocalDateTime.now().plusDays(7))
                .build();
    }
}
