package takeoff.logistics_service.msa.user.presentation.dto.response;

import java.util.List;
import lombok.Builder;
import org.springframework.data.domain.Page;
import takeoff.logistics_service.msa.user.domain.entity.User;
import takeoff.logistics_service.msa.user.presentation.common.dto.PaginationDto;

@Builder
public record GetUserListResponseDto(
        List<GetUserListInfoDto> users,
        PaginationDto pagination
) {
    public static GetUserListResponseDto from(Page<User> userPage) {
        return GetUserListResponseDto.builder()
                .users(userPage.getContent().stream().map(GetUserListInfoDto::from).toList())
                .pagination(PaginationDto.from(userPage))
                .build();
    }
}
