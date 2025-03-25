package takeoff.logistics_service.msa.user.presentation.dto.response;

import java.util.List;
import lombok.Builder;

@Builder
public record GetDeliveryManagerListInternalResponseDto(
        List<GetDeliveryManagerListInfoDto> deliveryManagers
) {
    public static GetDeliveryManagerListInternalResponseDto from(List<GetDeliveryManagerListInfoDto> list) {
        return new GetDeliveryManagerListInternalResponseDto(list);
    }
}
