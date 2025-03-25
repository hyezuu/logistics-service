package takeoff.logistics_service.msa.user.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import takeoff.logistics_service.msa.common.domain.BaseEntity;

@Entity
@Getter
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "user_role")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "p_user")
public class User extends BaseEntity {

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

    public void delete(Long deletedByUserId) {
        super.delete(deletedByUserId);
    }

    public boolean isDeleted() {
        return this.getDeletedAt() != null;
    }

}
