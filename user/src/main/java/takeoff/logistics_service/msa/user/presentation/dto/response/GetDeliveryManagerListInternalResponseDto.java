package takeoff.logistics_service.msa.user.presentation.dto.response;

import lombok.Builder;
import java.util.List;

@Builder
public record GetDeliveryManagerListInternalResponseDto(
        List<GetDeliveryManagerListInfoDto> deliveryManagers
) {
    public static GetDeliveryManagerListInternalResponseDto from(List<GetDeliveryManagerListInfoDto> list) {
        return new GetDeliveryManagerListInternalResponseDto(list);
    }
}
