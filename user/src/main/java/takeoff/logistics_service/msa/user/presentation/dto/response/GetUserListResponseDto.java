package takeoff.logistics_service.msa.user.presentation.dto.response;

import lombok.Builder;
import takeoff.logistics_service.msa.user.domain.entity.User;
import org.springframework.data.domain.Page;

import java.util.List;

@Builder
public record GetUserListResponseDto(
        List<UserInfoDto> users,
        PaginationDto pagination
) {
    public static GetUserListResponseDto from(Page<User> userPage) {
        return GetUserListResponseDto.builder()
                .users(userPage.getContent().stream().map(UserInfoDto::from).toList())
                .pagination(PaginationDto.from(userPage))
                .build();
    }
}

@Builder
record UserInfoDto(Long userId, String username, String slackEmail, String role) {
    public static UserInfoDto from(User user) {
        return UserInfoDto.builder()
                .userId(user.getId())
                .username(user.getUsername())
                .slackEmail(user.getSlackEmail())
                .role(user.getRole().name())
                .build();
    }
}

@Builder
record PaginationDto(int currentPage, int pageSize, int totalPages, long totalElements) {
    public static PaginationDto from(Page<?> page) {
        return PaginationDto.builder()
                .currentPage(page.getNumber())
                .pageSize(page.getSize())
                .totalPages(page.getTotalPages())
                .totalElements(page.getTotalElements())
                .build();
    }
}
