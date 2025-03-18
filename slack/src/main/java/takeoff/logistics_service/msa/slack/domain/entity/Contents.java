package takeoff.logistics_service.msa.slack.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 13.
 */
@Embeddable
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Contents {

    @Column(columnDefinition = "TEXT",nullable = false)
    private String message;

    @Column(name = "sent_at")
    private LocalDateTime sentAt;

    // 정적 팩토리 메서드
    //builder 사용시 jpa 리플렉션 동작 안 할 수 있음.
    public static Contents create(String message) {
        return new Contents(message,LocalDateTime.now());
    }
    private Contents(String message, LocalDateTime sentAt) {
        this.message = message;
        this.sentAt = sentAt;
    }

    //메세지 수정 메서드
    public void modifyMessage(String message) {
        this.message = message;
    }


}
