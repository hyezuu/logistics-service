package takeoff.logisticsservice.msa.delivery.DeliverySequence.application.client.response;

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
