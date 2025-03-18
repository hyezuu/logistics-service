package takeoff.logistics_service.msa.user.domain.repository;

import org.springframework.data.jpa.domain.Specification;
import takeoff.logistics_service.msa.user.domain.entity.User;
import takeoff.logistics_service.msa.user.domain.entity.UserRole;
import takeoff.logistics_service.msa.user.domain.service.UserSearchCondition;

public class UserSpecifications {
    public static Specification<User> toSpecification(UserSearchCondition condition) {
        Specification<User> spec = Specification.where(null);

        if (condition.username() != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("username"), condition.username()));
        }
        if (condition.email() != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("slackEmail"), condition.email()));
        }
        if (condition.role() != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("role"), condition.role()));
        }

        return spec;
    }
}