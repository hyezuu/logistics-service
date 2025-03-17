package takeoff.logistics_service.msa.user.presentation.dto.request;

import org.springframework.data.domain.Pageable;

public record GetUserListRequestDto(Pageable pageable) {
}
