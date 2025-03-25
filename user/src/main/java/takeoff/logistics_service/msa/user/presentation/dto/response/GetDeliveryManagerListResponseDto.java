package takeoff.logistics_service.msa.user.presentation.dto.response;

import java.util.List;
import lombok.Builder;
import org.springframework.data.domain.Page;
import takeoff.logistics_service.msa.user.domain.entity.DeliveryManager;
import takeoff.logistics_service.msa.user.presentation.common.dto.PaginationDto;

@Builder
public record GetDeliveryManagerListResponseDto(
        List<GetDeliveryManagerListInfoDto> deliveryManagers,
        PaginationDto pagination
) {
    public static GetDeliveryManagerListResponseDto from(Page<DeliveryManager> managerPage) {
        return GetDeliveryManagerListResponseDto.builder()
                .deliveryManagers(managerPage.getContent().stream().map(GetDeliveryManagerListInfoDto::from).toList())
                .pagination(PaginationDto.from(managerPage))
                .build();
    }
}
