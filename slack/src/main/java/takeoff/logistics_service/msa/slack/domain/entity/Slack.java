package takeoff.logistics_service.msa.slack.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import takeoff.logistics_service.msa.common.domain.BaseEntity;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 13.
 */
@Getter
@Entity
@Table(name = "p_slack")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Slack extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "slack_id")
    private UUID id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Embedded
    private Contents contents;

    public static Slack createSlack(Long userId, String message) {
        return Slack.builder()
            .userId(userId)
            .contents(Contents.create(message))
            .build();
    }
    @Builder
    private Slack(Long userId, Contents contents) {
        this.userId = userId;
        this.contents = contents;
    }

}
