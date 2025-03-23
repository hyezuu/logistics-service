package takeoff.logistics_service.msa.user.presentation.dto.response;

import java.util.List;

public record GetManagerListInternalResponseDto(
        List<GetManagerListInfoDto> users
) {
    public static GetManagerListInternalResponseDto from(List<GetManagerListInfoDto> users) {
        return new GetManagerListInternalResponseDto(users);
    }
}
