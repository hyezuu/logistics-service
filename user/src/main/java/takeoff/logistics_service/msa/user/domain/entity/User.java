package takeoff.logistics_service.msa.user.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "user_role")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "p_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String slackEmail;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Builder
    protected User(String username, String slackEmail, String password, UserRole role) {
        this.username = username;
        this.slackEmail = slackEmail;
        this.password = password;
        this.role = role;
    }

    public static User create(String username, String slackEmail, String password, UserRole role) {
        return User.builder()
                .username(username)
                .slackEmail(slackEmail)
                .password(password)
                .role(role)
                .build();
    }

    public void updateUserInfo(String username, String slackEmail) {
        this.username = username;
        this.slackEmail = slackEmail;
    }

    public void updateSlackEmail(String slackEmail){
        this.slackEmail = slackEmail;
    }

    public void delete() {
        this.deletedAt = LocalDateTime.now();
    }

    public boolean isDeleted() {
        return this.deletedAt != null;
    }

}
