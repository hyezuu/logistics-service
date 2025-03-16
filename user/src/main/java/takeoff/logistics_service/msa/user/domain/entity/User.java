package takeoff.logistics_service.msa.user.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import takeoff.logistics_service.msa.user.domain.vo.CompanyId;
import takeoff.logistics_service.msa.user.domain.vo.HubId;

import java.util.UUID;

@Entity
@Getter
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "user_role")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "p_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

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
    private CompanyId companyId;

    @Embedded
    private HubId hubId;

    protected User(String username, String email, String password, UserRole role, CompanyId companyId, HubId hubId) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
        this.companyId = companyId;
        this.hubId = hubId;
    }

    public static User create(String username, String email, String password, UserRole role, CompanyId companyId, HubId hubId) {
        return new User(username, email, password, role, companyId, hubId);
    }
}
