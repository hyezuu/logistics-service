package takeoff.logistics_service.msa.user.presentation.dto.request;

import lombok.Builder;
import org.springframework.data.domain.Pageable;
import takeoff.logistics_service.msa.user.domain.entity.UserRole;
import takeoff.logistics_service.msa.user.domain.service.UserSearchCondition;

@Builder
public record GetUserListRequestDto(
        String username,
        String email,
        UserRole role,
        Integer page,
        Integer size
) {
    public UserSearchCondition toCondition() {
        return new UserSearchCondition(username, email, role);
    }

    public Pageable toPageable() {
        return Pageable.ofSize(size != null ? size : 10).withPage(page != null ? page : 0);
    }
}
