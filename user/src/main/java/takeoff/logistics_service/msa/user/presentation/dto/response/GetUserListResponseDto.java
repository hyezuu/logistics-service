package takeoff.logistics_service.msa.user.presentation.dto.response;

import lombok.Builder;
import takeoff.logistics_service.msa.user.domain.entity.User;
import takeoff.logistics_service.msa.user.presentation.common.dto.UserPaginationDto;
import org.springframework.data.domain.Page;

import java.util.List;

@Builder
public record GetUserListResponseDto(
        List<GetUserListInfoDto> users,
        UserPaginationDto pagination
) {
    public static GetUserListResponseDto from(Page<User> userPage) {
        return GetUserListResponseDto.builder()
                .users(userPage.getContent().stream().map(GetUserListInfoDto::from).toList())
                .pagination(UserPaginationDto.from(userPage))
                .build();
    }
}
