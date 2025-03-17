package takeoff.logistics_service.msa.user.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import takeoff.logistics_service.msa.user.domain.vo.SlackId;

import java.time.LocalDateTime;

@Entity
@Getter
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "user_role")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "p_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_id", nullable = false)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

    @Embedded
    private SlackId slackId;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Builder
    protected User(String username, String email, String password, UserRole role, SlackId slackId) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
        this.slackId = slackId;
    }

    public static User create(String username, String email, String password, UserRole role, SlackId slackId) {
        return User.builder()
                .username(username)
                .email(email)
                .password(password)
                .role(role)
                .slackId(slackId)
                .build();
    }

    public void updateUserInfo(String username, String email, SlackId slackId) {
        this.username = username;
        this.email = email;
        this.slackId = slackId;
    }

    public void delete() {
        this.deletedAt = LocalDateTime.now();
    }

    public boolean isDeleted() {
        return this.deletedAt != null;
    }

    public boolean isDeliveryManager() {
        return this.role.isDeliveryManager();
    }

}
